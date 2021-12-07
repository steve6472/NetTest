package steve6472.netest.server;

import org.joml.Vector2d;
import steve6472.netest.network.forclient.*;
import steve6472.sge.main.networking.ConnectedClient;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class ServerPlayer extends ServerSpaceObject
{
	public final ConnectedClient client;
	public int lastPing;
	public long pingSent;
	public int pingNotReceivedCount;

	public short score;
	public short lastScore;

	public ServerPlayer(Server server, UUID id, ConnectedClient client)
	{
		super(server, id);
		this.client = client;
	}

	@Override
	public void tick()
	{
		if (shouldBeRemoved)
			return;

		if (lastPing <= 0)
		{
			if ((System.nanoTime() - pingSent) / 1_000_000_000 > (ServerOptions.PING_DELAY - 60) / 60)
			{
				pingNotReceivedCount++;

				if (pingNotReceivedCount >= ServerOptions.DISCONNECT_THREASHOLD)
				{
					server.sendPacket(new CRemove(uuid));
					shouldBeRemoved = true;
				}
			}

			server.sendPacketToClient(new CPing(), client);
			pingSent = System.nanoTime();
			lastPing = ServerOptions.PING_DELAY;
		} else
		{
			lastPing--;
		}

		if (lastScore != score)
		{
			lastScore = score;

			server.sendPacket(new CUpdateScore(score));
		}
	}

	public void teleport(double x, double y)
	{
		position.set(x, y);
		server.sendPacket(new CUpdatePosition(new Vector2d(x, y), 0, uuid));
	}

	@Override
	public CSpawn.Type type()
	{
		return CSpawn.Type.PLAYER;
	}
}
