package steve6472.netest.client;

import steve6472.sge.gfx.game.stack.Stack;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class OtherPlayer extends ClientSpaceObject
{
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
		Models.SHIP.render(stack);
		Models.SHIP_COLORED.render(stack);
		stack.popMatrix();
	}
}
