package steve6472.netest.client.gfx.particles.emitter.shapes;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.joml.Vector3f;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.particle.initial.InitialSpeed;
import steve6472.sge.main.util.RandomUtil;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/6/2021
 * Project: VoxWorld
 *
 ***********************/
public class ShapePoint extends Shape
{
	private static final Vector3f VEC = new Vector3f();

	public ShapePoint(JSONObject json)
	{
		super(json);
	}

	@Override
	public void modifyPosition(MoLangRuntime runtime, Particle particle)
	{
		particle.getPosition().add(getOffset().executeX(runtime), getOffset().executeY(runtime), getOffset().executeZ(runtime));

		if (getDirectionType() != DirectionType.VECTOR)
		{
			VEC.set(RandomUtil.randomFloat(-1, 1), RandomUtil.randomFloat(-1, 1), RandomUtil.randomFloat(-1, 1));
			VEC.normalize();
			VEC.mul(60f);
			VEC.mul(particle.getEmitter().getComponent(InitialSpeed.class).speed.executeF(runtime));

			particle.getMotion().set(VEC);
		} else
		{
			throw new RuntimeException("No Vector direction");
		}
	}
}
