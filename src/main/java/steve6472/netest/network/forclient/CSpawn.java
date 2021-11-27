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
public class CSpawn extends CPacket
{
	private Type type;
	private double x, y;
	private UUID uuid;

	public CSpawn(Type type, Vector2d position, UUID uuid)
	{
		this.type = type;
		this.x = position.x;
		this.y = position.y;
		this.uuid = uuid;
	}

	public CSpawn()
	{

	}

	@Override
	public void handlePacket(Client client)
	{
		if (type == Type.PLAYER)
		{
			OtherPlayer e = new OtherPlayer(uuid);
			e.position.set(x, y);
			client.space.players.add(e);
		}
	}

	@Override
	public void output(PacketData output)
	{
		output.writeInt(type.ordinal());
		output.writeDouble(x);
		output.writeDouble(y);
		output.writeUUID(uuid);
	}

	@Override
	public void input(PacketData input)
	{
		type = Type.values()[input.readInt()];
		x = input.readDouble();
		y = input.readDouble();
		uuid = input.readUUID();
	}

	public enum Type
	{
		PLAYER
	}

	@Override
	public String toString()
	{
		return "CSpawn{" + "type=" + type + ", x=" + x + ", y=" + y + ", uuid=" + uuid + '}';
	}
}
