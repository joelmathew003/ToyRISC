package processor.pipeline;

public class IF_EnableLatchType {
	
	boolean IF_enable;
	boolean isnop;
	
	public IF_EnableLatchType()
	{
		IF_enable = true;
		isnop = false;
	}
	public boolean isIF_enable() {
		return IF_enable;
	}
	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}
	public boolean get_isnop() {
		return isnop;
	}
	public void set_isnop(boolean isNop) {
		isnop = isNop;
	}
}
