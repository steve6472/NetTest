package steve6472.netest.network.forclient;

import steve6472.netest.client.Client;
import steve6472.netest.network.CPacket;
import steve6472.sge.main.networking.PacketData;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class CRemove extends CPacket
{
	private UUID uuid;

	public CRemove(UUID uuid)
	{
		this.uuid = uuid;
	}

	public CRemove()
	{

	}

	@Override
	public void handlePacket(Client client)
	{
		client.space.objects.removeIf(o -> o.uuid.equals(uuid));
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
