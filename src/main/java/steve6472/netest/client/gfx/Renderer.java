package steve6472.netest.client.gfx;

import org.lwjgl.opengl.GL11;
import steve6472.netest.Main;
import steve6472.netest.client.Client;
import steve6472.netest.client.Models;
import steve6472.netest.client.gfx.particles.Emitter;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.blockbench.model.ModelRepository;
import steve6472.sge.gfx.game.stack.RenderType;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.stack.tess.AbstractTess;
import steve6472.sge.gfx.game.stack.tess.BBTess;
import steve6472.sge.gfx.game.stack.tess.LineTess;
import steve6472.sge.gfx.game.stack.tess.TriangleTess;
import steve6472.sge.gfx.shaders.StaticShaderBase;
import steve6472.sge.main.game.Camera;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class Renderer
{
	private final Main main;
	private final Stack stack;
	private final ModelRepository repository;
	private final Camera camera;
	private final HashMap<Integer, Emitter> emitters;

	public Renderer(Main main)
	{
		this.main = main;
		stack = new Stack();
		setupStack();

		repository = new ModelRepository();
		camera = new Camera();
		emitters = new HashMap<>();
	}

	public void render()
	{
		Client client = Main.getInstance().client;
		if (client == null)
			return;

		camera.setPosition((float) client.position.x, 0, (float) client.position.y);
		camera.setPitch((float) (Math.PI / -2f));
		camera.setYaw(0);
		camera.calculateOrbit(8);

		glViewport(0, 0, main.getWidth(), main.getHeight());

		stack.reset();
		stack.clear();

		camera.updateViewMatrix();

		LineTess line = stack.getTess(LineTess.class, "line");
		line.axisGizmo(0.5f, 0.05f, 0.25f);
		stack.getTess(TriangleTess.class, "triangle").axisGizmo(0.5f, 0.05f, 0.1f);

		// render here
		// render client's ship
		stack.pushMatrix().translate((float) client.position.x, 0, (float) client.position.y);
		Models.SHIP.render(stack);
		Models.SHIP_COLORED.render(stack);
		stack.popMatrix();
		client.render(stack);

		Shaders.modelShader.bind(camera.getViewMatrix());
		repository.getAtlasTexture().bind();

		stack.render(camera.getViewMatrix());

		emitters.forEach((i, e) -> e.render(stack));
	}

	private void setupStack()
	{
		stack.addRenderType("blockbench", new RenderType(Shaders.modelShader, new BBTess(stack, 1024 * 64), (StaticShaderBase s, AbstractTess t) -> {

			repository.getAtlasTexture().bind();

			t.draw(GL11.GL_TRIANGLES);
		}));

		stack.addRenderType("line", new RenderType(Shaders.plainColorShader, new LineTess(stack, 1024 * 512), (StaticShaderBase s, AbstractTess t) -> t.draw(GL11.GL_LINES)));
		stack.addRenderType("line_no_depth", new RenderType(Shaders.plainColorShader, new LineTess(stack, 1024 * 32), (StaticShaderBase s, AbstractTess t) ->
		{
			glDisable(GL_DEPTH_TEST);
			t.draw(GL11.GL_LINES);
			glEnable(GL_DEPTH_TEST);
		}));
		stack.addRenderType("triangle", new RenderType(Shaders.plainColorShader, new TriangleTess(stack, 1024 * 64), (StaticShaderBase s, AbstractTess t) -> t.draw(GL11.GL_TRIANGLES)));
		stack.addRenderType("cube", new RenderType(Shaders.cubeShader, new CubeTess(1024 * 32), (StaticShaderBase s, AbstractTess t) -> t.draw(GL_POINTS)));
	}

	public void finish()
	{
		repository.finish();
	}

	public static BBModel addModel(String path)
	{
		return Main.getInstance().getRenderer().repository.loadModel("game/models/" + path);
	}

	public static Camera getCamera()
	{
		return Main.getInstance().getRenderer().camera;
	}
}
