package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int numberOfInstructions;
	static int numberOfCycles;
	static int ofstall;
	static int wrpath;
	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Number of times OF got stalled = "+ ofstall);
			writer.println("Number of times wrong path was taken = "+ wrpath);
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics
	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public static void setNumberOfCycles(int num) {
		Statistics.numberOfCycles = num;
	}
	public static void setOFstall(int dataHazard) {
		Statistics.ofstall = dataHazard;
	}
	public static int getOFstall() {
		return Statistics.ofstall;
	}
	public static int getWrpath() {
		return Statistics.wrpath;
	}
	public static int getNumberOfInstructions() {
		return Statistics.numberOfInstructions;
	}
	public static void setWrpath(int wrongPath) {
		Statistics.wrpath = wrongPath;
	}
}
