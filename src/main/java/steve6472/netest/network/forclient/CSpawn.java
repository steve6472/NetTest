package steve6472.netest.network.forclient;

import org.joml.Vector2d;
import steve6472.netest.client.Client;
import steve6472.netest.client.DestructableObject;
import steve6472.netest.client.Models;
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
	private int variant;
	private float rotation;

	/**
	 * @param type - type
	 * @param position - position
	 * @param variant - for Player: color
	 * @param rotation - rotation
	 * @param uuid - UUID
	 */
	public CSpawn(Type type, Vector2d position, int variant, float rotation, UUID uuid)
	{
		this.type = type;
		this.x = position.x;
		this.y = position.y;
		this.variant = variant;
		this.rotation = rotation;
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
			e.rotation = rotation;
			e.color = variant;
			client.space.objects.put(uuid, e);
		} else if (type == Type.SMALL_ASTEROID)
		{
			DestructableObject d = new DestructableObject(uuid, Models.SMALL_ASTEROIDS[variant]);
			d.position.set(x, y);
			d.rotation = rotation;
			client.space.objects.put(uuid, d);
		}
	}

	@Override
	public void output(PacketData output)
	{
		output.writeInt(type.ordinal());
		output.writeDouble(x);
		output.writeDouble(y);
		output.writeInt(variant);
		output.writeShort((short) (rotation * (Short.MAX_VALUE / (Math.PI * 2f))));
		output.writeUUID(uuid);
	}

	@Override
	public void input(PacketData input)
	{
		type = Type.values()[input.readInt()];
		x = input.readDouble();
		y = input.readDouble();
		variant = input.readInt();
		rotation = (float) ((input.readShort() / (float) Short.MAX_VALUE) * (Math.PI * 2f));
		uuid = input.readUUID();
	}

	public enum Type
	{
		PLAYER, SMALL_ASTEROID
	}

	@Override
	public String toString()
	{
		return "CSpawn{" + "type=" + type + ", x=" + x + ", y=" + y + ", uuid=" + uuid + ", variant=" + variant + '}';
	}
}
