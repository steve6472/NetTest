package steve6472.netest.client.gfx.particles.emitter.lifetime;

import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.base.EmitterComponent;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/6/2021
 * Project: VoxWorld
 *
 ***********************/
public abstract class Lifetime extends EmitterComponent
{
	public Lifetime(JSONObject json)
	{
		super(json);
	}
}
