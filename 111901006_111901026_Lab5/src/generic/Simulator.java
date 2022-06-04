package generic;

import processor.Clock;
import processor.Processor;
import processor.memorysystem.MainMemory;
import processor.pipeline.RegisterFile;
import java.io.*;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	static int num_inst;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p) throws FileNotFoundException,IOException
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile) throws FileNotFoundException,IOException
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		DataInputStream instr = new DataInputStream(new BufferedInputStream(new FileInputStream(assemblyProgramFile)));
		try
		{
			int main_address = instr.readInt();
			int i, res;
			for(i=0;i<main_address;i++)
			{
				res = instr.readInt();
				processor.getMainMemory().setWord(i,res);
			}
			processor.getRegisterFile().setProgramCounter(main_address);
			
			while(instr.available()>0)
			{
				int val = instr.readInt();
				processor.getMainMemory().setWord(i,val);			
				i++;
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		processor.getRegisterFile().setValue(0, 0);
        	processor.getRegisterFile().setValue(1, 65535);
        	processor.getRegisterFile().setValue(2, 65535);
	}
	
	public static void simulate()
	{	
		int cycle_count=0;
		int ofstall = 0;
		int wrpath = 0;	
		Statistics.setNumberOfInstructions(0);
		while(simulationComplete == false)
		{	
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			int retEX = processor.getEXUnit().performEX();
			if (retEX==-1)	wrpath++;
			int retOF = processor.getOFUnit().performOF();
			if (retOF==-1)	ofstall++;
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			cycle_count++;
			System.out.println("Count = "+cycle_count);
			System.out.println("OFstall = "+ofstall);
			System.out.println("Wrpath = "+wrpath);
			System.out.println();
		}
		
		// TODO
		// set statistics
		Statistics.setNumberOfCycles((int)Clock.getCurrentTime());
		Statistics.setNumberOfCycles(cycle_count);
		Statistics.setOFstall(ofstall);
		Statistics.setWrpath(wrpath);
	}
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
