package generic;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Statistics {
	
	// TODO add your statistics here
	static int numberOfInstructions;
	static int numberOfCycles;
	static int ofstall;
	static int wrpath;
	static int Cachesized,Cachesizei;
	public static void printStatistics(String statFile) throws IOException
	{
		FileWriter fw = null; 
		BufferedWriter bw = null; 
		PrintWriter pw = null; 
		try { 
		fw = new FileWriter(statFile, true); 
		bw = new BufferedWriter(fw); 
		pw = new PrintWriter(bw); 
		// TODO add code here to print statistics in the output file
		pw.println("Cache size L1d = "+Cachesized+", Cache size L1i = "+Cachesizei); 
		pw.println("Number of instructions executed = " + numberOfInstructions);
		pw.println("Number of cycles taken = " + numberOfCycles);
		pw.println("Throughput(instructions per cycle) = " + (numberOfInstructions/(float)numberOfCycles));
		pw.println();
		pw.flush(); 
		System.out.println("Data Successfully appended into file"); 
		} finally 
			{ 
			try 
			{
				pw.close(); 
				bw.close(); 
				fw.close(); 
			} 
			catch (IOException e) {// can't do anything } }
				Misc.printErrorAndExit(e.getMessage());
			}
		}
		/*try
		{
			PrintWriter writer = new PrintWriter(statFile,true);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Throughput(instructions per cycle) = " + (numberOfInstructions/(float)numberOfCycles));
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{*/
		
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
	public static void setcsized(int cd) {
		Statistics.Cachesized = cd;
	}
	public static void setcsizei(int ci) {
		Statistics.Cachesizei = ci;
	}
}
