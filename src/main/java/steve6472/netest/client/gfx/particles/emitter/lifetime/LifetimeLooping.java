package steve6472.netest.client.gfx.particles.emitter.lifetime;

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
public class LifetimeLooping extends Lifetime
{
	public final Val activeTime;
	public final Val sleepTime;

	public LifetimeLooping(JSONObject json)
	{
		super(json);

		activeTime = new Val(json.opt("active_time"), 10);
		sleepTime = new Val(json.opt("sleep_time"), 0);
	}

	@Override
	public void tick(MoLangRuntime runtime, Emitter emitter)
	{

	}
}
