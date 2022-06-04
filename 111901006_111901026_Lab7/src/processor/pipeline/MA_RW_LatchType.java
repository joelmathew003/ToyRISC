package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	boolean isnop, RW_busy;
	int aluresult; 
	int dest;
	int opcode;

	public MA_RW_LatchType()
	{
		RW_enable = false;
		isnop = false;
		dest = -1;
		RW_busy = false;
	}

	public boolean isRW_enable() 
	{
		return RW_enable;
	}
	public boolean get_isnop() 
	{
		return isnop;
	}
	public void set_isnop(boolean isNop) 
	{
		isnop = isNop;
	}
	public void setRW_enable(boolean rW_enable) 
	{
		RW_enable = rW_enable;
	}
	public void set_aluResult(int Aluresult)
	{
		aluresult = Aluresult;
	}
	public int get_aluResult()
	{
		return aluresult;
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
	public boolean isRW_busy() 
	{
		return RW_busy;
	}
	public void setRW_busy(boolean rW_busy) 
	{
		RW_busy = rW_busy;
	}
}
