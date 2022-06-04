package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			//TODO
			int res = MA_RW_Latch.get_aluresult();
			int opcode = MA_RW_Latch.get_opcode();
			int rd = MA_RW_Latch.get_dest();
			if(opcode == 29)
			{
				IF_EnableLatch.setIF_enable(false);
				MA_RW_Latch.setRW_enable(false);
				Simulator.setSimulationComplete(true);
			}
			else if(opcode < 23)
			{
				//System.out.println("writing "+res+" in "+rd);
				containingProcessor.getRegisterFile().setValue(rd,res);
			} 
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
