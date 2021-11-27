package steve6472.netest.client.gfx.particles.particle.initial;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.base.ParticleComponent;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/6/2021
 * Project: VoxWorld
 *
 ***********************/
public abstract class ParticleInitialStateComponent extends ParticleComponent
{
	public ParticleInitialStateComponent(JSONObject json)
	{
		super(json);
	}

	public abstract void modifyState(MoLangRuntime runtime, Particle particle);

	/**
	 * @deprecated Not called, ever
	 * @param particle particle
	 */
	@Override
	@Deprecated
	public void tick(MoLangRuntime runtime, Particle particle)
	{

	}
}
