package steve6472.netest;

import steve6472.netest.client.Client;
import steve6472.netest.client.Models;
import steve6472.netest.client.gfx.Renderer;
import steve6472.netest.client.gfx.Shaders;
import steve6472.netest.client.gui.InGameGui;
import steve6472.netest.client.gui.MainMenu;
import steve6472.netest.network.forclient.*;
import steve6472.netest.network.forserver.*;
import steve6472.netest.server.Server;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.MainFlags;
import steve6472.sge.main.Window;
import steve6472.sge.main.networking.Packets;

import static org.lwjgl.opengl.GL11.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class Main extends MainApp
{
	private static Main INSTANCE;
	private long tickCounter;

	public Server server;
	public Client client;

	private Renderer renderer;
	private MainMenu mainMenu;
	public InGameGui inGameGui;

	@Override
	public void init()
	{
		Window.enableVSync(true);
		glEnable(GL_CULL_FACE);

		INSTANCE = this;

		registerPackets();

		addGui(mainMenu = new MainMenu(this));
		addGui(inGameGui = new InGameGui(this));

		Shaders.init();
		this.renderer = new Renderer(this);

		Models.init();
		renderer.finish();

		getEventHandler().register(new Shaders());
	}

	protected void registerPackets()
	{
		Packets.registerPacket(CSetUUID::new);
		Packets.registerPacket(CSpawn::new);
		Packets.registerPacket(CUpdatePosition::new);
		Packets.registerPacket(CPing::new);
		Packets.registerPacket(CUpdatePing::new);
		Packets.registerPacket(CRemove::new);
		Packets.registerPacket(CSetColor::new);
		Packets.registerPacket(CUpdateMaxShootDelay::new);

		Packets.registerPacket(SUpdatePosition::new);
		Packets.registerPacket(SPong::new);
		Packets.registerPacket(SSetColor::new);
		Packets.registerPacket(SShootProjectile::new);
		Packets.registerPacket(SRequestObject::new);
	}

	public Renderer getRenderer()
	{
		return renderer;
	}

	public static Main getInstance()
	{
		return INSTANCE;
	}

	public static long getTicksCounter()
	{
		return getInstance().tickCounter;
	}

	@Override
	public void tick()
	{
		tickGui();

		if (server != null)
			server.tick();
		if (client != null)
			client.tick();

		tickCounter++;
	}

	@Override
	public void render()
	{
		renderer.render();

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		renderGui();
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
	}

	@Override
	public void setWindowHints()
	{
		Window.setResizable(false);
		//TODO: remove in prod.
		Window.setFloating(true);
	}

	@Override
	public int getWindowWidth()
	{
		return 16 * 50;
	}

	@Override
	public int getWindowHeight()
	{
		return 9 * 50;
	}

	@Override
	public void exit()
	{

	}

	@Override
	public String getTitle()
	{
		return "Netest";
	}

	@Override
	protected int[] getFlags()
	{
		return new int[] {MainFlags.ENABLE_GL_DEPTH_TEST, MainFlags.ADD_BASIC_ORTHO};
	}

	public static void main(String[] args)
	{
		new Main();
	}
}
