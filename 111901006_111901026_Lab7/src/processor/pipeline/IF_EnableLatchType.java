package processor.pipeline;

public class IF_EnableLatchType {
	
	boolean IF_enable, IF_busy;
	boolean isnop, wrpath;
	
	public IF_EnableLatchType()
	{
		IF_enable = true;
		isnop = false;
		IF_busy = false;
		wrpath = false;
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
	public boolean isIF_busy() {
		return IF_busy;
	}
	public void setIF_busy(boolean iF_busy) {
		IF_busy = iF_busy;
	}
	public boolean is_Wrongpath() {
		return wrpath;
	}
	public void set_Wrongpath(boolean Wrpath) {
		wrpath = Wrpath;
	}
}
