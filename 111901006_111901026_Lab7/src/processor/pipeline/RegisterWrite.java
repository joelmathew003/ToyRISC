package processor.pipeline;
import processor.pipeline.RegisterFile;
import org.graalvm.compiler.lir.Opcode;

import generic.Simulator;
import generic.Statistics;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_MA_LatchType EX_MA_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch,IF_OF_LatchType iF_OF_Latch, EX_MA_LatchType eX_MA_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performRW()
	{	
		if(IF_EnableLatch.isIF_busy() || EX_MA_Latch.isMA_busy() || OF_EX_Latch.isEX_busy())
		{
			return ;
		}
		if(MA_RW_Latch.isRW_enable() && !MA_RW_Latch.get_isnop())
		{	
			//TODO
			System.out.println("RW of"+MA_RW_Latch.get_opcode());
			Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions()+1);
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			int res = MA_RW_Latch.get_aluResult();
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
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
		else if(MA_RW_Latch.get_isnop()){
			IF_EnableLatch.set_isnop(false);
			IF_EnableLatch.setIF_enable(true);
			IF_OF_Latch.set_isnop(false);
		}
	}

}
