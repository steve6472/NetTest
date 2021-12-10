package steve6472.netest;

import java.util.Calendar;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 12/10/2021
 * Project: NetTest
 *
 ***********************/
public class SecondCounter
{
	private byte second;
	private int internalCounter;
	private int secondCounter;

	public void tick()
	{
		byte s = (byte) Calendar.getInstance().get(Calendar.SECOND);
		if (s != second)
		{
			second = s;
			secondCounter = internalCounter;
			internalCounter = 0;
		}
		internalCounter++;
	}

	public int getInternalCounter()
	{
		return internalCounter;
	}

	public int getSecondCounter()
	{
		return secondCounter;
	}
}
