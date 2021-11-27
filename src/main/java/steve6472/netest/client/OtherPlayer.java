package steve6472.netest.client;

import net.querz.nbt.tag.CompoundTag;
import org.joml.Vector2d;
import steve6472.netest.SpaceObject;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class OtherPlayer extends SpaceObject
{
	public OtherPlayer(UUID id)
	{
		super(id);
		position = new Vector2d();
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

	@Override
	public void tick()
	{

	}
}
