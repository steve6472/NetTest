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
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class SProjectile extends ServerSpaceObject
{
	private final UUID shooter;

	float speed;
	int maxLife;
	int life;

	public SProjectile(Server server, UUID shooter)
	{
		super(server, UUID.randomUUID());
		this.shooter = shooter;
		speed = 1 / 5f;
		maxLife = RandomUtil.randomInt(120, 180);
	}

	@Override
	public void tick()
	{
		position.add(speed * -Math.sin(rotation), speed * -Math.cos(rotation));
		server.sendPacket(new CUpdatePosition(position, rotation, uuid));

		if (life >= maxLife)
			shouldBeRemoved = true;

		life++;


		for (ServerSpaceObject value : server.space.objects.values())
		{
			if (value instanceof ServerPlayer player)
			{
				if (player.uuid.equals(shooter))
					continue;

				if (player.position.distance(position) <= 0.5)
				{
					shouldBeRemoved = true;
					player.teleport(0, 0);
					player.score = (short) Math.max(player.score - 5, 0);
					server.sendPacket(new CUpdateScore(player.score));
				}
			}
		}
	}

	@Override
	public CSpawn.Type type()
	{
		return CSpawn.Type.PROJECTILE;
	}
}
