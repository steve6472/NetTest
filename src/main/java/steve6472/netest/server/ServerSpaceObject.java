package steve6472.netest.server;

import net.querz.nbt.tag.CompoundTag;
import steve6472.netest.SpaceObject;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public abstract class ServerSpaceObject extends SpaceObject
{
	public ServerSpaceObject(UUID id)
	{
		super(id);
	}

	@Override
	public CompoundTag write()
	{
		return null;
	}

	@Override
	public void read(CompoundTag tag)
	{

	}
}
