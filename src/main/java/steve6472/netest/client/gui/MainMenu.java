package steve6472.netest.client.gui;

import steve6472.netest.Main;
import steve6472.netest.client.Client;
import steve6472.netest.network.forserver.SSetColor;
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
		ip.setSize(300, 30);
		ip.setFontSize(2);
		ip.setText("localhost");
		ip.endCarret();
		addComponent(ip);

		Button localtonet = new Button("LTN");
		localtonet.setLocation(320, 10);
		localtonet.setSize(100, 30);
		localtonet.setFontSize(2);
		localtonet.addClickEvent(c ->
		{
			ip.setText("tr1.localtonet.com");
			ip.endCarret();
		});
		addComponent(localtonet);

		TextField port = new TextField();
		port.setLocation(10, 50);
		port.setSize(200, 30);
		port.setFontSize(2);
		port.setText("36210");
		port.endCarret();
		addComponent(port);

		SliderColorSelectComp color = new SliderColorSelectComp(0xffffff);
		addComponent(color);
		color.setLocation(10, 200);

		Button connect = new Button("Connect");
		connect.setLocation(10, 90);
		connect.setSize(200, 30);
		connect.setFontSize(2);
		connect.addClickEvent(c ->
		{
			Main.getInstance().client = new Client(ip.getText(), Integer.parseInt(port.getText()));
			Main.getInstance().client.connect();
			setVisible(false);
			Main.getInstance().inGameGui.setVisible(true);
			Main.getInstance().client.sendPacket(new SSetColor(color.getColor()));
			Main.getInstance().client.color = color.getColor();
		});
		addComponent(connect);

		Button host = new Button("Host");
		host.setLocation(10, 130);
		host.setSize(200, 30);
		host.setFontSize(2);
		host.addClickEvent(c ->
		{
			int thePort = Integer.parseInt(port.getText());
			Main.getInstance().server = new Server(thePort);
			Main.getInstance().client = new Client("localhost", thePort);
			Main.getInstance().client.connect();
			setVisible(false);
			Main.getInstance().inGameGui.setVisible(true);
			Main.getInstance().client.sendPacket(new SSetColor(color.getColor()));
			Main.getInstance().client.color = color.getColor();
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
