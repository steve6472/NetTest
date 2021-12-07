package steve6472.netest.network;

import steve6472.netest.network.forclient.CRemove;
import steve6472.netest.server.Server;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 12/7/2021
 * Project: NetTest
 *
 ***********************/
public class PacketUtil
{
	public static void removeEntities(Server server, Collection<UUID> uuids)
	{
		if (uuids.size() == 0)
			return;

		CRemove[] removePackets = new CRemove[uuids.size() / 31 + 1];
		int i = 0;
		Set<UUID> uuidSet = new HashSet<>();
		for (UUID uuid : uuids)
		{
			uuidSet.add(uuid);
			if (uuidSet.size() == 31 || i == uuids.size() - 1)
			{
				removePackets[i / 31] = new CRemove(uuidSet);
				uuidSet.clear();
			}
			i++;
		}
		for (CRemove removePacket : removePackets)
		{
			server.sendPacket(removePacket);
		}
	}
}
