package processor.pipeline;

public class EX_IF_LatchType {

	boolean IF_enable;
	int branchPC;
	boolean isBranchTaken;

	public EX_IF_LatchType()
	{
		IF_enable = false;
	}
	public boolean isIF_enable()
	{
		return IF_enable;
	}
	public void setIF_enable(boolean iF_enable) 
	{
		IF_enable = iF_enable;
	}
	public boolean getIS_enable() 
	{
		return IF_enable;
	}
	public void set_BranchPC(int branchpc) 
	{
		branchPC = branchpc;
	}
	public int getPC()
	{
		return branchPC;
	}	
}
