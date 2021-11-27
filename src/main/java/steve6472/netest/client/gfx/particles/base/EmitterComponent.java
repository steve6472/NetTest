package steve6472.netest.client.gfx.particles.base;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Emitter;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/5/2021
 * Project: VoxWorld
 *
 ***********************/
public abstract class EmitterComponent extends Component
{
	public EmitterComponent(JSONObject json)
	{
		super(json);
	}

	public abstract void tick(MoLangRuntime runtime, Emitter emitter);
}
