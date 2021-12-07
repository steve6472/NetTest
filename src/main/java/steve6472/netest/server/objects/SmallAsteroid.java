package steve6472.netest.server.objects;

import steve6472.netest.network.forclient.CSpawn;
import steve6472.netest.network.forclient.CUpdatePosition;
import steve6472.netest.network.forclient.CUpdateScore;
import steve6472.netest.server.Server;
import steve6472.netest.server.ServerPlayer;
import steve6472.netest.server.ServerSpaceObject;
import steve6472.sge.main.util.RandomUtil;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class SmallAsteroid extends ServerSpaceObject
{
	public int variant;
	float speed;
	int b = RandomUtil.randomInt(0, 2);

	public SmallAsteroid(Server server)
	{
		super(server, UUID.randomUUID());
		speed = RandomUtil.randomFloat(0.3f, 0.8f);
	}

	@Override
	public void tick()
	{
		rotation += RandomUtil.randomRadian() * 0.025;
		position.add(speed * Math.sin(rotation) / 60f, speed * Math.cos(rotation) / 60f);

		if (position.distanceSquared(0, 0) > 128)
			position.set(RandomUtil.randomDouble(-12, 12), RandomUtil.randomDouble(-12, 12));

		if (server.serverTick % 3 == b)
		{
			server.sendPacket(new CUpdatePosition(position, rotation, uuid));
		}

		for (ServerSpaceObject value : server.space.objects.values())
		{
			if (value instanceof SProjectile projectile)
			{
				if (projectile.position.distance(position) <= 0.5)
				{
					shouldBeRemoved = true;
					projectile.shouldBeRemoved = true;

					server.space.spawnResources(0, position.x, position.y, 0.2, RandomUtil.randomInt(1, 3));
				}
			}
			if (value instanceof ServerPlayer player)
			{
				if (player.position.distance(position) <= 0.65)
				{
					player.teleport(0, 0);
					player.score = (short) Math.max(0, player.score - 2);
					server.sendPacket(new CUpdateScore(player.score));
					shouldBeRemoved = true;
				}
			}
		}


	}

	@Override
	public CSpawn.Type type()
	{
		return CSpawn.Type.SMALL_ASTEROID;
	}
}
