package steve6472.netest.client;

import org.joml.Vector2d;
import steve6472.netest.Main;
import steve6472.netest.network.CPacket;
import steve6472.netest.network.IClientHandler;
import steve6472.netest.network.forserver.SUpdatePosition;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.voxelizer.IModelAccessor;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.networking.Packet;
import steve6472.sge.main.networking.UDPClient;

import java.net.DatagramPacket;
import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class Client extends UDPClient
{
	private final Main main;

	public ClientSpace space;

	public UUID uuid;
	public Vector2d position = new Vector2d();
	public Vector2d motion = new Vector2d();
	public Vector2d acceleration = new Vector2d();
	public Vector2d lastPos = new Vector2d();

	/**
	 * @param ip   Client is gonna connect to this IP
	 * @param port Client is gonna connect to this PORT
	 */
	public Client(String ip, int port)
	{
		super(ip, port);
		start();
		setIPacketHandler(new IClientHandler());
		main = Main.getInstance();
		space = new ClientSpace();
	}

	public void render(Stack stack)
	{
		space.render(stack);
	}

	public void tick()
	{
		acceleration.set(0);
		if (main.isKeyPressed(KeyList.W)) acceleration.y--;
		if (main.isKeyPressed(KeyList.S)) acceleration.y++;
		if (main.isKeyPressed(KeyList.A)) acceleration.x--;
		if (main.isKeyPressed(KeyList.D)) acceleration.x++;

		motion.add(acceleration.div(60));
		position.add(motion);
		motion.mul(0.9);

		if (!lastPos.equals(position))
		{
			lastPos.set(position);
			sendPacket(new SUpdatePosition(position));
		}
	}

	@Override
	public void handlePacket(Packet<?> packet, DatagramPacket sender)
	{
		System.out.println("Client got packet: " + packet);
		if (packet instanceof CPacket sp)
		{
			sp.handlePacket(this);
		} else
		{
			super.handlePacket(packet, sender);
		}
	}
}
