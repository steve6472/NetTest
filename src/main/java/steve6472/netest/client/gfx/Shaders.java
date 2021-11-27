package steve6472.netest.client.gfx;

import org.joml.Matrix4f;
import steve6472.netest.client.ClientOptions;
import steve6472.sge.gfx.shaders.BBShader;
import steve6472.sge.gfx.shaders.DialogShader;
import steve6472.sge.gfx.shaders.PlainColorShader;
import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.WindowSizeEvent;
import steve6472.sge.main.util.MathUtil;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/17/2021
 * Project: VoxWorld
 *
 ***********************/
public class Shaders
{
	public static final Matrix4f projectionMatrix = new Matrix4f();

	public static PlainColorShader plainColorShader;
	public static BBShader modelShader;
	public static DialogShader dialogShader;
	public static CubeShader cubeShader;
	public static BillboardParticleShader billboardParticleShader;

	public static void init()
	{
		plainColorShader = new PlainColorShader();

		modelShader = new BBShader();
		modelShader.bind();
		modelShader.setUniform(BBShader.ATLAS, 0);

		dialogShader = new DialogShader();
		dialogShader.bind();
		dialogShader.setUniform(DialogShader.SAMPLER, 0);

		cubeShader = new CubeShader();

		billboardParticleShader = new BillboardParticleShader();
		billboardParticleShader.bind();
		billboardParticleShader.setUniform(BillboardParticleShader.TEXTURE, 0);
	}

	@Event
	public void resizeWindow(WindowSizeEvent e)
	{
		projectionMatrix.set(MathUtil.createProjectionMatrix(e.getWidth(), e.getHeight(), ClientOptions.FAR_PLANE, ClientOptions.FOV));

		plainColorShader.getShader().bind();
		plainColorShader.setProjection(projectionMatrix);

		modelShader.getShader().bind();
		modelShader.setProjection(projectionMatrix);

		dialogShader.getShader().bind();
		dialogShader.setProjection(projectionMatrix);

		cubeShader.getShader().bind();
		cubeShader.setProjection(projectionMatrix);

		billboardParticleShader.getShader().bind();
		billboardParticleShader.setProjection(projectionMatrix);
	}
}
