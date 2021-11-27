package steve6472.netest.client.gfx.particles;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.MoLang;
import com.bedrockk.molang.runtime.MoLangRuntime;

import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/13/2021
 * Project: VoxWorld
 *
 ***********************/
public class Val
{
	private static final List<Expression> ZERO = MoLang.parse("0");

	private final List<Expression> expressions;
	String debug;

	public Val(Object code)
	{
		if (code == null)
			expressions = ZERO;
		else
		{
			String code1 = code.toString();
			debug = code1;
			expressions = MoLang.parse(code1);
		}
	}

	public Val(Object code, double defaultValue)
	{
		if (code == null)
		{
			debug = "DEFAULT: " + defaultValue;
			expressions = MoLang.parse("" + defaultValue);
		} else
		{
			String code1 = code.toString();
			debug = code1;
			expressions = MoLang.parse(code1);
		}
	}

	public double execute(MoLangRuntime runtime)
	{
		try
		{
			return runtime.execute(expressions).asDouble();
		} catch (Exception ex)
		{
			System.err.println(debug);
			throw ex;
		}
	}

	public float executeF(MoLangRuntime runtime)
	{
		try
		{
			return (float) runtime.execute(expressions).asDouble();
		} catch (Exception ex)
		{
			System.err.println(debug);
			throw ex;
		}
	}

	@Override
	public String toString()
	{
		return "Val{" + "expressions=" + expressions + ", debug='" + debug + '\'' + '}';
	}
}
