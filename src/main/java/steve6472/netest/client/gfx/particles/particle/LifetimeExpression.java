package steve6472.netest.client.gfx.particles.particle;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.Val;
import steve6472.netest.client.gfx.particles.base.ParticleComponent;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/5/2021
 * Project: VoxWorld
 *
 ***********************/
public class LifetimeExpression extends ParticleComponent
{
	Val value;
	boolean isExpression;

	public LifetimeExpression(JSONObject json)
	{
		super(json);

		isExpression = json.has("expiration_expression");

		if (isExpression)
			value = new Val(json.get("expiration_expression"));
		else
		{
			value = new Val(json.get("max_lifetime"));
		}
	}

	@Override
	public void tick(MoLangRuntime runtime, Particle particle)
	{

	}

	public boolean shouldBeRemoved(MoLangRuntime runtime, Particle particle)
	{
		if (isExpression)
		{
			return value.execute(runtime) != 0;
		} else
		{
			return particle.getLife() >= particle.getMaxLifetime();
		}
	}

	public double getMaxLifetime(MoLangRuntime runtime, Particle particle)
	{
		return value.execute(runtime);
	}
}
