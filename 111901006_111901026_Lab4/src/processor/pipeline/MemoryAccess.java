package processor.pipeline;
import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		//TODO
		//System.out.println("mem");
		if(EX_MA_Latch.isMA_enable())
		{
			if(EX_MA_Latch.get_opcode() == 22)
			{
				int value = containingProcessor.getMainMemory().getWord(EX_MA_Latch.get_aluResult());
				MA_RW_Latch.set_aluresult(value);
				
			}
			else if(EX_MA_Latch.get_opcode() == 23)
			{
				int res = EX_MA_Latch.get_aluResult();
				int rd = EX_MA_Latch.get_dest();
				containingProcessor.getMainMemory().setWord(res,rd);
				MA_RW_Latch.set_aluresult(EX_MA_Latch.get_aluResult());
			}
			else
			{
				MA_RW_Latch.set_aluresult(EX_MA_Latch.get_aluResult());
			}
			MA_RW_Latch.set_dest(EX_MA_Latch.get_dest());
			MA_RW_Latch.set_opcode(EX_MA_Latch.get_opcode());
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_enable(false);
		}
	}

}
