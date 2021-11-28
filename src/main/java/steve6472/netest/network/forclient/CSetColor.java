package steve6472.netest.network.forclient;

import steve6472.netest.client.Client;
import steve6472.netest.client.ClientSpaceObject;
import steve6472.netest.client.objects.OtherPlayer;
import steve6472.netest.network.CPacket;
import steve6472.sge.main.networking.PacketData;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class CSetColor extends CPacket
{
	UUID uuid;
	int color;

	public CSetColor(int color, UUID uuid)
	{
		this.color = color;
		this.uuid = uuid;
	}

	public CSetColor()
	{
	}

	@Override
	public void handlePacket(Client client)
	{
		ClientSpaceObject object = client.space.getObject(uuid);
		if (!(object instanceof OtherPlayer player))
		{
			throw new IllegalStateException("ClientSpaceObject with uuid '" + uuid + "' is not Player!");
		} else
		{
			player.color = color;
		}
	}

	@Override
	public void output(PacketData output)
	{
		output.writeInt(color);
		output.writeUUID(uuid);
	}

	@Override
	public void input(PacketData input)
	{
		color = input.readInt();
		uuid = input.readUUID();
	}
}
