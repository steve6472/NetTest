package steve6472.netest.client.gfx.particles.emitter.shapes;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.joml.Vector3f;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.Val;
import steve6472.netest.client.gfx.particles.particle.initial.InitialSpeed;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/6/2021
 * Project: VoxWorld
 *
 ***********************/
public class ShapeSphere extends Shape
{
	private static final Vector3f VEC = new Vector3f();
	Val radius;
	boolean onSurface;

	public ShapeSphere(JSONObject json)
	{
		super(json);
		radius = new Val(json.opt("radius"));
		onSurface = json.optBoolean("surface_only");
	}

	@Override
	public void modifyPosition(MoLangRuntime runtime, Particle particle)
	{
		newSphereCoords(onSurface);
		VEC.mul(radius.executeF(runtime));
		double ox = getOffset().executeX(runtime);
		double oy = getOffset().executeY(runtime);
		double oz = getOffset().executeZ(runtime);
		particle.getPosition().add(VEC.x + ox, VEC.y + oy, VEC.z + oz);

		if (getDirectionType() != DirectionType.VECTOR)
		{
			VEC.normalize();
			VEC.mul(60f);
			VEC.mul(particle.getEmitter().getComponent(InitialSpeed.class).speed.executeF(runtime));
			if (getDirectionType() == DirectionType.INWARDS)
				VEC.mul(-1);

			particle.getMotion().set(VEC);
		} else
		{
			throw new RuntimeException("No Vector direction");
		}
	}

	/**
	 * @author https://karthikkaranth.me/blog/generating-random-points-in-a-sphere/
	 */
	private void newSphereCoords(boolean onSurface)
	{
		var u = Math.random();
		var v = Math.random();
		var theta = u * 2.0 * Math.PI;
		var phi = Math.acos(2.0 * v - 1.0);
		var r = onSurface ? 1 : Math.cbrt(Math.random());
		var sinTheta = Math.sin(theta);
		var cosTheta = Math.cos(theta);
		var sinPhi = Math.sin(phi);
		var cosPhi = Math.cos(phi);
		var x = r * sinPhi * cosTheta;
		var y = r * sinPhi * sinTheta;
		var z = r * cosPhi;
		VEC.set(x, y, z);
	}
}
