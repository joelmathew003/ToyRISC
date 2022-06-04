package processor.pipeline;

import processor.Clock;
import processor.Processor;
import generic.Simulator;
import generic.Element;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Event;

public class InstructionFetch implements Element{
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType	MA_RW_Latch;
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch, EX_MA_LatchType eX_MA_Latch,OF_EX_LatchType oF_EX_Latch,MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performIF()
	{	
		
		/*if(EX_MA_Latch.isMA_busy() || OF_EX_Latch.isEX_busy())
		{
			return;
		}*/
		if(IF_EnableLatch.isIF_busy())
		{
			return ;
		}
		if(IF_EnableLatch.isIF_enable() && !IF_EnableLatch.get_isnop())
		{	
			if(IF_EnableLatch.isIF_busy())
			{
				return ;
			}
			Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime(),
										this ,
										containingProcessor.getMainMemory(),
										containingProcessor.getRegisterFile().getProgramCounter()));
			IF_OF_Latch.setPC(containingProcessor.getRegisterFile().getProgramCounter());
			System.out.println("IF event added");
			
			IF_EnableLatch.setIF_busy(true);
		}
	}
	@Override
	public void handleEvent(Event e)
	{
		if(IF_OF_Latch.isOF_busy() || IF_EnableLatch.get_isnop() )
		{
			System.out.println("if stalled");
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			System.out.println("Handling IF..");
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			IF_OF_Latch.setInstruction(event.getValue());
			containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter() + 1);
				
			System.out.println("PC set as :"+containingProcessor.getRegisterFile().getProgramCounter());
			IF_EnableLatch.setIF_enable(false);
			IF_OF_Latch.setOF_enable(true);
			IF_EnableLatch.setIF_busy(false);
			if(IF_EnableLatch.is_Wrongpath())
			{
				System.out.println("wrong pass nop");
				IF_EnableLatch.set_Wrongpath(false);
				OF_EX_Latch.set_isnop(true);
				EX_MA_Latch.set_isnop(true);
				MA_RW_Latch.set_isnop(true);
				return ;	
			}
		}
	}
}
