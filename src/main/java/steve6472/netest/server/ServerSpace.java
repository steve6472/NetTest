package steve6472.netest.server;

import net.querz.nbt.tag.CompoundTag;
import steve6472.netest.ISaveable;
import steve6472.netest.network.forclient.CSpawn;
import steve6472.netest.server.objects.SmallAsteroid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class ServerSpace implements ISaveable
{
	private final Server server;

	List<ServerPlayer> players = new ArrayList<>();
	List<ServerSpaceObject> objects = new ArrayList<>();

	public ServerSpace(Server server)
	{
		this.server = server;
	}

	public void tick()
	{
		for (Iterator<ServerPlayer> iterator = players.iterator(); iterator.hasNext(); )
		{
			ServerPlayer player = iterator.next();
			if (player.shouldBeRemoved)
				iterator.remove();
			else
				player.tick();
		}

		for (ServerSpaceObject object : objects)
		{
			object.tick();
		}

		if (server.serverTick == 10)
		{
			createSpace();
		}
	}

	private void createSpace()
	{
//		for (int i = 0; i < 16; i++)
//		{
//			spawnSmallAsteroid(0, RandomUtil.randomDouble(-5, 5), RandomUtil.randomDouble(-5, 5), (float) RandomUtil.randomRadian());
//		}
	}

	public void spawnSmallAsteroid(int variant, double x, double y, float rotation)
	{
		SmallAsteroid asteroid = new SmallAsteroid(server);
		asteroid.position.set(x, y);
		asteroid.rotation = rotation;
		asteroid.variant = variant;
		objects.add(asteroid);
		server.sendPacket(new CSpawn(CSpawn.Type.SMALL_ASTEROID, asteroid.position, variant, asteroid.rotation, asteroid.uuid));
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
