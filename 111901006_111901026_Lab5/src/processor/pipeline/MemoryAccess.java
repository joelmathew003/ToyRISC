package processor.pipeline;

import processor.Processor;
import processor.memorysystem.MainMemory;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_OF_LatchType IF_OF_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch,IF_OF_LatchType iF_OF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
	}
	
	public void performMA()
	{
		//TODO
		MA_RW_Latch.set_isnop(EX_MA_Latch.get_isnop());
		if(EX_MA_Latch.isMA_enable() && !EX_MA_Latch.get_isnop()){
		int res = EX_MA_Latch.get_aluResult();
		int rd = EX_MA_Latch.get_dest();
		MA_RW_Latch.set_aluResult(res);
		if(EX_MA_Latch.get_opcode() == 22)
		{
			int value = containingProcessor.getMainMemory().getWord(res);
			MA_RW_Latch.set_aluResult(value);
				
		}
		else if(EX_MA_Latch.get_opcode() == 23)
		{
				containingProcessor.getMainMemory().setWord(res,rd);
				MA_RW_Latch.set_aluResult(EX_MA_Latch.get_aluResult());
			
		}
		if(EX_MA_Latch.get_opcode()>=23 && EX_MA_Latch.get_opcode()<=28 && res ==-1)
		{
			if(res == -1)
			{
				IF_OF_Latch.set_isnop(false);
			}
		} 
					
		MA_RW_Latch.set_dest(rd);
		MA_RW_Latch.set_opcode(EX_MA_Latch.get_opcode());
		MA_RW_Latch.setRW_enable(true);
		EX_MA_Latch.setMA_enable(false);
		}
		
	}

}
