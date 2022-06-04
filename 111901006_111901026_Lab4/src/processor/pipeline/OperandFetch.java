package processor.pipeline;

import processor.Processor;
import java.util.Scanner;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
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
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			//TODO
			int instruction = IF_OF_Latch.getInstruction();
			String instr = addBits(instruction);
			int optype = Integer.parseInt(instr.substring(0,5),2);
			OF_EX_Latch.set_opcode(optype);
			if(optype <= 21)//arithmetic instructions
			{
				if(optype % 2 == 0)//R3 type
				{
					int op1 = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(5,10),2));
					int op2 = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(10,15),2));
					int dest = Integer.parseInt(instr.substring(15,20),2);
					OF_EX_Latch.set_op1(op1);
					OF_EX_Latch.set_op2(op2);
					OF_EX_Latch.set_dest(dest);
					OF_EX_Latch.set_imm(0);
				}
				else//R2I type
				{
					int op1 = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(5,10),2));
					int dest = Integer.parseInt(instr.substring(10,15),2);
					int imm = Integer.parseInt(instr.substring(15),2);
					OF_EX_Latch.set_op1(op1);
					OF_EX_Latch.set_dest(dest);
					OF_EX_Latch.set_imm(imm);
				}
			}	
			else if((optype == 22))//load, R2I type
			{
				int op1 = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(5,10),2));
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
			else if(optype == 23)//store, R2I type
			{
				int op1 = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(5,10),2));
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
			else if(optype == 24)//jmp, RI type
			{
				int dest = Integer.parseInt(instr.substring(5,10),2);
				dest = containingProcessor.getRegisterFile().getValue(dest);
				String immediate = instr.substring(10);
				int imm = Integer.parseInt(immediate,2);
				if (immediate.charAt(0) == '1')
				{
					imm = -1*twosComplement(immediate);
				}
				OF_EX_Latch.set_dest(dest);
				OF_EX_Latch.set_imm(imm);
			}
			else if(optype == 29)//end, RI type
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
			else//control flow having R2I type
			{
				int op1 = containingProcessor.getRegisterFile().getValue(Integer.parseInt(instr.substring(5,10),2));
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
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
