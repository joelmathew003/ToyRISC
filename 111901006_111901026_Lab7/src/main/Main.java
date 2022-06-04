package main;
import configuration.Configuration;
import generic.Misc;
import generic.Statistics;
import processor.Processor;
import generic.Simulator;
import java.io.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException,IOException{
		if(args.length != 3)
		{
			Misc.printErrorAndExit("usage: java -jar <path-to-jar-file> <path-to-config-file> <path-to-stat-file> <path-to-object-file>\n");
		}
		
		Configuration.parseConfiguratioFile(args[0]);
		int latency[] = {1,2,4,8,12};
		int Cachesize[] = {4,8,32,128,1024}; 
		for(int i=0;i<5;i++)
		{
			Processor processor = new Processor(Cachesize[i],latency[i],1024,12);
			Statistics.setcsizei(Cachesize[i]);
			Statistics.setcsized(1024);
			Simulator.setupSimulation(args[2], processor);
			Simulator.simulate();
		
			processor.printState(65515, 65535); // ((0, 0) refers to the range of main memory addresses we wish to print. this is an empty set.
		
			Statistics.printStatistics(args[1]);
		}
		for(int i=0;i<5;i++)
		{
			Processor processor = new Processor(1024,12,Cachesize[i],latency[i]);
			Statistics.setcsized(Cachesize[i]);
			Statistics.setcsizei(1024);			

			Simulator.setupSimulation(args[2], processor);
			Simulator.simulate();
		
			processor.printState(65515, 65535); // ((0, 0) refers to the range of main memory addresses we wish to print. this is an empty set.
		
			Statistics.printStatistics(args[1]);
		}
	}

}
