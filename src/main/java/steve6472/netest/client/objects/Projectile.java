package steve6472.netest.client.objects;

import steve6472.netest.client.ClientSpaceObject;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.stack.Stack;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class Projectile extends ClientSpaceObject
{
	BBModel model;

	public Projectile(UUID id, BBModel model)
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
		stack.rotateY(rotation);
		model.render(stack);
		stack.popMatrix();
	}
}
