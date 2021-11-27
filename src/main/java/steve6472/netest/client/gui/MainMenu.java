package steve6472.netest.client.gui;

import steve6472.netest.Main;
import steve6472.netest.client.Client;
import steve6472.netest.server.Server;
import steve6472.sge.gui.Gui;
import steve6472.sge.gui.components.Background;
import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.TextField;
import steve6472.sge.main.MainApp;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class MainMenu extends Gui
{
	public MainMenu(MainApp mainApp)
	{
		super(mainApp);
		setVisible(true);
	}

	@Override
	public void createGui()
	{
		Background.createComponent(this);

		TextField ip = new TextField();
		ip.setLocation(10, 10);
		ip.setSize(100, 20);
		ip.setText("localhost");
		addComponent(ip);

		TextField port = new TextField();
		port.setLocation(10, 40);
		port.setSize(100, 20);
		port.setText("36210");
		addComponent(port);

		Button connect = new Button("Connect");
		connect.setLocation(10, 70);
		connect.setSize(100, 20);
		connect.addClickEvent(c ->
		{
			Main.getInstance().client = new Client(ip.getText(), Integer.parseInt(port.getText()));
			Main.getInstance().client.connect();
			setVisible(false);
			Main.getInstance().inGameGui.setVisible(true);
		});
		addComponent(connect);

		Button host = new Button("Host");
		host.setLocation(10, 100);
		host.setSize(100, 20);
		host.addClickEvent(c ->
		{
			int thePort = Integer.parseInt(port.getText());
			Main.getInstance().server = new Server(thePort);
			Main.getInstance().client = new Client("localhost", thePort);
			Main.getInstance().client.connect();
			setVisible(false);
			Main.getInstance().inGameGui.setVisible(true);
		});
		addComponent(host);
	}

	@Override
	public void guiTick()
	{
	}

	@Override
	public void render()
	{

	}
}
