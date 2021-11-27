package steve6472.netest.client.gfx.particles.emitter.rate;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Emitter;
import steve6472.netest.client.gfx.particles.Val;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/5/2021
 * Project: VoxWorld
 *
 ***********************/
public class RateInstant extends Rate
{
	Val count;
	boolean emitted = false;

	public RateInstant(JSONObject json)
	{
		super(json);

		count = new Val(json.opt("num_particles"), 10);
	}

	@Override
	public void tick(MoLangRuntime runtime, Emitter emitter)
	{
		if (emitted)
			return;

		emitted = true;

		float count = this.count.executeF(runtime);

		while (emitter.getParticleCount() < count)
		{
			emitter.createParticle();
		}
	}
}
