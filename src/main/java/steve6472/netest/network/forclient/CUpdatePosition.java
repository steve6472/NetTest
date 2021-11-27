package steve6472.netest.network.forclient;

import org.joml.Vector2d;
import steve6472.netest.client.Client;
import steve6472.netest.client.OtherPlayer;
import steve6472.netest.network.CPacket;
import steve6472.sge.main.networking.PacketData;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class CUpdatePosition extends CPacket
{
	Vector2d position;
	UUID uuid;

	public CUpdatePosition(Vector2d position, UUID uuid)
	{
		this.position = position;
		this.uuid = uuid;
	}

	public CUpdatePosition()
	{

	}

	@Override
	public void handlePacket(Client client)
	{
		for (OtherPlayer player : client.space.players)
		{
			if (player.id.equals(uuid))
				player.position.set(position);
		}
	}

	@Override
	public void output(PacketData output)
	{
		output.writeDouble(position.x);
		output.writeDouble(position.y);
		output.writeUUID(uuid);
	}

	@Override
	public void input(PacketData input)
	{
		position = new Vector2d(input.readDouble(), input.readDouble());
		uuid = input.readUUID();
	}

	@Override
	public String toString()
	{
		return "CUpdatePosition{" + "position=" + position + ", uuid=" + uuid + '}';
	}
}
