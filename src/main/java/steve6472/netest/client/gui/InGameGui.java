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
		Font.render("UUID: " + client.uuid, 5, 15);
	}
}
