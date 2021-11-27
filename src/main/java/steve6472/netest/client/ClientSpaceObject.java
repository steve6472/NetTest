package steve6472.netest.client;

import net.querz.nbt.tag.CompoundTag;
import steve6472.netest.SpaceObject;
import steve6472.sge.gfx.game.stack.Stack;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public abstract class ClientSpaceObject extends SpaceObject
{
	public ClientSpaceObject(UUID id)
	{
		super(id);
	}

	public abstract void render(Stack stack);

	@Override @Deprecated
	public CompoundTag write()
	{ return null; }

	@Override @Deprecated
	public void read(CompoundTag tag)
	{ }
}
