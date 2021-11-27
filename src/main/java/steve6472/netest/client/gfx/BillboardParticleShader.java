package steve6472.netest.client.gfx;

import steve6472.sge.gfx.shaders.StaticShader3D;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 10/2/2021
 * Project: VoxWorld
 *
 ***********************/
public class BillboardParticleShader extends StaticShader3D
{
	public static Type TEXTURE, COLOR;

	public BillboardParticleShader()
	{
		super("game/particle/shader");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("tex", TEXTURE = new Type(EnumUniformType.INT_1));
		addUniform("color", COLOR = new Type(EnumUniformType.FLOAT_4));
	}
}
