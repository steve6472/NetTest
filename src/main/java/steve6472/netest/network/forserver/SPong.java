package steve6472.netest.network.forserver;

import steve6472.netest.network.SPacket;
import steve6472.netest.network.forclient.CUpdatePing;
import steve6472.netest.server.Server;
import steve6472.netest.server.ServerPlayer;
import steve6472.sge.main.networking.ConnectedClient;
import steve6472.sge.main.networking.PacketData;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class SPong extends SPacket
{
	public SPong()
	{

	}

	@Override
	public void handlePacket(Server server)
	{
		long time = System.nanoTime();
		ConnectedClient connectedClient = server.findConnectedClient(getSender());
		ServerPlayer player = server.findPlayer(connectedClient);
		long ping = time - player.pingSent;
		player.pingNotReceivedCount = 0;
		server.sendPacketToClient(new CUpdatePing(ping), connectedClient);
	}

	@Override
	public void output(PacketData output)
	{

	}

	@Override
	public void input(PacketData input)
	{

	}
}
