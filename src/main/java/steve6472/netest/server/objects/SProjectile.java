package steve6472.netest.server.objects;

import steve6472.netest.network.forclient.CSpawn;
import steve6472.netest.network.forclient.CUpdatePosition;
import steve6472.netest.server.Server;
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
	float speed;
	int maxLife;
	int life;

	public SProjectile(Server server)
	{
		super(server, UUID.randomUUID());
		speed = 1 / 15f;
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
	}

	@Override
	public CSpawn.Type type()
	{
		return CSpawn.Type.PROJECTILE;
	}
}
