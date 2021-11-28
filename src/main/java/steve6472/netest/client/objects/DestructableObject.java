package steve6472.netest.client.objects;

import steve6472.netest.client.ClientSpaceObject;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.main.util.RandomUtil;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class DestructableObject extends ClientSpaceObject
{
	BBModel model;
	private final float randomRotX = (float) RandomUtil.randomRadian();
	private final float randomRotZ = (float) RandomUtil.randomRadian();

	public DestructableObject(UUID id, BBModel model)
	{
		super(id);
		this.model = model;
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
		stack.rotateZYX(randomRotZ, rotation, randomRotX);
		model.render(stack);
		stack.popMatrix();
	}
}
