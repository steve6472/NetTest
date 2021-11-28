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
public class CUpdateMaxShootDelay extends CPacket
{
	long delay;

	@Override
	public void handlePacket(Client client)
	{
		client.maxShootDelay = delay;
	}

	@Override
	public void output(PacketData output)
	{
		output.writeLong(delay);
	}

	@Override
	public void input(PacketData input)
	{
		delay = input.readLong();
	}
}
