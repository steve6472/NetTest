package steve6472.netest.network.forserver;

import steve6472.netest.network.SPacket;
import steve6472.netest.network.forclient.CSpawn;
import steve6472.netest.server.Server;
import steve6472.netest.server.ServerPlayer;
import steve6472.netest.server.ServerSpaceObject;
import steve6472.sge.main.networking.PacketData;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class SRequestObject extends SPacket
{
	UUID uuid;

	public SRequestObject(UUID uuid)
	{
		this.uuid = uuid;
	}

	public SRequestObject()
	{
	}

	@Override
	public void handlePacket(Server server)
	{
		ServerSpaceObject object = server.space.objects.get(uuid);

		int var = 0;
		if (object instanceof ServerPlayer sp)
			var = sp.color;

		server.sendPacketToClient(new CSpawn(object.type(), object.position, var, object.rotation, object.uuid), server.findConnectedClient(getSender()));
	}

	@Override
	public void output(PacketData output)
	{
		output.writeUUID(uuid);
	}

	@Override
	public void input(PacketData input)
	{
		uuid = input.readUUID();
	}
}
