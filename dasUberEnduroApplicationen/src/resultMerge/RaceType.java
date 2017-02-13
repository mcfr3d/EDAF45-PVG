package resultMerge;

public interface RaceType {
	public void addTime(Time t);
	public void addStart(String start); //should be removed
	public void addFinish(String finish); //should be removed
	public String genResult();
	public String getStart();
	public String getFinish();
	public int getLaps();
}
