package steve6472.netest.server;

import net.querz.nbt.tag.CompoundTag;
import steve6472.netest.ISaveable;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class ServerSpace implements ISaveable
{
	List<ServerPlayer> players = new ArrayList<>();

	public void tick()
	{

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
