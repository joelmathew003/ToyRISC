package processor.pipeline;

import processor.Clock;
import processor.Processor;
import generic.Simulator;
import generic.Element;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Event;
import generic.ExecutionCompleteEvent;
import generic.Statistics;

public class Execute implements Element{
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_OF_LatchType IF_OF_Latch;
	IF_EnableLatchType IF_EnableLatch;
	Event e;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch,IF_OF_LatchType iF_OF_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public int performEX()
	{
		//TODO
		/*if(IF_EnableLatch.isIF_busy()|| EX_MA_Latch.isMA_busy())
		{
			return 1;
		}*/
		if(OF_EX_Latch.isEX_busy())
		{
			return 1;
		}
		EX_MA_Latch.set_isnop(OF_EX_Latch.get_isnop());
		if(OF_EX_Latch.isEX_enable() && !OF_EX_Latch.get_isnop())
		{
			if(OF_EX_Latch.isEX_busy())
			{
				return 1;
			}
			int opcode = OF_EX_Latch.get_opcode();
			System.out.println("EX event added of "+opcode);
			switch(opcode)
			{
				case 4:
				case 5:
					if(OF_EX_Latch.isEX_busy())
					{
						return 1;
					}
					Simulator.getEventQueue().addEvent(new ExecutionCompleteEvent(Clock.getCurrentTime() + 4,
										this,
										this));
					OF_EX_Latch.setEX_busy(true);
					break;
				case 6:
				case 7:
					if(OF_EX_Latch.isEX_busy())
					{
						return 1;
					}
					Simulator.getEventQueue().addEvent(new ExecutionCompleteEvent(Clock.getCurrentTime() + 10,
										this,
										this));
					OF_EX_Latch.setEX_busy(true);
					break;
				default:
					if(OF_EX_Latch.isEX_busy())
					{
						return 1;
					}
					Simulator.getEventQueue().addEvent(new ExecutionCompleteEvent(Clock.getCurrentTime() + 1,
										this,
										this));
					OF_EX_Latch.setEX_busy(true);
					break;
			}
		}
		return 1;
	}
	public void ex()
	{
		int ret = 1;
		if(OF_EX_Latch.isEX_enable() && !OF_EX_Latch.get_isnop())
		{
			int pc = OF_EX_Latch.get_pc();
			int op1 = OF_EX_Latch.get_op1();
			int op2 = OF_EX_Latch.get_op2();
			int opcode = OF_EX_Latch.get_opcode();
			int imm = OF_EX_Latch.get_imm();
			int rd = OF_EX_Latch.get_dest();
			int res = -100;
			boolean isBranchTaken = false;
			int rs1,dest;
			System.out.println("opcode = "+opcode);
			switch(opcode){
				case 0://add
					res = op1+op2;
					break;
				case 1://addi
					res = op1+imm;
					break;
				case 2://sub
					res = op1-op2;
					break;
				case 3://subi
					res = op1-imm;
					break;
				case 4://mul
					res = op1*op2;
					break;
				case 5://muli
					res = op1*imm;
					break;
				case 6://div
					res = op1/op2;
					break;
				case 7://divi
					res = op1/imm;
					break;
				case 8://and
					res = op1&op2;
					break;
				case 9://andi
					res = op1&imm;
					break;
				case 10://or
					res = op1|op2;
					break;
				case 11://ori
					res = op1|imm;
					break;
				case 12://xor
					res = op1^op2;
					break;
				case 13://xori
					res = op1^imm;
					break;
				case 14://slt
					if(op1<op2) res=1;
					else res=0;
					break;
				case 15://slti
					if(op1<imm) res=1;
					else res=0;
					break;
				case 16://sll
					res = op1<<op2;
					break;
				case 17://slli
					res = op1<<imm;
					break;
				case 18://srl
					res = op1>>>op2;
					break;
				case 19://srli
					res = op1>>>imm;
					break;
				case 20://sra
					res = op1>>op2;
					break;
				case 21://srai
					res = op1>>imm;
					break;
				case 22://load
					res = op1+imm;
					break;
				case 23://store
					res = rd+imm;
					rd = op1;
					break;
				case 24:
					isBranchTaken = true;
					pc = pc+rd+imm;
					containingProcessor.getRegisterFile().setProgramCounter(pc);
					break;
				case 25:
					if(op1==rd){
						System.out.println("1, pc ="+pc+imm);
						isBranchTaken = true;
						pc = pc+imm;
						containingProcessor.getRegisterFile().setProgramCounter(pc);
					}
					break;
				case 26:
					if(op1!=rd){
						isBranchTaken = true;
						pc = pc+imm;
						containingProcessor.getRegisterFile().setProgramCounter(pc);
					}
					break;
				case 27:
					if(op1<rd){
						isBranchTaken = true;
						pc = pc+imm;
						containingProcessor.getRegisterFile().setProgramCounter(pc);
					}
					break;
				case 28:
					if(op1>rd){
						isBranchTaken = true;
						pc = pc+imm;
						containingProcessor.getRegisterFile().setProgramCounter(pc);
					}
					break;
				case 29:
					//end
					break;			
			}
			if(isBranchTaken)
			{
				ret = -1;
				res =-1;	
				OF_EX_Latch.set_isnop(true);
				IF_OF_Latch.set_isnop(true);
				//Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions()+1);
				IF_EnableLatch.set_Wrongpath(true);
			}
			EX_MA_Latch.set_aluResult(res);
			EX_MA_Latch.set_opcode(opcode);
			EX_MA_Latch.set_pc(OF_EX_Latch.get_pc());
			EX_MA_Latch.set_dest(rd);
			EX_MA_Latch.set_opcode(opcode);	
			if(opcode>=24 && opcode<=28)
			{	
				//IF_EnableLatch.setIF_enable(true);
				EX_IF_Latch.setIF_enable(true);
				EX_MA_Latch.setMA_enable(true);
			}
			else
			{
				EX_MA_Latch.setMA_enable(true);
			}
			OF_EX_Latch.setEX_enable(false);
		}
			
	}
	@Override
	public void handleEvent(Event e)
	{
		if(EX_MA_Latch.isMA_busy() || IF_EnableLatch.isIF_busy())
		{
			System.out.println("ex stalled=");
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			System.out.println("Handling EX..");
			ExecutionCompleteEvent event = (ExecutionCompleteEvent) e;
			ex();
			OF_EX_Latch.setEX_busy(false);
		}
	}
}
