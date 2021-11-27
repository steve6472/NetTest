package steve6472.netest.client.gfx.particles;

import com.bedrockk.molang.runtime.MoLangRuntime;
import com.bedrockk.molang.runtime.value.DoubleValue;
import org.joml.Vector3d;
import steve6472.netest.client.gfx.particles.particle.LifetimeExpression;
import steve6472.sge.main.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/13/2021
 * Project: VoxWorld
 *
 ***********************/
public class Particle
{
	private final List<DoubleValue> randomValues = new ArrayList<>();
	private DoubleValue lifetime;
	private final Emitter emitter;
	private final Vector3d position, motion;
	private double speed;
	private double rotationRate;
	private double rotation;
	private final long birth;

	public Particle(Emitter emitter)
	{
		this.emitter = emitter;
		this.position = new Vector3d();
		this.motion = new Vector3d();
		birth = System.currentTimeMillis();

		generateRandomValues();
	}

	public void setLifetime(MoLangRuntime runtime)
	{
		this.lifetime = new DoubleValue(emitter.getComponent(LifetimeExpression.class).getMaxLifetime(runtime, this));
	}

	public void generateRandomValues()
	{
		for (int i = 0; i < BugrockParticleMain.PARTICLE_RANDOM_COUNT; i++)
		{
			randomValues.add(new DoubleValue(RandomUtil.randomDouble(0, 1)));
		}
	}

	public void updateParticleValues(MoLangRuntime runtime)
	{
		runtime.getEnvironment().setValue("variable.particle_age", new DoubleValue(getLife()));
		runtime.getEnvironment().setValue("variable.particle_x", new DoubleValue(getPosition().x));
		runtime.getEnvironment().setValue("variable.particle_y", new DoubleValue(getPosition().y));
		runtime.getEnvironment().setValue("variable.particle_z", new DoubleValue(getPosition().z));
		runtime.getEnvironment().setValue("variable.particle_lifetime", lifetime);

		for (int i = 0; i < randomValues.size(); i++)
		{
			runtime.getEnvironment().setValue("variable.particle_random_" + (i + 1), randomValues.get(i));
		}

		for (Curve curve : getEmitter().getCurves())
		{
			runtime.getEnvironment().setValue(curve.getName(), new DoubleValue(curve.getValue(runtime)));
		}
	}

	public Vector3d getPosition()
	{
		return position;
	}

	public Vector3d getMotion()
	{
		return motion;
	}

	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

	public double getRotationRate()
	{
		return rotationRate;
	}

	public void setRotationRate(double rotationRate)
	{
		this.rotationRate = rotationRate;
	}

	public double getRotation()
	{
		return rotation;
	}

	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}

	public double getMaxLifetime()
	{
		return lifetime.asDouble();
	}

	public Emitter getEmitter()
	{
		return emitter;
	}

	public double getLife()
	{
		return (System.currentTimeMillis() - birth) / 1000d;
	}
}
