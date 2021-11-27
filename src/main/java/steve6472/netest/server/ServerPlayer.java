package steve6472.netest.server;

import org.joml.Vector2d;
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

	public ServerPlayer(UUID id, ConnectedClient client)
	{
		super(id);
		this.client = client;
		position = new Vector2d();
	}

	@Override
	public void tick()
	{

	}
}
