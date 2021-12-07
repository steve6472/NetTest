package steve6472.netest.network.forclient;

import steve6472.netest.client.Client;
import steve6472.netest.network.CPacket;
import steve6472.sge.main.networking.PacketData;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class CRemove extends CPacket
{
	private final Set<UUID> uuids = new HashSet<>();

	public CRemove(UUID uuid)
	{
		uuids.add(uuid);
	}

	public CRemove(Set<UUID> uuidSet)
	{
		if (uuidSet.size() > 31)
			throw new IllegalArgumentException("Too many uuids to remove at once!");

		uuids.addAll(uuidSet);
	}

	public CRemove(UUID... uuid)
	{
		if (uuid.length > 31)
			throw new IllegalArgumentException("Too many uuids to remove at once!");

		uuids.addAll(Arrays.asList(uuid));
	}

	public CRemove()
	{

	}

	@Override
	public void handlePacket(Client client)
	{
		for (UUID uuid : uuids)
		{
			client.space.objects.remove(uuid);
		}
	}

	@Override
	public void output(PacketData output)
	{
		output.writeInt(uuids.size());
		for (UUID uuid : uuids)
		{
			output.writeUUID(uuid);
		}
	}

	@Override
	public void input(PacketData input)
	{
		int size = input.readInt();
		while (size-- > 0)
		{
			uuids.add(input.readUUID());
		}
	}
}
