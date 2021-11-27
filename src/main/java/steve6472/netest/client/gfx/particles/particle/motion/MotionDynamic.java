package steve6472.netest.client.gfx.particles.particle.motion;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.Val;
import steve6472.netest.client.gfx.particles.Val3;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/10/2021
 * Project: VoxWorld
 *
 ***********************/
public class MotionDynamic extends ParticleMotion
{
	Val3 linearAcceleration;
	Val linearDragCoefficient;
	Val rotationAcceleration;
	Val rotationDragCoefficient;

	public MotionDynamic(JSONObject json)
	{
		super(json);

		linearAcceleration = new Val3(json.optJSONArray("linear_acceleration"), 0, 0, 0);
		linearDragCoefficient = new Val(json.opt("linear_drag_coefficient"), 0);
		rotationAcceleration = new Val(json.opt("rotation_acceleration"), 0);
		rotationDragCoefficient = new Val(json.opt("rotation_drag_coefficient"), 0);
	}

	@Override
	public void tick(MoLangRuntime runtime, Particle particle)
	{
		double velX = particle.getSpeed() * particle.getMotion().x;
		double velY = particle.getSpeed() * particle.getMotion().y;
		double velZ = particle.getSpeed() * particle.getMotion().z;

		double ldc = linearDragCoefficient.execute(runtime);

		double accX = -ldc * velX;
		double accY = -ldc * velY;
		double accZ = -ldc * velZ;
		particle.getMotion().add(accX + linearAcceleration.executeX(runtime), accY + linearAcceleration.executeY(runtime), accZ + linearAcceleration.executeZ(runtime));

		particle.getPosition().add(particle.getMotion().x / 3600.0, particle.getMotion().y / 3600.0, particle.getMotion().z / 3600.0);

		double accRot = rotationAcceleration.execute(runtime) -particle.getRotationRate() * rotationDragCoefficient.execute(runtime) + particle.getRotationRate();
		particle.setRotation(particle.getRotation() + accRot / 60.0);
	}
}
