package steve6472.netest.server;

import steve6472.sge.main.networking.ConnectedClient;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class ServerPlayer extends ServerSpaceObject
{
	public final ConnectedClient client;

	public ServerPlayer(Server server, UUID id, ConnectedClient client)
	{
		super(server, id);
		this.client = client;
	}

	@Override
	public void tick()
	{

	}
}
