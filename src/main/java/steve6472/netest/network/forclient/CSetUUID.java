package steve6472.netest.network.forclient;

import steve6472.netest.client.Client;
import steve6472.netest.network.CPacket;
import steve6472.sge.main.networking.PacketData;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class CSetUUID extends CPacket
{
	private UUID uuid;

	public CSetUUID()
	{

	}

	public CSetUUID(UUID uuid)
	{
		this.uuid = uuid;
	}

	@Override
	public void handlePacket(Client client)
	{
		System.out.println("UUID: " + uuid.toString());
		client.uuid = uuid;
	}

	@Override
	public void output(PacketData output)
	{
		output.writeUUID(uuid);
	}

	@Override
	public void input(PacketData input)
	{
		uuid = input.readUUID();
	}
}
