package steve6472.netest.client.gfx.particles;

import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.base.EmitterComponent;
import steve6472.netest.client.gfx.particles.base.ParticleComponent;
import steve6472.netest.client.gfx.particles.emitter.lifetime.LifetimeLooping;
import steve6472.netest.client.gfx.particles.emitter.rate.RateInstant;
import steve6472.netest.client.gfx.particles.emitter.rate.RateSteady;
import steve6472.netest.client.gfx.particles.emitter.shapes.ShapePoint;
import steve6472.netest.client.gfx.particles.emitter.shapes.ShapeSphere;
import steve6472.netest.client.gfx.particles.particle.LifetimeExpression;
import steve6472.netest.client.gfx.particles.particle.appearance.RenderBillboard;
import steve6472.netest.client.gfx.particles.particle.appearance.RenderCube;
import steve6472.netest.client.gfx.particles.particle.appearance.Tinting;
import steve6472.netest.client.gfx.particles.particle.initial.InitialSpeed;
import steve6472.netest.client.gfx.particles.particle.initial.InitialSpin;
import steve6472.netest.client.gfx.particles.particle.motion.MotionDynamic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Function;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/5/2021
 * Project: VoxWorld
 *
 ***********************/
public class BugrockParticleMain
{
	public static final int EMITTER_RANDOM_COUNT = 4;
	public static final int PARTICLE_RANDOM_COUNT = 4;

	public static final HashMap<String, Function<JSONObject, ? extends EmitterComponent>> COMPONENTS_EMITTER = EmitterBuilder
		.create()

		.add("emitter_rate_steady", RateSteady::new)
		.add("emitter_rate_instant", RateInstant::new)

		.add("emitter_lifetime_looping", LifetimeLooping::new)

		// Shapes
		.add("emitter_shape_point", ShapePoint::new)
		.add("emitter_shape_sphere", ShapeSphere::new)
//		.add("emitter_shape_box", ShapeBox::new)
		.build();

	public static final HashMap<String, Function<JSONObject, ? extends ParticleComponent>> COMPONENTS_PARTICLE = ParticleBuilder
		.create()
		// Initial state
		.add("particle_initial_speed", InitialSpeed::new)
		.add("particle_initial_spin", InitialSpin::new)

		.add("particle_lifetime_expression", LifetimeExpression::new)

		.add("particle_appearance_tinting", Tinting::new)
		.add("particle_render_cube", RenderCube::new) // Custom
		.add("particle_appearance_billboard", RenderBillboard::new)

		.add("particle_motion_dynamic", MotionDynamic::new)
		.build();

	private static class EmitterBuilder
	{
		HashMap<String, Function<JSONObject, ? extends EmitterComponent>> map;

		private EmitterBuilder()
		{
			map = new LinkedHashMap<>();
		}

		public static EmitterBuilder create()
		{
			return new EmitterBuilder();
		}

		public EmitterBuilder add(String key, Function<JSONObject, ? extends EmitterComponent> value)
		{
			map.put(key, value);
			return this;
		}

		public HashMap<String, Function<JSONObject, ? extends EmitterComponent>> build()
		{
			return map;
		}
	}

	private static class ParticleBuilder
	{
		HashMap<String, Function<JSONObject, ? extends ParticleComponent>> map;

		private ParticleBuilder()
		{
			map = new LinkedHashMap<>();
		}

		public static ParticleBuilder create()
		{
			return new ParticleBuilder();
		}

		public ParticleBuilder add(String key, Function<JSONObject, ? extends ParticleComponent> value)
		{
			map.put(key, value);
			return this;
		}

		public HashMap<String, Function<JSONObject, ? extends ParticleComponent>> build()
		{
			return map;
		}
	}
}
