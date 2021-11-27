package steve6472.netest.client.gfx.particles.particle.appearance;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.joml.Matrix4f;
import org.json.JSONArray;
import org.json.JSONObject;
import steve6472.netest.client.gfx.BillboardParticleShader;
import steve6472.netest.client.gfx.Renderer;
import steve6472.netest.client.gfx.Shaders;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.Val;
import steve6472.netest.client.gfx.particles.Val3;
import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.VertexObjectCreator;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.main.util.MathUtil;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/11/2021
 * Project: VoxWorld
 *
 ***********************/
public class RenderBillboard extends ParticleRender
{
	Val sizeX, sizeY;
	FaceCameraMode mode;
	Val3 customDirection;

	float textureWidth, textureHeight;
	AUV uv;

	// static

	private static final int model;
	private static final float[] textureArray;
	private static final int textureVBO;

	static
	{
		textureArray = new float[] {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0};

		model = VertexObjectCreator.createVAO();
		VertexObjectCreator.storeFloatDataInAttributeList(0, 3, new float[] {-1, 1, 0, -1, -1, 0, 1, -1, 0, 1, -1, 0, 1, 1, 0, -1, 1, 0}); // Vertex
		textureVBO = VertexObjectCreator.storeFloatDataInAttributeList(1, 2, textureArray); // Texture
		VertexObjectCreator.unbindVAO();
	}

	public RenderBillboard(JSONObject json)
	{
		super(json);
		JSONArray size = json.optJSONArray("size");
		if (size == null)
		{
			sizeX = new Val(null, 1);
			sizeY = new Val(null, 1);
		} else
		{
			sizeX = new Val(size.get(0));
			sizeY = new Val(size.get(1));
		}

		JSONObject uv = json.getJSONObject("uv");
		textureWidth = uv.optInt("texture_width", 1);
		textureHeight = uv.optInt("texture_height", 1);

		if (uv.has("flipbook"))
			this.uv = new FlipbookUV(uv);
		else
			this.uv = new StaticUV(uv);

		mode = FaceCameraMode.getMode(json.getString("facing_camera_mode"));

		if (mode.requiresDirection())
		{
			JSONObject direction = json.getJSONObject("direction");
			if (direction != null)
			{
				String mode = direction.getString("mode");

				// cause Snowstorm uses "custom" instead of "custom_direction" as specified in bedrock docs ???
				if (mode.equals("custom") || mode.equals("custom_direction"))
				{
					customDirection = new Val3(direction.getJSONArray("custom_direction"), 0, 0, 0);
				}
			}
		}
	}

	private static final Matrix4f MAT = new Matrix4f();

	@Override
	public void render(MoLangRuntime runtime, Particle particle, Stack stack)
	{
		if (particle.getEmitter().isFirst())
		{
			Shaders.billboardParticleShader.bind(Renderer.getCamera().getViewMatrix());
			StaticTexture.bind(0, particle.getEmitter().getTexture());

			VertexObjectCreator.bindVAO(model);
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);

			uv.transformArray(runtime, particle, textureWidth, textureHeight, textureArray);
			VertexObjectCreator.storeFloatDataInAttributeList(1, 2, textureVBO, textureArray);

			Tinting tinting = particle.getEmitter().getComponent(Tinting.class);
			if (tinting == null)
			{
				Shaders.billboardParticleShader.setUniform(BillboardParticleShader.COLOR, 1, 1, 1, 1);
			}
		}

		Tinting tinting = particle.getEmitter().getComponent(Tinting.class);
		if (tinting != null)
		{
			Shaders.billboardParticleShader.setUniform(BillboardParticleShader.COLOR, tinting.getRed(runtime), tinting.getGreen(runtime), tinting.getBlue(runtime), tinting.getAlpha(runtime));
		}

		MAT.identity();
		MAT.translate((float) particle.getPosition().x, (float) particle.getPosition().y, (float) particle.getPosition().z);

		switch (mode)
		{
			case ROTATE_XYZ -> {
				MAT.rotate(Renderer.getCamera().getYaw(), 0.0f, 1.0f, 0.0f);
				MAT.rotate(Renderer.getCamera().getPitch(), 1.0f, 0.0f, 0.0f);
			}
		}

		MAT.rotateZ((float) Math.toRadians(particle.getRotation()));

		MAT.scaleXY(sizeX.executeF(runtime), sizeY.executeF(runtime));

		Shaders.billboardParticleShader.setTransformation(MAT);

		if (!(uv instanceof StaticUV))
		{
			uv.transformArray(runtime, particle, textureWidth, textureHeight, textureArray);
			VertexObjectCreator.storeFloatDataInAttributeList(1, 2, textureVBO, textureArray);
		}

		glDrawArrays(GL_TRIANGLES, 0, 6);

		if (particle.getEmitter().isLast())
		{
			VertexObjectCreator.unbindVAO();
			glBindVertexArray(0);
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
		}
	}

	private static abstract class AUV
	{
		private AUV()
		{}

		public abstract void transformArray(MoLangRuntime runtime, Particle particle, float tw, float th, float[] uvs);
	}

	private static class StaticUV extends AUV
	{
		Val uvX, uvY;
		Val uvSizeX, uvSizeY;

		private StaticUV(JSONObject uvJson)
		{
			super();
			JSONArray uv = uvJson.getJSONArray("uv");
			JSONArray size = uvJson.getJSONArray("uv_size");
			uvX = new Val(uv.get(0));
			uvY = new Val(uv.get(1));
			uvSizeX = new Val(size.get(0));
			uvSizeY = new Val(size.get(1));
		}

		@Override
		public void transformArray(MoLangRuntime runtime, Particle particle, float tw, float th, float[] uvs)
		{
			float x = uvX.executeF(runtime) / tw;
			float y = uvY.executeF(runtime) / th;

			float sx = uvSizeX.executeF(runtime) / tw;
			float sy = uvSizeY.executeF(runtime) / th;

			uvs[0] = x;
			uvs[1] = y;

			uvs[2] = x;
			uvs[3] = y + sy;

			uvs[4] = x + sx;
			uvs[5] = y + sy;

			uvs[6] = x + sx;
			uvs[7] = y + sy;

			uvs[8] = x + sx;
			uvs[9] = y;

			uvs[10] = x;
			uvs[11] = y;
		}
	}

	private static class FlipbookUV extends AUV
	{
		Val baseUvX, baseUvY;
		float sizeX, sizeY;
		float stepX, stepY;
		float fps;
		Val maxFrame;
		boolean stretchToLifetime;
		boolean loop;

		private FlipbookUV(JSONObject uvJson)
		{
			super();
			JSONObject flipbook = uvJson.getJSONObject("flipbook");

			JSONArray baseUv = flipbook.getJSONArray("base_UV");
			baseUvX = new Val(baseUv.get(0));
			baseUvY = new Val(baseUv.get(1));

			JSONArray size = flipbook.getJSONArray("size_UV");
			sizeX = size.getFloat(0);
			sizeY = size.getFloat(1);

			JSONArray step = flipbook.getJSONArray("step_UV");
			stepX = step.getFloat(0);
			stepY = step.getFloat(1);

			fps = flipbook.optFloat("frames_per_second");
			maxFrame = new Val(flipbook.get("max_frame"));

			stretchToLifetime = flipbook.optBoolean("stretch_to_lifetime");
			loop = flipbook.optBoolean("loop");
		}

		@Override
		public void transformArray(MoLangRuntime runtime, Particle particle, float tw, float th, float[] uvs)
		{
			float baseX = baseUvX.executeF(runtime) / tw;
			float baseY = baseUvY.executeF(runtime) / th;

			float frame = 1;
			if (stretchToLifetime)
				frame = (int) MathUtil.lerp(0, maxFrame.execute(runtime), particle.getLife() / particle.getMaxLifetime());
			baseX += stepX * frame;
			baseY += stepY * frame;

			float x = baseX / tw;
			float y = baseY / th;

			float sx = sizeX / tw;
			float sy = sizeY / th;

			uvs[0] = x;
			uvs[1] = y;

			uvs[2] = x;
			uvs[3] = y + sy;

			uvs[4] = x + sx;
			uvs[5] = y + sy;

			uvs[6] = x + sx;
			uvs[7] = y + sy;

			uvs[8] = x + sx;
			uvs[9] = y;

			uvs[10] = x;
			uvs[11] = y;
		}
	}

	private enum FaceCameraMode
	{
		ROTATE_XYZ("rotate_xyz"), ROTATE_Y("rotate_y"),
		LOOKAT_XYZ("lookat_xyz"), LOOKAT_Y("lookat_y"),
		DIRECTION_X("direction_x"), DIRECTION_Y("direction_y"), DIRECTION_Z("direction_z");

		private static final FaceCameraMode[] VALUES = {ROTATE_XYZ, ROTATE_Y, LOOKAT_XYZ, LOOKAT_Y, DIRECTION_X, DIRECTION_Y, DIRECTION_Z};

		private final String id;

		FaceCameraMode(String id)
		{
			this.id = id;
		}

		public static FaceCameraMode getMode(String id)
		{
			for (FaceCameraMode value : VALUES)
			{
				if (value.id.equals(id))
					return value;
			}

			throw new IllegalArgumentException("'" + id + "' is not valid");
		}

		public boolean requiresDirection()
		{
			return this == DIRECTION_X || this == DIRECTION_Y || this == DIRECTION_Z;
		}
	}
}
