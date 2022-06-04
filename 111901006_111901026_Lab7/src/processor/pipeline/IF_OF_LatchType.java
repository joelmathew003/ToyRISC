package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	boolean isnop, OF_busy;
	int instruction, PC;
	
	public IF_OF_LatchType()
	{
		OF_enable = false;
		isnop = false;
		OF_busy = false;
	}
	
	public boolean isOF_busy() {
		return OF_busy;
	}
	public void setOF_busy(boolean oF_busy) {
		OF_busy = oF_busy;
	}
	
	public boolean isOF_enable() {
		return OF_enable;
	}
	public boolean get_isnop() {
		return isnop;
	}
	
	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public void set_isnop(boolean isNop) {
		isnop = isNop;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
	public int getPC() {
		return PC;
	}

	public void setPC(int pC) {
		this.PC = pC;
	}
}
