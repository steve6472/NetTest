package steve6472.netest.network.forclient;

import steve6472.netest.client.Client;
import steve6472.netest.network.CPacket;
import steve6472.netest.network.forserver.SPong;
import steve6472.sge.main.networking.PacketData;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class CPing extends CPacket
{
	public CPing()
	{

	}

	@Override
	public void handlePacket(Client client)
	{
		client.sendPacket(new SPong());
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
