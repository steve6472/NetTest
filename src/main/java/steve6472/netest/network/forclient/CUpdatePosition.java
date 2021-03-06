package steve6472.netest.network.forclient;

import org.joml.Vector2d;
import steve6472.netest.client.Client;
import steve6472.netest.client.ClientSpaceObject;
import steve6472.netest.network.CPacket;
import steve6472.netest.network.forserver.SRequestObject;
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
	float rotation;

	public CUpdatePosition(Vector2d position, float rotation, UUID uuid)
	{
		this.position = position;
		this.rotation = rotation;
		this.uuid = uuid;
	}

	public CUpdatePosition()
	{

	}

	@Override
	public void handlePacket(Client client)
	{
		if (client.uuid.equals(uuid))
		{
			client.position = position;
			client.rotation = rotation;
		} else
		{
			ClientSpaceObject object = client.space.getObject(uuid);
			if (object == null)
			{
				client.sendPacket(new SRequestObject(uuid));
				return;
			}

			object.position.set(position);
			object.rotation = rotation;
		}

	}

	@Override
	public void output(PacketData output)
	{
		output.writeDouble(position.x);
		output.writeDouble(position.y);
		output.writeShort((short) (rotation * (Short.MAX_VALUE / (Math.PI * 2f))));
		output.writeUUID(uuid);
	}

	@Override
	public void input(PacketData input)
	{
		position = new Vector2d(input.readDouble(), input.readDouble());
		rotation = (float) ((input.readShort() / (float) Short.MAX_VALUE) * (Math.PI * 2f));
		uuid = input.readUUID();
	}

	@Override
	public String toString()
	{
		return "CUpdatePosition{" + "position=" + position + ", uuid=" + uuid + ", rotation=" + rotation + '}';
	}
}
