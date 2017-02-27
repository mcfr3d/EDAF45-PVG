package resultMerge;

public interface RaceType extends Comparable<RaceType> {
	public void addTime(Time t);
	public String genResult();
	public String genResultWithErrors(Database db);
	public String getStart();
	public String getFinish();
	public int getLaps();
}
