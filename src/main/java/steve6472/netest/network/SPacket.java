package steve6472.netest.network;

import steve6472.netest.server.Server;
import steve6472.sge.main.networking.Packet;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public abstract class SPacket extends Packet<IServerHandler>
{
	@Override
	public void handlePacket(IServerHandler handler)
	{

	}

	public abstract void handlePacket(Server server);
}
