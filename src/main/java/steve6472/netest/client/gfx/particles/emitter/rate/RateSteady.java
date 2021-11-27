package steve6472.netest.client.gfx.particles.emitter.rate;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONObject;
import steve6472.netest.Main;
import steve6472.netest.client.gfx.particles.Emitter;
import steve6472.netest.client.gfx.particles.Val;
import steve6472.netest.client.gfx.particles.emitter.lifetime.LifetimeLooping;
import steve6472.sge.main.util.MathUtil;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/5/2021
 * Project: VoxWorld
 *
 ***********************/
public class RateSteady extends Rate
{
	double nextSpawnAt;
	Val spawnRate;
	Val maxParticles;

	public RateSteady(JSONObject json)
	{
		super(json);

		spawnRate = new Val(json.opt("spawn_rate"), 1);
		maxParticles = new Val(json.opt("max_particles"), 50);
	}

	@Override
	public void tick(MoLangRuntime runtime, Emitter emitter)
	{
		double spawnRate = this.spawnRate.execute(runtime);
		if (spawnRate < 60)
		{
			if (emitter.getLife() > nextSpawnAt || MathUtil.compareFloat((float) emitter.getLife(), (float) nextSpawnAt, 0.02f))
			{
				nextSpawnAt = (60f / spawnRate) / 60f + emitter.getLife();
				LifetimeLooping looping = emitter.getComponent(LifetimeLooping.class);
				if (looping != null)
				{
					nextSpawnAt = nextSpawnAt % looping.activeTime.execute(runtime);
				}
				emitOne(runtime, emitter);
			}
		} else if (spawnRate == 60)
		{
			emitOne(runtime, emitter);
		} else
		{
			int fraction = (int) (60f / ((spawnRate / 60f) % 1f * 60f));
			if (Main.getTicksCounter() % fraction == 0)
			{
				emitOne(runtime, emitter);
			}
			for (int i = 0; i < (int) (spawnRate / 60f); i++)
			{
				emitOne(runtime, emitter);
			}
		}
	}

	private void emitOne(MoLangRuntime runtime, Emitter emitter)
	{
		if (emitter.getParticleCount() < maxParticles.execute(runtime))
		{
			emitter.createParticle();
		}
	}
}
