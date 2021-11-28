package steve6472.netest.server.objects;

import steve6472.netest.network.forclient.CUpdatePosition;
import steve6472.netest.server.Server;
import steve6472.netest.server.ServerSpaceObject;

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

	public SProjectile(Server server)
	{
		super(server, UUID.randomUUID());
		speed = 1 / 15f;
	}

	@Override
	public void tick()
	{
		position.add(speed * -Math.sin(rotation), speed * -Math.cos(rotation));
		server.sendPacket(new CUpdatePosition(position, rotation, uuid));
	}
}
