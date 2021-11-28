package steve6472.netest.client;

import org.joml.Vector2d;
import steve6472.netest.Main;
import steve6472.netest.network.CPacket;
import steve6472.netest.network.IClientHandler;
import steve6472.netest.network.forserver.SUpdatePosition;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.stack.tess.BBTess;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.networking.Packet;
import steve6472.sge.main.networking.UDPClient;
import steve6472.sge.main.util.Pair;

import java.net.DatagramPacket;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class Client extends UDPClient
{
	private final Main main;
	private final BlockingQueue<Pair<Packet<?>, DatagramPacket>> packetQueue;

	public ClientSpace space;

	public long ping;

	/*
	 * Player
	 */

	public UUID uuid;
	public Vector2d position = new Vector2d();
	public Vector2d motion = new Vector2d();
	public Vector2d lastPos = new Vector2d();
	public int color;

	public float rotation;
	public float rotationMot;
	public double lastSpeed;

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
		packetQueue = new LinkedBlockingQueue<>();
	}

	public void render(Stack stack)
	{
		space.render(stack);

		stack.pushMatrix();
		stack.translate((float) position.x, 0, (float) position.y);
		stack.rotateY(rotation);
		BBTess blockbench = stack.getTess(BBTess.class, "blockbench");
		blockbench.color(color);
		Models.SHIP.render(stack);
		blockbench.color(0xffffffff);
		Models.SHIP_COLORED.render(stack);
		stack.popMatrix();
	}

	public void tick()
	{
		handlePackets();

		float acceleration = 0;
		if (main.isKeyPressed(KeyList.W)) acceleration--;
		if (main.isKeyPressed(KeyList.S)) acceleration++;

		acceleration /= 60f;
		motion.add(acceleration * Math.sin(rotation), acceleration * Math.cos(rotation));
		lastSpeed = position.distance(position.x + motion.x, position.y + motion.y);
		position.add(motion);
		motion.mul(0.9);

		float rotAcceleration = 0;
		if (main.isKeyPressed(KeyList.A)) rotAcceleration++;
		if (main.isKeyPressed(KeyList.D)) rotAcceleration--;

		rotationMot += rotAcceleration / 60;
		rotation += rotationMot;
		rotationMot *= 0.8;

		rotation = (float) (rotation % (Math.PI * 2f));

		if (!lastPos.equals(position))
		{
			lastPos.set(position);
			sendPacket(new SUpdatePosition(position, rotation));
		}
	}

	private void handlePackets()
	{
		while (!packetQueue.isEmpty())
		{
			Pair<Packet<?>, DatagramPacket> packetPair;
			try
			{
				packetPair = packetQueue.take();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
				continue;
			}

			Packet<?> packet = packetPair.a();
			if (packet instanceof CPacket sp)
			{
				sp.handlePacket(this);
			} else
			{
				super.handlePacket(packet, packetPair.b());
			}
		}
	}

	@Override
	public void handlePacket(Packet<?> packet, DatagramPacket sender)
	{
		System.out.println("Client got packet: " + packet);
		try
		{
			packetQueue.put(new Pair<>(packet, sender));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
