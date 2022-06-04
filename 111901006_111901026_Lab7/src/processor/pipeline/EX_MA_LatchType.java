package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	boolean isnop, MA_busy;
	int dest;
	int aluResult;
	int opcode;
	int pc;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
		isnop = false;
		dest = -1;
		MA_busy = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public boolean get_isnop() {
		return isnop;
	}
	public void set_isnop(boolean isNop) {
		isnop = isNop;
	}
	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}
	public void set_pc(int op){
		this.pc = op;
	}
	public int get_pc(){
		return this.pc;
	}
	public void set_aluResult(int aluRes)
	{
		aluResult = aluRes;
	}
	public int get_aluResult()
	{
		return aluResult;
	}
	public void set_dest(int rd)
	{
		dest = rd;
	}
	public int get_dest()
	{
		return dest;
	}
	public void set_opcode(int Opcode)
	{
		opcode = Opcode;
	}
	public int get_opcode()
	{
		return opcode;
	}
	public boolean isMA_busy() 
	{
		return MA_busy;
	}
	public void setMA_busy(boolean mA_busy) 
	{
		MA_busy = mA_busy;
	}
}
