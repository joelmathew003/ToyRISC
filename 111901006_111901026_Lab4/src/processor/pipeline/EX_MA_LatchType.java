package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	int dest;
	int aluResult;
	int opcode;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}
	public boolean isMA_enable() {
		return MA_enable;
	}
	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
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
}
