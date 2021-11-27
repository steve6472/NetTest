package steve6472.netest.client.gfx.particles;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.MoLang;
import com.bedrockk.molang.runtime.MoLangRuntime;
import org.json.JSONArray;

import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/13/2021
 * Project: VoxWorld
 *
 ***********************/
public class Val3
{
	private final List<Expression> x, y, z;

	public Val3(JSONArray array, float defaultX, float defaultY, float defaultZ)
	{
		if (array != null && ((array.opt(2) instanceof String) || (array.opt(2) instanceof Number)))
		{
			x = MoLang.parse(array.get(0).toString());
			y = MoLang.parse(array.get(1).toString());
			z = MoLang.parse(array.get(2).toString());
		} else
		{
			x = MoLang.parse("" + defaultX);
			y = MoLang.parse("" + defaultY);
			z = MoLang.parse("" + defaultZ);
		}
	}

	public Val3(JSONArray array)
	{
		if (array != null && ((array.opt(2) instanceof String) || (array.opt(2) instanceof Number)))
		{
			x = MoLang.parse(array.get(0).toString());
			y = MoLang.parse(array.get(1).toString());
			z = MoLang.parse(array.get(2).toString());
		} else
		{
			x = y = z = MoLang.parse("0");
		}
	}

	public double executeX(MoLangRuntime runtime)
	{
		try
		{
			return runtime.execute(x).asDouble();
		} catch (Exception ex)
		{
			System.err.println(x);
			throw ex;
		}
	}

	public double executeY(MoLangRuntime runtime)
	{
		try
		{
			return runtime.execute(y).asDouble();
		} catch (Exception ex)
		{
			System.err.println(y);
			throw ex;
		}
	}

	public double executeZ(MoLangRuntime runtime)
	{
		try
		{
			return runtime.execute(z).asDouble();
		} catch (Exception ex)
		{
			System.err.println(z);
			throw ex;
		}
	}

	public float executeFX(MoLangRuntime runtime)
	{
		try
		{
			return (float) runtime.execute(x).asDouble();
		} catch (Exception ex)
		{
			System.err.println(x);
			throw ex;
		}
	}

	public float executeFY(MoLangRuntime runtime)
	{
		try
		{
			return (float) runtime.execute(y).asDouble();
		} catch (Exception ex)
		{
			System.err.println(y);
			throw ex;
		}
	}

	public float executeFZ(MoLangRuntime runtime)
	{
		try
		{
			return (float) runtime.execute(z).asDouble();
		} catch (Exception ex)
		{
			System.err.println(z);
			throw ex;
		}
	}

	@Override
	public String toString()
	{
		return "Val3{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
