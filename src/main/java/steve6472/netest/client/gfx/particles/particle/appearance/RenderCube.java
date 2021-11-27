package steve6472.netest.client.gfx.particles.particle.appearance;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONObject;
import steve6472.netest.client.gfx.CubeTess;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.Val;
import steve6472.sge.gfx.game.stack.Stack;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/10/2021
 * Project: VoxWorld
 *
 ***********************/
public class RenderCube extends ParticleRender
{
	Val size;

	public RenderCube(JSONObject json)
	{
		super(json);

		size = new Val(json.get("size"), 1 / 16f);
	}

	@Override
	public void render(MoLangRuntime runtime, Particle particle, Stack stack)
	{
		CubeTess tess = stack.getTess(CubeTess.class, "cube");
		tess.color(1, 1, 1, 1);

		Tinting tint = particle.getEmitter().getComponent(Tinting.class);
		if (tint != null)
			tess.color(tint.getRed(runtime), tint.getGreen(runtime), tint.getBlue(runtime), tint.getAlpha(runtime));

		System.out.println(size);

		tess.size(size.executeF(runtime));

		tess.pos(((float) particle.getPosition().x), ((float) particle.getPosition().y), ((float) particle.getPosition().z)).endVertex();
	}
}
