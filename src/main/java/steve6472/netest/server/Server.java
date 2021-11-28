package steve6472.netest.server;

import steve6472.netest.network.IServerHandler;
import steve6472.netest.network.SPacket;
import steve6472.netest.network.forclient.CSetUUID;
import steve6472.netest.network.forclient.CSpawn;
import steve6472.sge.main.networking.*;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class Server extends UDPServer
{
	ServerSpace space;
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
		space.players.add(player);

		for (ServerPlayer serverPlayer : space.players)
		{
			if (serverPlayer.client == client)
			{
				continue;
			}

			sendPacketToClient(new CSpawn(CSpawn.Type.PLAYER, serverPlayer.position, serverPlayer.color, serverPlayer.rotation, serverPlayer.uuid), client);
		}

		sendPacketExcept(new CSpawn(CSpawn.Type.PLAYER, player.position, player.color, player.rotation, player.uuid), client);
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
		for (ServerPlayer player : space.players)
		{
			if (player.client == client)
				return player;
		}

		return null;
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
