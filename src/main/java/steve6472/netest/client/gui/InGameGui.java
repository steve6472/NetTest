package steve6472.netest.client.gui;

import steve6472.netest.Main;
import steve6472.netest.client.Client;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.Gui;
import steve6472.sge.main.MainApp;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class InGameGui extends Gui
{
	Client client;

	public InGameGui(MainApp mainApp)
	{
		super(mainApp);
	}

	@Override
	public void showEvent()
	{
		if (!isVisible())
			return;

		client = Main.getInstance().client;
	}

	@Override
	public void createGui()
	{

	}

	@Override
	public void guiTick()
	{

	}

	@Override
	public void render()
	{
		Font.render(String.format("Pos: %.2f/%.2f", client.position.x, client.position.y), 5, 5);
		Font.render("Ping: " + client.ping / 1_000_000_000f, 5, 15);
		Font.render("Score: " + client.score, 5, 25);
		Font.render("Recieved packets/s: " + client.recievedPacketCounter.getSecondCounter(), 5, 35);
	}
}
