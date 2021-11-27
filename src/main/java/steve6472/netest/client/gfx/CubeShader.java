package steve6472.netest.client.gfx;

import steve6472.sge.gfx.shaders.StaticGeometryShader3D;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 10/2/2021
 * Project: VoxWorld
 *
 ***********************/
public class CubeShader extends StaticGeometryShader3D
{
	public CubeShader()
	{
		super("game/cube_shader");
	}

	@Override
	protected void createUniforms()
	{
	}
}
