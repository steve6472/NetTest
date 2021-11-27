package steve6472.netest.client.gfx.particles;

import com.bedrockk.molang.MoLang;
import com.bedrockk.molang.runtime.MoLangRuntime;
import com.bedrockk.molang.runtime.value.DoubleValue;
import org.joml.Vector3d;
import steve6472.netest.client.gfx.particles.base.Component;
import steve6472.netest.client.gfx.particles.emitter.lifetime.LifetimeLooping;
import steve6472.netest.client.gfx.particles.emitter.rate.Rate;
import steve6472.netest.client.gfx.particles.emitter.shapes.Shape;
import steve6472.netest.client.gfx.particles.particle.LifetimeExpression;
import steve6472.netest.client.gfx.particles.particle.appearance.ParticleRender;
import steve6472.netest.client.gfx.particles.particle.initial.InitialSpeed;
import steve6472.netest.client.gfx.particles.particle.initial.InitialSpin;
import steve6472.netest.client.gfx.particles.particle.motion.ParticleMotion;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.main.game.registry.RegistryObject;
import steve6472.sge.main.util.RandomUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/13/2021
 * Project: VoxWorld
 *
 ***********************/
public class Emitter
{
	private final RegistryObject<ParticleEntry> particle;
	private final MoLangRuntime runtime;

	/*
	 * Emitter data
	 */
	private final Set<Component> components = new HashSet<>();
	private final Set<Curve> curves = new HashSet<>();
	private final List<Particle> particles = new ArrayList<>();
	long birth;
	private final Vector3d position;

	private int currentRenderIndex;

	public Emitter(RegistryObject<ParticleEntry> particle)
	{
		this.particle = particle;
		position = new Vector3d();

		for (Supplier<Curve> curve : particle.get().curves)
		{
			this.curves.add(curve.get());
		}

		particle.get().emitterComponentSupplier.forEach(c -> this.components.add(c.get()));
		particle.get().particleComponentSupplier.forEach(c -> this.components.add(c.get()));

		runtime = MoLang.createRuntime();

		particle.get().getCreationExpression().execute(runtime);
		generateRandomValues();

		birth = System.currentTimeMillis();
	}

	public void tick()
	{
		updateEmitterValues(runtime);

		ParticleMotion motion = getComponent(ParticleMotion.class);
		LifetimeExpression particleLifetime = getComponent(LifetimeExpression.class);
		particles.removeIf(p ->
		{
			p.updateParticleValues(runtime);
			motion.tick(runtime, p);
			return particleLifetime.shouldBeRemoved(runtime, p);
		});

		Rate rate = getComponent(Rate.class);
		rate.tick(runtime, this);
	}

	public void render(Stack stack)
	{
		updateEmitterValues(runtime);
		ParticleRender render = getComponent(ParticleRender.class);

		for (int i = 0; i < particles.size(); i++)
		{
			currentRenderIndex = i;
			Particle p = particles.get(i);
			p.updateParticleValues(runtime);
			render.render(runtime, p, stack);
		}
	}

	public void updateEmitterValues(MoLangRuntime runtime)
	{
		runtime.getEnvironment().setValue("variable.emitter_age", new DoubleValue(getLife()));
	}

	public boolean isFirst()
	{
		return currentRenderIndex == 0;
	}

	public boolean isLast()
	{
		return currentRenderIndex == getParticleCount() - 1;
	}

	public int getParticleCount()
	{
		return particles.size();
	}

	public void createParticle()
	{
		Particle particle = new Particle(this);
		//TODO: emitter position
		particle.getPosition().set(position);
		particle.updateParticleValues(runtime);
		particle.setLifetime(runtime);

		InitialSpeed speed = getComponent(InitialSpeed.class);
		if (speed != null)
			speed.modifyState(runtime, particle);

		InitialSpin spin = getComponent(InitialSpin.class);
		if (spin != null)
			spin.modifyState(runtime, particle);

		Shape shape = getComponent(Shape.class);
		shape.modifyPosition(runtime, particle);

		particles.add(particle);
	}

	public void generateRandomValues()
	{
		for (int i = 0; i < BugrockParticleMain.EMITTER_RANDOM_COUNT; i++)
		{
			runtime.getEnvironment().setValue("variable.emitter_random_" + (i + 1), new DoubleValue(RandomUtil.randomDouble(0, 1)));
		}
	}

	public <T> T getComponent(Class<T> clazz)
	{
		for (Component component : components)
		{
			if (clazz.isAssignableFrom(component.getClass()))
				return (T) component;
		}
		return null;
	}

	public Vector3d getPosition()
	{
		return position;
	}

	public void setPosition(double x, double y, double z)
	{
		position.set(x, y, z);
	}

	public void setPosition(float x, float y, float z)
	{
		position.set(x, y, z);
	}

	public Set<Curve> getCurves()
	{
		return curves;
	}

	public double getLife()
	{
		double v = (System.currentTimeMillis() - birth) / 1000d;
		LifetimeLooping looping = getComponent(LifetimeLooping.class);
		if (looping != null)
		{
			v = v % looping.activeTime.execute(runtime);
		}
		return v;
	}

	public int getTexture()
	{
		return particle.get().getTexture();
	}
}
