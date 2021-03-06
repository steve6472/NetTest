package steve6472.netest.server;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public class ServerOptions
{
	public static final double MAXIMUM_MOVE_DISTANCE = 5;

	// In ticks
	public static final int PING_DELAY = 60 * 5;

	// Disconnect player after not responding to this many pings
	public static final int DISCONNECT_THREASHOLD = 2;
}
