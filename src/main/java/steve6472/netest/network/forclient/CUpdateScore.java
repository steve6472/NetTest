package steve6472.netest.network.forclient;

import steve6472.netest.client.Client;
import steve6472.netest.network.CPacket;
import steve6472.sge.main.networking.PacketData;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 12/7/2021
 * Project: NetTest
 *
 ***********************/
public class CUpdateScore extends CPacket
{
	short score;

	public CUpdateScore(short score)
	{
		this.score = score;
	}

	public CUpdateScore()
	{
	}

	@Override
	public void handlePacket(Client client)
	{
		client.score = score;
	}

	@Override
	public void output(PacketData output)
	{
		output.writeShort(score);
	}

	@Override
	public void input(PacketData input)
	{
		this.score = input.readShort();
	}
}
