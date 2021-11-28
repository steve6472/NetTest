package steve6472.netest.client;

import steve6472.netest.client.gfx.Renderer;
import steve6472.sge.gfx.game.blockbench.model.BBModel;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class Models
{
	public static final BBModel SHIP = Renderer.addModel("ship");
	public static final BBModel SHIP_COLORED = Renderer.addModel("ship_colored");
	public static final BBModel PROJECTILE = Renderer.addModel("projectile");

	// Asteroids
	public static final BBModel SMALL_ASTEROID_01 = Renderer.addModel("small_asteroid_01");

	public static final BBModel[] SMALL_ASTEROIDS = {SMALL_ASTEROID_01};

	public static void init()
	{

	}
}
