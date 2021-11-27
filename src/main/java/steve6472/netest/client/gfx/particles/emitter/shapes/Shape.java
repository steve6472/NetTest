package steve6472.netest.client.gfx.particles.emitter.shapes;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONArray;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Emitter;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.Val3;
import steve6472.netest.client.gfx.particles.base.EmitterComponent;

import static steve6472.netest.client.gfx.particles.emitter.shapes.Shape.DirectionType.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/6/2021
 * Project: VoxWorld
 *
 ***********************/
public abstract class Shape extends EmitterComponent
{
	Val3 offset;
	Val3 direction;
	DirectionType directionType;

	public Shape(JSONObject json)
	{
		super(json);
		offset = new Val3(json.optJSONArray("offset"), 0, 0, 0);

		Object dir = json.opt("direction");
		if (dir == null)
		{
			directionType = OUTWARDS;
		} else
		{
			if (dir instanceof String s)
			{
				if (s.equals("inwards"))
					directionType = INWARDS;
				else if (s.equals("outwards"))
					directionType = OUTWARDS;
				else
					throw new IllegalArgumentException("Direction '" + s + "' is invalid");
			} else
			{
				direction = new Val3(((JSONArray) dir), 0, 0, 0);
				directionType = VECTOR;
			}
		}
	}

	/**
	 * @deprecated Should not be used
	 * @param emitter emitter
	 */
	@Override
	@Deprecated
	public void tick(MoLangRuntime runtime, Emitter emitter)
	{

	}

	public abstract void modifyPosition(MoLangRuntime runtime, Particle particle);

	public Val3 getOffset()
	{
		return offset;
	}

	public Val3 getDirection()
	{
		return direction;
	}

	public DirectionType getDirectionType()
	{
		return directionType;
	}

	public enum DirectionType
	{
		INWARDS, OUTWARDS, VECTOR
	}
}
