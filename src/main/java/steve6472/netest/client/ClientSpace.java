package steve6472.netest.client;

import steve6472.sge.gfx.game.stack.Stack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class ClientSpace
{
	public Map<UUID, ClientSpaceObject> objects = new HashMap<>();

	public void tick()
	{

	}

	public void render(Stack stack)
	{
		objects.forEach((u, o) -> o.render(stack));
	}

	public ClientSpaceObject getObject(UUID uuid)
	{
		return objects.get(uuid);
	}
}
