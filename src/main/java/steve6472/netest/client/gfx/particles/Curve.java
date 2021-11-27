package steve6472.netest.client.gfx.particles;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONArray;
import org.json.JSONObject;
import steve6472.sge.main.util.MathUtil;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/4/2021
 * Project: VoxWorld
 *
 ***********************/
public class Curve
{
	public final String name;
	public final CurveType type;
	public Val[] controlNodes;
	public Val input;
	public Val horizontalRange;

	public Curve(String name, JSONObject json)
	{
		this.name = name;
		this.type = CurveType.fromString(json.getString("type"));

		JSONArray nodes = json.getJSONArray("nodes");
		controlNodes = new Val[nodes.length()];
		for (int i = 0; i < nodes.length(); i++)
		{
			controlNodes[i] = new Val(nodes.get(i));
		}

		input = new Val(json.get("input"));
		horizontalRange = new Val(json.get("horizontal_range"));
	}

	public String getName()
	{
		return name;
	}

	public CurveType getType()
	{
		return type;
	}

	public double getValue(MoLangRuntime runtime)
	{
		double input = this.input.execute(runtime);
		double range = this.horizontalRange.execute(runtime);
		double indexSelector = MathUtil.clamp(input / range, 0, 1);

		int index = MathUtil.clamp((int) (indexSelector * (controlNodes.length - 1)), 0, controlNodes.length - 1);
		int next = MathUtil.clamp(index + 1, 0, controlNodes.length - 1);

		double start = index / ((double) controlNodes.length - 1);
		double end = next / ((double) controlNodes.length - 1);

		double t = MathUtil.time(start, end, indexSelector);

//		System.out.printf("%.3f, %d, %d\n", t, index, next);
//		System.out.println(String.format("%.3f, %.3f       %d, %d     %.2f, %.2f", t, indexSelector, index, next, index / (range + 1), next / (range + 1)));

		double v = switch (type)
			{
				case LINEAR -> (float) MathUtil.lerp(controlNodes[index].execute(runtime), controlNodes[next].execute(runtime), t);
				case BEZIER -> 1f;
				case CATMULL_ROM -> {

					int past = MathUtil.clamp((int) (indexSelector * (controlNodes.length - 3)), 0, controlNodes.length - 1);
					index = MathUtil.clamp(past + 1, 0, controlNodes.length - 2);
					next = MathUtil.clamp(index + 1, 0, controlNodes.length - 2);
					int future = MathUtil.clamp(next + 1, 0, controlNodes.length - 1);

					start = past / ((double) controlNodes.length - 1);
					end = future / ((double) controlNodes.length - 1);

					t = MathUtil.time(start, end, indexSelector);

					double v1 = (float) MathUtil.catmullLerp(controlNodes[past].execute(runtime), controlNodes[index].execute(runtime), controlNodes[next]
						.execute(runtime), controlNodes[future].execute(runtime), t);
//					String x = past + " " + index + " " + next + " " + future + " " + String.format("%.2f", indexSelector) + " " + String
//						.format("%.2f", t) + " " + String.format("%.2f", start) + " " + String.format("%.2f", end);
//					Main.log(x);
//					System.out.println(x);
					yield v1;
				}
			};
//		Main.log(String.format("%.3f, %.3f       %d, %d     %.2f, %.2f    %.3f    %.3f", t, indexSelector, index, next, start, end, range, v));
		return v;
	}

	public enum CurveType
	{
		LINEAR, BEZIER, CATMULL_ROM;

		public static CurveType fromString(String s)
		{
			return switch (s)
				{
					case "linear" -> CurveType.LINEAR;
//					case "bezier" -> CurveType.BEZIER;
					case "catmull_rom" -> CurveType.CATMULL_ROM;
					case "bezier_chain", "bezier" -> throw new IllegalStateException(s + " is not supported yet cause IDK what it is/am lazy");
					default -> throw new IllegalStateException("Unexpected value: " + s);
				};
		}
	}
}
