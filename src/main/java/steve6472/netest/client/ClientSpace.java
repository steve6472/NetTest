package steve6472.netest.client;

import steve6472.sge.gfx.game.stack.Stack;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class ClientSpace
{
	public List<ClientSpaceObject> objects = new ArrayList<>();

	public void tick()
	{

	}

	public void render(Stack stack)
	{
		for (ClientSpaceObject obj : objects)
		{
			obj.render(stack);
		}
	}
}
