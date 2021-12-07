package steve6472.netest.server.objects;

import steve6472.netest.network.forclient.CSpawn;
import steve6472.netest.server.Server;
import steve6472.netest.server.ServerPlayer;
import steve6472.netest.server.ServerSpaceObject;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 12/7/2021
 * Project: NetTest
 *
 ***********************/
public class SResource extends ServerSpaceObject
{
	public SResource(Server server, UUID id)
	{
		super(server, id);
	}

	@Override
	public void tick()
	{
		for (ServerSpaceObject value : server.space.objects.values())
		{
			if (value instanceof ServerPlayer player)
			{
				if (player.position.distance(position) <= 0.5)
				{
					shouldBeRemoved = true;
					player.score++;
				}
			}
		}
	}

	@Override
	public CSpawn.Type type()
	{
		return CSpawn.Type.RESOURCE;
	}
}
