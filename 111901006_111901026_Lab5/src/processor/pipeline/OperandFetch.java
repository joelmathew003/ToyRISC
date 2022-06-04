package processor.pipeline;

import processor.Processor;
import generic.Simulator;
import generic.Statistics;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	IF_EnableLatchType IF_EnableLatch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch,IF_EnableLatchType iF_EnableLatch,EX_MA_LatchType eX_MA_Latch,MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
	}
	public char flip(char c) {return (c == '0')? '1': '0';}
	public int twosComplement(String s)//to return twos complement of a negative binary string
	{
		String res = "";
		for (int i = 0; i < s.length(); i++)
        	{
            		res += flip(s.charAt(i));
        	}
        	int ans = Integer.parseInt(res,2);
        	ans+=1;
        	return ans;
	}
	public String addBits(int instruction)//to extend length of binary string to 32
	{
		String binaryString = Integer.toBinaryString(instruction);
		String result = "";
		int sam = binaryString.length() - 32;
		while(sam<0)
		{
			result+="0";
			sam++;
		}
		result+=binaryString;
		return result;
	}
	public int performOF()
	{	
		OF_EX_Latch.set_isnop(IF_OF_Latch.get_isnop());
		int ret = 1;
		int inst = IF_OF_Latch.getInstruction();
		String instr = addBits(inst);
		System.out.println("INST = "+instr);
		int opcode = Integer.parseInt(instr.substring(0,5),2);
		//System.out.println("optype = "+optype);
		OF_EX_Latch.set_opcode(opcode);
		int r1 = Integer.parseInt(instr.substring(5,10),2);
		int r2 = Integer.parseInt(instr.substring(10,15),2);
		int op1,op2;
		if(IF_OF_Latch.isOF_enable() && !IF_OF_Latch.get_isnop())
		{
			//TODO
			//System.out.println("OF ");
			int dmarw = MA_RW_Latch.get_dest();
			int dexma = EX_MA_Latch.get_dest();
			int c1 = EX_MA_Latch.get_opcode();
			int c2 = MA_RW_Latch.get_opcode();
			/*System.out.println("IN OF");				
			System.out.println("MA_RW = "+dmarw);
			System.out.println("EX_MA = "+dexma);*/
			if(!(c1<=22 && c1>=0))
				dexma = -1;
			if(!(c2<=22 && c2>=0))
				dmarw = -1;
			if(r1==dexma && !EX_MA_Latch.get_isnop() || r1==dmarw && !MA_RW_Latch.get_isnop() || r2==dexma && !EX_MA_Latch.get_isnop()  || r2==dmarw && !MA_RW_Latch.get_isnop() ){
				ret=-1;
				IF_EnableLatch.set_isnop(true);
				IF_OF_Latch.set_isnop(true);
				OF_EX_Latch.set_isnop(true);
			}
			else{
				OF_EX_Latch.set_isnop(false);
				if(opcode <= 21)//arithmetic instructions
				{
					if(opcode % 2 == 0)//R3 type
					{
						r1 = Integer.parseInt(instr.substring(5,10),2);
						r2 = Integer.parseInt(instr.substring(10,15),2);
						op1=containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(5,10),2));
						op2=containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(10,15),2));
						int dest = Integer.parseInt(instr.substring(15,20),2);
						OF_EX_Latch.set_op1(op1);
						OF_EX_Latch.set_op2(op2);
						OF_EX_Latch.set_dest(dest);
						OF_EX_Latch.set_imm(0);
						
					}
					else//R2I type
					{
						r1 = Integer.parseInt(instr.substring(5,10),2);
						op1 = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(5,10),2));
						int dest = Integer.parseInt(instr.substring(10,15),2);
						int imm = Integer.parseInt(instr.substring(15),2);
						OF_EX_Latch.set_op1(op1);
						OF_EX_Latch.set_dest(dest);
						OF_EX_Latch.set_imm(imm);
						
					}
				}
				else if(opcode == 24)//jmp, RI type
				{
					int dest = Integer.parseInt(instr.substring(5,10),2);
					dest = containingProcessor.getRegisterFile().getValue(dest);
					String immediate = instr.substring(10);
					int imm = Integer.parseInt(immediate,2);
					if(immediate.charAt(0) == '1')
					{
						imm = -1*twosComplement(immediate);
					}
					OF_EX_Latch.set_dest(dest);
					OF_EX_Latch.set_imm(imm);
					
				}
				else if(opcode == 29)//end, RI type
				{
					int dest = Integer.parseInt(instr.substring(5,10),2);
					String immediate = instr.substring(10);
					int imm = Integer.parseInt(immediate,2);
					if (immediate.charAt(0) == '1')
					{
						imm = -1*twosComplement(immediate);
					}
					
					OF_EX_Latch.set_dest(dest);
					OF_EX_Latch.set_imm(imm);
					
				}
				else if(opcode == 22)//load, R2I type
				{
					r1 = Integer.parseInt(instr.substring(5,10),2);
					op1 = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(5,10),2));
					int dest = Integer.parseInt(instr.substring(10,15),2);
					String immediate = instr.substring(15);
					int imm = Integer.parseInt(immediate,2);
					if (immediate.charAt(0) == '1')
					{
						imm = -1*twosComplement(immediate);
					}
					
					OF_EX_Latch.set_op1(op1);
					OF_EX_Latch.set_dest(dest);
					OF_EX_Latch.set_imm(imm);
					
				}
				else if(opcode == 23)//store, R2I type
				{
					r1 = Integer.parseInt(instr.substring(5,10),2);
					op1 = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(5,10),2));
					int dest = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(10,15),2));
					String immediate = instr.substring(15);
					int imm = Integer.parseInt(immediate,2);
					if (immediate.charAt(0) == '1')
					{
						imm = -1*twosComplement(immediate);
					}
					
					OF_EX_Latch.set_op1(op1);
					OF_EX_Latch.set_dest(dest);
					OF_EX_Latch.set_imm(imm);
							
				}
				else//control flow having R2I type
				{
					r1 = Integer.parseInt(instr.substring(5,10),2);
					op1 = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(5,10),2));
					int dest = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(10,15),2));
					r2 = Integer.parseInt(instr.substring(10,15),2);
					String immediate = instr.substring(15);
					int imm = Integer.parseInt(immediate,2);
					if (immediate.charAt(0) == '1')
					{
						imm = -1*twosComplement(immediate);
					}
					
					OF_EX_Latch.set_op1(op1);
					OF_EX_Latch.set_dest(dest);
					OF_EX_Latch.set_imm(imm);
					
				}
				OF_EX_Latch.set_pc(containingProcessor.getRegisterFile().getProgramCounter()-1);
				IF_OF_Latch.setOF_enable(false);
				OF_EX_Latch.setEX_enable(true);
			}
		}
		return ret;
	}
}
