package steve6472.netest.network.forclient;

import steve6472.netest.client.Client;
import steve6472.netest.network.CPacket;
import steve6472.sge.main.networking.PacketData;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class CUpdatePing extends CPacket
{
	// ping in nanoseconds
	long ping;

	public CUpdatePing(long ping)
	{
		this.ping = ping;
	}

	public CUpdatePing()
	{

	}

	@Override
	public void handlePacket(Client client)
	{
		client.ping = ping;
	}

	@Override
	public void output(PacketData output)
	{
		output.writeLong(ping);
	}

	@Override
	public void input(PacketData input)
	{
		ping = input.readLong();
	}
}
