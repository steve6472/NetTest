package steve6472.netest.server;

import steve6472.netest.network.IServerHandler;
import steve6472.netest.network.SPacket;
import steve6472.netest.network.forclient.CSetUUID;
import steve6472.netest.network.forclient.CSpawn;
import steve6472.sge.main.networking.*;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class Server extends UDPServer
{
	public ServerSpace space;
	public long serverTick;

	//36210
	public Server(int port)
	{
		super(port);
		try
		{
			System.out.println("Started server on port " + port + " Ip: " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		setIPacketHandler(new IServerHandler());
		start();

		space = new ServerSpace(this);
	}

	public void tick()
	{
		space.tick();
		serverTick++;
	}

	@Override
	public void clientConnectEvent(ConnectedClient client)
	{
		System.out.println("Client connected " + client.getIp() + ":" + client.getPort() + " Total clients: " + getClientCount());
		UUID uuid = UUID.randomUUID();
		ServerPlayer player = new ServerPlayer(this, uuid, client);
		sendPacketToClient(new CSetUUID(uuid), client);
		space.objects.put(uuid, player);

		space.objects.forEach((u, o) ->
		{
			if (!u.equals(uuid))
				sendPacketToClient(new CSpawn(o.type(), o.position, o.variant, o.rotation, o.uuid), client);
		});

		sendPacketExcept(new CSpawn(CSpawn.Type.PLAYER, player.position, player.variant, player.rotation, player.uuid), client);
	}

	@Override
	public void clientDisconnectEvent(ConnectedClient client)
	{
		System.out.println("Client disconnected " + client.getIp() + ":" + client.getPort() + " Total clients: " + getClientCount());
	}

	@Override
	public void handlePacket(Packet<? extends IPacketHandler> packet, DatagramPacket sender)
	{
		System.out.println("Server got packet: " + packet);
		if (packet instanceof SPacket sp)
		{
			sp.setSender(sender);
			sp.handlePacket(this);
		} else
		{
			super.handlePacket(packet, sender);
		}
	}

	public final void sendPacketToClient(Packet<? extends IPacketHandler> packet, ConnectedClient client)
	{
		sendData(Packets.packetToByteArray(packet), client.getIp(), client.getPort());
	}

	public final void sendPacketExcept(Packet<? extends IPacketHandler> packet, ConnectedClient exceptClient)
	{
		for (ConnectedClient client : clients)
		{
			if (client == exceptClient)
				continue;
			sendPacketToClient(packet, client);
		}
	}

	public ServerPlayer findPlayer(ConnectedClient client)
	{
		Optional<ServerPlayer> first = space.objects
			.values()
			.stream()
			.filter(o -> o instanceof ServerPlayer)
			.map(o -> (ServerPlayer) o)
			.filter(p -> p.client == client)
			.findFirst();

		return first.orElse(null);
	}

	public ConnectedClient findConnectedClient(DatagramPacket packet)
	{
		int port = packet.getPort();
		InetAddress address = packet.getAddress();

		for (ConnectedClient client : clients)
		{
			if (client.getPort() == port && client.getIp().equals(address))
			{
				return client;
			}
		}

		return null;
	}
}
