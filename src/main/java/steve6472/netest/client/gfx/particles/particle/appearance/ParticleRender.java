package steve6472.netest.client.gfx.particles.particle.appearance;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.base.ParticleComponent;
import steve6472.sge.gfx.game.stack.Stack;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/10/2021
 * Project: VoxWorld
 *
 ***********************/
public abstract class ParticleRender extends ParticleComponent
{
	public ParticleRender(JSONObject json)
	{
		super(json);
	}

	public abstract void render(MoLangRuntime runtime, Particle particle, Stack stack);

	/**
	 * Use render
	 * @param runtime
	 * @param particle -
	 */
	@Override
	@Deprecated
	public void tick(MoLangRuntime runtime, Particle particle)
	{

	}
}
