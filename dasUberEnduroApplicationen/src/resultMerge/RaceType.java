package resultMerge;

public interface RaceType {
	public void addStart(String start);
	public void addFinish(String finish);
	public String genResult();
	public String getStart();
	public String getFinish();
	public int getLaps();
}
