package steve6472.netest.client.gfx.particles.base;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Particle;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/5/2021
 * Project: VoxWorld
 *
 ***********************/
public abstract class ParticleComponent extends Component
{
	public ParticleComponent(JSONObject json)
	{
		super(json);
	}

	public abstract void tick(MoLangRuntime runtime, Particle particle);
}
