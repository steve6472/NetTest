package steve6472.netest.server;

import steve6472.netest.network.forclient.CPing;
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
	public int lastPing;
	public long pingSent;

	public ServerPlayer(Server server, UUID id, ConnectedClient client)
	{
		super(server, id);
		this.client = client;
	}

	@Override
	public void tick()
	{
		if (lastPing <= 0)
		{
			server.sendPacketToClient(new CPing(), client);
			pingSent = System.nanoTime();
			lastPing = ServerOptions.PING_DELAY;
		} else
		{
			lastPing--;
		}
	}
}
