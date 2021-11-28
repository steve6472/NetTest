package steve6472.netest.network.forserver;

import steve6472.netest.network.SPacket;
import steve6472.netest.network.forclient.CSetColor;
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
public class SSetColor extends SPacket
{
	int color;

	public SSetColor(int color)
	{
		this.color = color;
	}

	public SSetColor()
	{
	}

	@Override
	public void handlePacket(Server server)
	{
		ConnectedClient client = server.findConnectedClient(getSender());
		ServerPlayer player = server.findPlayer(client);
		player.color = color;
		server.sendPacketExcept(new CSetColor(color, player.uuid), client);
	}

	@Override
	public void output(PacketData output)
	{
		output.writeInt(color);
	}

	@Override
	public void input(PacketData input)
	{
		color = input.readInt();
	}
}
