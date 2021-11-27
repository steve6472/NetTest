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
	public List<OtherPlayer> players = new ArrayList<>();

	public void tick()
	{

	}

	public void render(Stack stack)
	{
		for (OtherPlayer player : players)
		{
			stack.pushMatrix();
			stack.translate((float) player.position.x, 0, (float) player.position.y);
			stack.rotateY(player.rotation);
			Models.SHIP.render(stack);
			Models.SHIP_COLORED.render(stack);
			stack.popMatrix();
		}
	}
}
