package steve6472.netest.network.forserver;

import steve6472.netest.network.SPacket;
import steve6472.netest.network.forclient.CSpawn;
import steve6472.netest.server.Server;
import steve6472.netest.server.ServerPlayer;
import steve6472.netest.server.objects.SProjectile;
import steve6472.sge.main.networking.ConnectedClient;
import steve6472.sge.main.networking.PacketData;
import steve6472.sge.main.util.RandomUtil;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class SShootProjectile extends SPacket
{
	private int variant;
	private float rotation;

	public SShootProjectile(int variant, float rotation)
	{
		this.variant = variant;
		this.rotation = rotation;
	}

	public SShootProjectile()
	{
	}

	@Override
	public void handlePacket(Server server)
	{
		ConnectedClient client = server.findConnectedClient(getSender());
		ServerPlayer player = server.findPlayer(client);

		SProjectile projectile = new SProjectile(server, player.uuid);
		double correction = RandomUtil.flipACoin() ? Math.toRadians(15) : -Math.toRadians(15);
		projectile.position.set(player.position.x - Math.sin(player.rotation + correction) * 0.6, player.position.y - Math.cos(player.rotation + correction) * 0.6);
		projectile.rotation = rotation;
		server.space.objects.put(projectile.uuid, projectile);
		server.sendPacket(new CSpawn(CSpawn.Type.PROJECTILE, projectile.position, 0, projectile.rotation, projectile.uuid));
	}

	@Override
	public void output(PacketData output)
	{
		output.writeInt(variant);
		output.writeFloat(rotation);
	}

	@Override
	public void input(PacketData input)
	{
		variant = input.readInt();
		rotation = input.readFloat();
	}
}
