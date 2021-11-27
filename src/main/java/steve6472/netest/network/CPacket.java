package steve6472.netest.network;

import steve6472.netest.client.Client;
import steve6472.sge.main.networking.Packet;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public abstract class CPacket extends Packet<IClientHandler>
{
	@Override
	public void handlePacket(IClientHandler handler)
	{

	}

	public abstract void handlePacket(Client client);
}
