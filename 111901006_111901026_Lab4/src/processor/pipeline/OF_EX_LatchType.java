package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	int opcode;
	int op1;
	int op2;
	int imm;
	int dest;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}
	public int get_opcode()
	{
		return opcode;
	}
	public void set_opcode(int Opcode)
	{
		opcode = Opcode;
	}
	public int get_op1()
	{
		return op1;
	}
	public void set_op1(int oper1)
	{
		this.op1 = oper1;
	}
	public int get_op2()
	{
		return op2;
	}
	public void set_op2(int oper2)
	{
		this.op2 = oper2;
	}
	public int get_imm()
	{
		return imm;
	}
	public void set_imm(int immediate)
	{
		this.imm = immediate;
	}
	public int get_dest()
	{
		return dest;
	}
	public void set_dest(int Dest)
	{
		this.dest = Dest;
	}
}
