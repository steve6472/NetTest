package steve6472.netest.client.objects;

import steve6472.netest.client.ClientSpaceObject;
import steve6472.netest.client.Models;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.stack.tess.BBTess;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class OtherPlayer extends ClientSpaceObject
{
	public int color;

	public OtherPlayer(UUID id)
	{
		super(id);
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void render(Stack stack)
	{
		stack.pushMatrix();
		stack.translate((float) position.x, 0, (float) position.y);
		stack.rotateY(rotation);
		BBTess blockbench = stack.getTess(BBTess.class, "blockbench");
		blockbench.color(color);
		Models.SHIP.render(stack);
		blockbench.color(0xffffffff);
		Models.SHIP_COLORED.render(stack);
		stack.popMatrix();
	}
}
