package steve6472.netest.client.gfx;

import steve6472.sge.gfx.game.stack.buffer.AbstractBuffer;
import steve6472.sge.gfx.game.stack.buffer.Buffer1f;
import steve6472.sge.gfx.game.stack.buffer.Buffer3f;
import steve6472.sge.gfx.game.stack.buffer.Buffer4f;
import steve6472.sge.gfx.game.stack.mix.IColor4;
import steve6472.sge.gfx.game.stack.mix.IPos3;
import steve6472.sge.gfx.game.stack.tess.AbstractTess;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 9/28/2021
 * Project: VoxWorld
 *
 ***********************/
public class CubeTess extends AbstractTess implements IPos3<CubeTess>, IColor4<CubeTess>
{
	private Buffer3f positionBuffer;
	private Buffer4f colorBuffer;
	private Buffer1f sizeBuffer;

	public CubeTess(int maxSize)
	{
		super(maxSize);
	}

	@Override
	protected void createBuffers(int maxSize)
	{
		positionBuffer = new Buffer3f(maxSize, 0);
		colorBuffer = new Buffer4f(maxSize, 1);
		sizeBuffer = new Buffer1f(maxSize, 2);
	}

	@Override
	protected AbstractBuffer[] buffers()
	{
		return new AbstractBuffer[] {positionBuffer, colorBuffer, sizeBuffer};
	}

	@Override
	public Buffer3f getPositionBuffer()
	{
		return positionBuffer;
	}

	@Override
	public CubeTess getTess()
	{
		return this;
	}

	@Override
	public Buffer4f getColorBuffer()
	{
		return colorBuffer;
	}

	public CubeTess size(float f)
	{
		sizeBuffer.set(f);

		return this;
	}
}
