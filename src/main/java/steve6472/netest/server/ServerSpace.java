package steve6472.netest.server;

import net.querz.nbt.tag.CompoundTag;
import steve6472.netest.ISaveable;
import steve6472.netest.network.forclient.CSpawn;
import steve6472.netest.server.objects.SmallAsteroid;

import java.util.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class ServerSpace implements ISaveable
{
	private final Server server;

	public Map<UUID, ServerSpaceObject> objects = new HashMap<>();

	public ServerSpace(Server server)
	{
		this.server = server;
	}

	public void tick()
	{
		Set<UUID> toBeRemoved = new HashSet<>();
		objects.forEach((u, o) ->
		{
			if (o.shouldBeRemoved)
				toBeRemoved.add(u);
			else
				o.tick();
		});
		toBeRemoved.forEach(u -> objects.remove(u));

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
		objects.put(asteroid.uuid, asteroid);
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
