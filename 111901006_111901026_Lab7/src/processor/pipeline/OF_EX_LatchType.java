package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable,EXbusy;
	boolean isnop;
	int opcode;
	int op1;
	int op2;
	int imm;
	int dest;
	int pc;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
		isnop = false;
		dest = -1;
		EXbusy = false;
	}
	public boolean isEX_enable() {
		return EX_enable;
	}
	public boolean get_isnop() {
		return isnop;
	}
	public void set_isnop(boolean isNop) {
		isnop = isNop;
	}
	public void setEX_busy(boolean eXbusy) {
		EXbusy = eXbusy;
	}
	public boolean isEX_busy() {
		return EXbusy;
	}
	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}
	public void set_pc(int PC){
		this.pc = PC;
	}
	public int get_pc(){
		return pc;
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
