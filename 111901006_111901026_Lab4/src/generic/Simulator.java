package generic;
import processor.Clock;
import processor.Processor;
import java.util.*;
import java.io.*;
import generic.Statistics;


public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	static Statistics stats;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p) throws FileNotFoundException
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
		stats = new Statistics();
	}
	
	static void loadProgram(String assemblyProgramFile) throws FileNotFoundException
	{

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
	}
	
	public static void simulate()
	{
		int cycle_count=0;
		int instruction_count=0;
		
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			cycle_count++;
			Clock.incrementClock();

			processor.getOFUnit().performOF();
			cycle_count++;
			Clock.incrementClock();
			
			processor.getEXUnit().performEX();
			cycle_count++;
			Clock.incrementClock();
			
			processor.getMAUnit().performMA();
			cycle_count++;
			Clock.incrementClock();
			
			processor.getRWUnit().performRW();
			cycle_count++;
			Clock.incrementClock();
			
			instruction_count++;
		}
		
		// Statistics stats= 

		stats.setNumberOfInstructions(instruction_count);
		stats.setNumberOfCycles(cycle_count);
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
