package processor;

public class Clock {
	static long currentTime = 0;
	
	public static void incrementClock()
	{
		currentTime++;
	}
	public static void setCurrentTime(long time)
	{
		currentTime = time;
	}
	public static long getCurrentTime()
	{
		return currentTime;
	}
}
