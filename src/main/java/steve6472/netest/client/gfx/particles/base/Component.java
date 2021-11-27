package steve6472.netest.client.gfx.particles.base;

import org.json.JSONObject;
import steve6472.sge.main.game.Id;
import steve6472.sge.main.game.registry.ID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/5/2021
 * Project: VoxWorld
 *
 ***********************/
public abstract class Component implements ID
{
	private Id id;

	public Component(JSONObject json)
	{

	}

	@Override
	public void setId(Id id)
	{
		this.id = id;
	}

	@Override
	public Id getId()
	{
		return id;
	}
}
