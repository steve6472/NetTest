package steve6472.netest.client.gfx.particles.particle.appearance;

import com.bedrockk.molang.runtime.MoLangRuntime;
import org.joml.Vector4f;
import org.json.JSONArray;
import org.json.JSONObject;
import steve6472.netest.client.gfx.particles.Particle;
import steve6472.netest.client.gfx.particles.Val;
import steve6472.netest.client.gfx.particles.Val3;
import steve6472.netest.client.gfx.particles.base.ParticleComponent;
import steve6472.sge.main.util.ColorUtil;
import steve6472.sge.main.util.MathUtil;
import steve6472.sge.main.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/4/2021
 * Project: VoxWorld
 *
 ***********************/
public class Tinting extends ParticleComponent
{
	AColor color;

	public Tinting(JSONObject json)
	{
		super(json);
		Object o = json.get("color");

		if (o instanceof JSONArray a)
		{
			color = new ArrayColor(a);
		}
		else if (o instanceof JSONObject jsonO)
		{
			color = new InterpolantColor(jsonO);
		}
		else
		{
			throw new IllegalArgumentException("Color can only accept JSONArray & JSONObject for now");
		}
	}

	@Override
	public void tick(MoLangRuntime runtime, Particle particle)
	{

	}

	public float getRed(MoLangRuntime runtime)
	{
		return color.getRed(runtime);
	}

	public float getGreen(MoLangRuntime runtime)
	{
		return color.getGreen(runtime);
	}

	public float getBlue(MoLangRuntime runtime)
	{
		return color.getBlue(runtime);
	}

	public float getAlpha(MoLangRuntime runtime)
	{
		return color.getAlpha(runtime);
	}

	abstract static class AColor
	{
		public abstract float getRed(MoLangRuntime runtime);
		public abstract float getGreen(MoLangRuntime runtime);
		public abstract float getBlue(MoLangRuntime runtime);
		public abstract float getAlpha(MoLangRuntime runtime);
	}

	static class ArrayColor extends AColor
	{
		Val3 color;
		Val alpha;

		ArrayColor(JSONArray array)
		{
			if (array.length() == 4)
				alpha = new Val(array.get(3), 1);

			color = new Val3(array, 1, 1, 1);
		}

		public float getRed(MoLangRuntime runtime)
		{
			return color.executeFX(runtime);
		}

		public float getGreen(MoLangRuntime runtime)
		{
			return color.executeFY(runtime);
		}

		public float getBlue(MoLangRuntime runtime)
		{
			return color.executeFZ(runtime);
		}

		public float getAlpha(MoLangRuntime runtime)
		{
			return alpha.executeF(runtime);
		}
	}

	static class InterpolantColor extends AColor
	{
		private static final Vector4f VEC = new Vector4f();

		Val interpolant;
		List<Pair<Float, Vector4f>> gradient = new ArrayList<>();
		float range;

		public InterpolantColor(JSONObject json)
		{
			interpolant = new Val(json.get("interpolant"));
			Object gradient = json.opt("gradient");
			if (gradient instanceof JSONObject o)
			{
				for (String s : o.keySet())
				{
					String string = o.getString(s);
					if (string.startsWith("#"))
						string = string.substring(1);
					int i = (int) Long.parseLong(string, 16);
					this.gradient.add(new Pair<>(Float.parseFloat(s), new Vector4f(ColorUtil.getRed(i) / 255f, ColorUtil.getGreen(i) / 255f, ColorUtil.getBlue(i) / 255f, ColorUtil.getAlpha(i) / 255f)));
				}
			}

			// Fuck whoever wants to read this
			// Comment only for me: return 0 if equals (should never happen cause JSON), otherwise move
			this.gradient.sort((a, b) -> a.a().equals(b.a()) ? 0 : a.a() < b.a() ? -1 : 1);
			this.range = this.gradient.get(this.gradient.size() - 1).a();
		}

		@Override
		public float getRed(MoLangRuntime runtime)
		{
			return get(runtime).x;
		}

		@Override
		public float getGreen(MoLangRuntime runtime)
		{
			return get(runtime).y;
		}

		@Override
		public float getBlue(MoLangRuntime runtime)
		{
			return get(runtime).z;
		}

		@Override
		public float getAlpha(MoLangRuntime runtime)
		{
			return get(runtime).w;
		}

		private Vector4f get(MoLangRuntime runtime)
		{
			double time = interpolant.execute(runtime) / range;
			int i = binarySearch(gradient, time);
			Pair<Pair<Float, Vector4f>, Pair<Float, Vector4f>> keys = getKeys(gradient, i);
			double t = MathUtil.time(keys.a().a(), keys.b().a(), time);

			VEC.set(
				MathUtil.lerp(keys.a().b().x, keys.b().b().x, t),
				MathUtil.lerp(keys.a().b().y, keys.b().b().y, t),
				MathUtil.lerp(keys.a().b().z, keys.b().b().z, t),
				MathUtil.lerp(keys.a().b().w, keys.b().b().w, t)
			);

			return VEC;
		}

		private Pair<Pair<Float, Vector4f>, Pair<Float, Vector4f>> getKeys(List<Pair<Float, Vector4f>> keys, int index)
		{
			if (index == -1)
				throw new IllegalStateException("Index is negative");

			if (index == keys.size())
				index -= 1;

			if (index + 1 >= keys.size())
				return new Pair<>(keys.get(index), keys.get(index));

			return new Pair<>(keys.get(index), keys.get(index + 1));
		}

		private int binarySearch(List<Pair<Float, Vector4f>> keys, double target)
		{
			if (keys.size() == 0)
				return -1;
			else if (keys.size() == 1)
				return 1;

			if (target <= keys.get(0).a())
				return 0;
			else if (target > keys.get(keys.size() - 1).a())
				return keys.size() - 1;

			int min = 0;
			int max = keys.size() - 1;

			while (min <= max)
			{
				int mid = (min + max) / 2;

				if (target < keys.get(mid).a())
				{
					max = mid - 1;
				} else if (target > keys.get(mid).a())
				{
					min = mid + 1;
				} else
				{
					return mid;
				}
			}

			// Returns closest double, that I do not want
			//		return (keys.get(min).time() - target) < (target - keys.get(max).time()) ? min : max;
			// Returns the lower closest value
			return max;
		}
	}
}
