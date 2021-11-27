package steve6472.netest.client.gfx.particles;

import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.base.EmitterComponent;
import steve6472.netest.client.gfx.particles.base.ParticleComponent;
import steve6472.netest.client.gfx.particles.particle.initial.InitialSpeed;
import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.main.game.Id;
import steve6472.sge.main.game.registry.ID;
import steve6472.sge.main.util.JSONUtil;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/11/2021
 * Project: VoxWorld
 *
 ***********************/
public class ParticleEntry implements ID
{
	public final Set<Supplier<ParticleComponent>> particleComponentSupplier = new LinkedHashSet<>();
	public final Set<Supplier<EmitterComponent>> emitterComponentSupplier = new LinkedHashSet<>();
	public final Set<Supplier<Curve>> curves = new LinkedHashSet<>();

	private Val creationExpression;

	private Id id;
	private StaticTexture texture;

	public ParticleEntry()
	{
	}

	@Override
	public void setId(Id id)
	{
		this.id = id;

		JSONObject json = JSONUtil.readJSON(new File("game/" + id.namespace() + "/particles/" + id.id() + ".json"));

		JSONObject particleEffect = json.getJSONObject("particle_effect");
		JSONObject description = particleEffect.getJSONObject("description");
		JSONObject basicRenderParameters = description.getJSONObject("basic_render_parameters");
		texture = StaticTexture.fromFile(new File("game/textures/", basicRenderParameters.getString("texture") + ".png"));

		JSONObject curves = particleEffect.optJSONObject("curves");
		if (curves != null)
		{
			for (String s : curves.keySet())
			{
				JSONObject jsonObject = curves.getJSONObject(s);
				this.curves.add(() -> new Curve(s, jsonObject));
			}
		}


		JSONObject components = particleEffect.getJSONObject("components");

		/*
		 * Hack in init vars
		 */
		JSONObject init;
		init = components.optJSONObject("minecraft:emitter_initialization");
		if (init == null) init = components.optJSONObject("emitter_initialization");

		if (init != null)
		{
			creationExpression = new Val(init.optString("creation_expression", ""));
		} else
		{
			creationExpression = new Val(null, 0);
		}

		/*
		 * Create Emitter Components
		 */

		for (String s : BugrockParticleMain.COMPONENTS_EMITTER.keySet())
		{
			String withMC = "minecraft:" + s;

			if (components.has(withMC))
			{
//				System.out.println("Emitter Component: " + withMC);
				Function<JSONObject, ? extends EmitterComponent> jsonObjectFunction = BugrockParticleMain.COMPONENTS_EMITTER.get(s);
				emitterComponentSupplier.add(() -> jsonObjectFunction.apply(components.getJSONObject(withMC)));
			}

			if (components.has(s))
			{
//				System.out.println("Emitter Component: " + s);
				Function<JSONObject, ? extends EmitterComponent> jsonObjectFunction = BugrockParticleMain.COMPONENTS_EMITTER.get(s);
				emitterComponentSupplier.add(() -> jsonObjectFunction.apply(components.getJSONObject(s)));
			}
		}

		/*
		 * Create Particle Components
		 */

		for (String S : components.keySet())
		{
			final String noMC = S.startsWith("minecraft") ? S.substring("minecraft:".length()) : S;
			final String s = S;

			if (BugrockParticleMain.COMPONENTS_PARTICLE.containsKey(noMC))
			{
				Function<JSONObject, ? extends ParticleComponent> jsonObjectFunction = BugrockParticleMain.COMPONENTS_PARTICLE
					.get(noMC);

//				System.out.println("Particle Component: " + noMC);

				if (noMC.equals("particle_initial_speed"))
				{
					particleComponentSupplier.add(() ->
					{
						ParticleComponent apply = jsonObjectFunction.apply(null);
						((InitialSpeed) apply).setSpeed(new Val(components.get(s)));
						return apply;
					});
				} else
				{
					particleComponentSupplier.add(() -> jsonObjectFunction.apply(components.getJSONObject(s)));
				}
			}
		}
	}

	public Val getCreationExpression()
	{
		return creationExpression;
	}

	public int getTexture()
	{
		return texture.getId();
	}

	@Override
	public Id getId()
	{
		return id;
	}
}
