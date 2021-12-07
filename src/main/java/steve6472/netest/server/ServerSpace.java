package steve6472.netest.server;

import net.querz.nbt.tag.CompoundTag;
import steve6472.netest.ISaveable;
import steve6472.netest.network.PacketUtil;
import steve6472.netest.network.forclient.CSpawn;
import steve6472.netest.server.objects.SResource;
import steve6472.netest.server.objects.SmallAsteroid;
import steve6472.sge.main.util.RandomUtil;

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
	private Map<UUID, ServerSpaceObject> objectsToSpawn = new HashMap<>();

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
		PacketUtil.removeEntities(server, toBeRemoved);

		if (server.serverTick == 10)
		{
			createSpace();
		}

		long asteroidCount = objects.values().stream().filter(a -> a instanceof SmallAsteroid).count();
		if (asteroidCount < 8)
			spawnSmallAsteroid(0, RandomUtil.randomDouble(-12, 12), RandomUtil.randomDouble(-12, 12), (float) RandomUtil.randomRadian());

		objectsToSpawn.forEach((k, v) -> objects.put(k, v));
		objectsToSpawn.clear();
	}

	private void createSpace()
	{
		for (int i = 0; i < 8; i++)
		{
			spawnSmallAsteroid(0, RandomUtil.randomDouble(-5, 5), RandomUtil.randomDouble(-5, 5), (float) RandomUtil.randomRadian());
		}
	}

	public void spawnSmallAsteroid(int variant, double x, double y, float rotation)
	{
		SmallAsteroid asteroid = new SmallAsteroid(server);
		asteroid.position.set(x, y);
		asteroid.rotation = rotation;
		asteroid.variant = variant;
		addServerObject(asteroid);
	}

	public void spawnResources(int variant, double x, double y, double radius, int count)
	{
		for (int i = 0; i < count; i++)
		{
			SResource resource = new SResource(server, UUID.randomUUID());
			resource.position.set(x + RandomUtil.randomCosRad() * RandomUtil.randomDouble(0, radius), y + RandomUtil.randomSinRad() * RandomUtil.randomDouble(0, radius));
			resource.variant = variant;
			addServerObject(resource);
		}
	}

	public void addServerObject(ServerSpaceObject object)
	{
		objectsToSpawn.put(object.uuid, object);
		server.sendPacket(new CSpawn(object.type(), object.position, object.variant, object.rotation, object.uuid));
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
