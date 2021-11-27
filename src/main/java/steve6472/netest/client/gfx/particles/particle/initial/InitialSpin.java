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
public class InitialSpin extends ParticleInitialStateComponent
{
	public Val rotation;
	public Val rotationRate;

	public InitialSpin(JSONObject json)
	{
		super(json);
		this.rotation = new Val(json.opt("rotation"), 0);
		this.rotationRate = new Val(json.opt("rotation_rate"), 0);
	}

	@Override
	public void modifyState(MoLangRuntime runtime, Particle particle)
	{
		particle.setRotation(rotation.execute(runtime));
		particle.setRotationRate(rotationRate.execute(runtime));
	}
}
