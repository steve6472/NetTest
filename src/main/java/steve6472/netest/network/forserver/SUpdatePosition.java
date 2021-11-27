package steve6472.netest.network.forserver;

import org.joml.Vector2d;
import steve6472.netest.network.SPacket;
import steve6472.netest.network.forclient.CUpdatePosition;
import steve6472.netest.server.ServerPlayer;
import steve6472.netest.server.Server;
import steve6472.sge.main.networking.ConnectedClient;
import steve6472.sge.main.networking.PacketData;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class SUpdatePosition extends SPacket
{
	Vector2d position;
	float rotation;

	public SUpdatePosition(Vector2d position, float rotation)
	{
		this.position = position;
		this.rotation = rotation;
	}

	public SUpdatePosition()
	{

	}

	@Override
	public void handlePacket(Server server)
	{
		ConnectedClient connectedClient = server.findConnectedClient(getSender());
		ServerPlayer player = server.findPlayer(connectedClient);
		player.position.set(position);
		player.rotation = rotation;
		server.sendPacketExcept(new CUpdatePosition(position, rotation, player.id), connectedClient);
	}

	@Override
	public void output(PacketData output)
	{
		output.writeDouble(position.x);
		output.writeDouble(position.y);
		output.writeShort((short) (rotation * (Short.MAX_VALUE / (Math.PI * 2f))));
	}

	@Override
	public void input(PacketData input)
	{
		position = new Vector2d(input.readDouble(), input.readDouble());
		rotation = (float) ((input.readShort() / (float) Short.MAX_VALUE) * (Math.PI * 2f));
	}

	@Override
	public String toString()
	{
		return "SUpdatePosition{" + "position=" + position + ", rotation=" + rotation + '}';
	}
}
