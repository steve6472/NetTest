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
	protected final Server server;

	public ServerSpaceObject(Server server, UUID id)
	{
		super(id);
		this.server = server;
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
