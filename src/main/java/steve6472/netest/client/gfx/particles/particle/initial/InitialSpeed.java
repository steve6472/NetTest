package steve6472.netest.client.gfx.particles.particle.initial;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.Val;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/6/2021
 * Project: VoxWorld
 *
 ***********************/
public class InitialSpeed extends ParticleInitialStateComponent
{
	public Val speed;

	public InitialSpeed(JSONObject json)
	{
		super(json);
	}

	public void setSpeed(Val speed)
	{
		this.speed = speed;
	}

	@Override
	public void modifyState(MoLangRuntime runtime, Particle particle)
	{
		particle.setSpeed(speed.execute(runtime));
	}
}
