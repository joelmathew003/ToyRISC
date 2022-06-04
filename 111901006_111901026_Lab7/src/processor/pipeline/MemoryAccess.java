package processor.pipeline;

import processor.Clock;
import processor.Processor;
import processor.memorysystem.MainMemory;
import generic.Simulator;
import generic.Element;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Event;

public class MemoryAccess implements Element
{
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_OF_LatchType IF_OF_Latch;
	IF_EnableLatchType IF_EnableLatch;
	OF_EX_LatchType OF_EX_Latch;

	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_OF_LatchType iF_OF_Latch, IF_EnableLatchType iF_EnableLatch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performMA()
	{
		//TODO
		if(EX_MA_Latch.isMA_busy())
		{
			return ;
		}
		/*if(IF_EnableLatch.isIF_busy() || OF_EX_Latch.isEX_busy())
		{
			return ;
		}*/
		MA_RW_Latch.set_isnop(EX_MA_Latch.get_isnop());
		if(EX_MA_Latch.isMA_enable() && !EX_MA_Latch.get_isnop()){
			
			System.out.println("MA of"+EX_MA_Latch.get_opcode());
			int res = EX_MA_Latch.get_aluResult();
			int rd = EX_MA_Latch.get_dest();
			MA_RW_Latch.set_aluResult(res);
			if(EX_MA_Latch.get_opcode() == 22)
			{
				if(EX_MA_Latch.isMA_busy())
				{
					return ;
				}
				Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime(),
											this,
											containingProcessor.getCacheL1d(),
											res));				
				EX_MA_Latch.setMA_busy(true);
				
			}
			else if(EX_MA_Latch.get_opcode() == 23)
			{
				if(EX_MA_Latch.isMA_busy())
				{
					return ;
				}
				System.out.println("storing"+rd+"in"+res);
				Simulator.getEventQueue().addEvent(new MemoryWriteEvent(Clock.getCurrentTime(),
											this,
											containingProcessor.getCacheL1d(),
											res,
											rd));				
				EX_MA_Latch.setMA_busy(true);
				
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
	@Override
	public void handleEvent(Event e)
	{
		if(IF_EnableLatch.isIF_busy() || OF_EX_Latch.isEX_busy())
		{
			System.out.println("ma stalled");
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			System.out.println("Handling MA..");
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			MA_RW_Latch.set_aluResult(event.getValue());
			EX_MA_Latch.setMA_enable(false);
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_busy(false);
		}
	}
}
