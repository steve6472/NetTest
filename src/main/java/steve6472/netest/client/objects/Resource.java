package steve6472.netest.client.objects;

import steve6472.netest.client.ClientSpaceObject;
import steve6472.netest.client.Models;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.stack.tess.BBTess;
import steve6472.sge.main.util.MathUtil;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 12/7/2021
 * Project: NetTest
 *
 ***********************/
public class Resource extends ClientSpaceObject
{
	public Resource(UUID id)
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
		BBTess blockbench = stack.getTess(BBTess.class, "blockbench");
		blockbench.color((float) MathUtil.animateRadians(1), (float) MathUtil.animateRadians(2), (float) MathUtil.animateRadians(3), 1);
		stack.pushMatrix();
		stack.translate((float) position.x, 0, (float) position.y);
		stack.scale(0.5f);
		Models.SMALL_ASTEROID_01.render(stack);
		stack.popMatrix();
		blockbench.color(1, 1, 1, 1);
	}
}
