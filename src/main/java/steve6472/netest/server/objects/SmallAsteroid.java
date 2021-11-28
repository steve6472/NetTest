package steve6472.netest.server.objects;

import steve6472.netest.network.forclient.CSpawn;
import steve6472.netest.network.forclient.CUpdatePosition;
import steve6472.netest.server.Server;
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

		if (server.serverTick % 3 == b)
		{
			server.sendPacket(new CUpdatePosition(position, rotation, uuid));
		}
	}

	@Override
	public CSpawn.Type type()
	{
		return CSpawn.Type.SMALL_ASTEROID;
	}
}
