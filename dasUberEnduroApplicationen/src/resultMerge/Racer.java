package resultMerge;

public class Racer {

	private String name;
	private int startNumber;
	
	private RaceType rt = new OneLapRace();

	public Racer(int startNumber) {
		this.startNumber = startNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addStart(String startTime) {
		rt.addStart(startTime);
	}

	public void addFinish(String finishTime) {
		rt.addFinish(finishTime);
	}

	public String toString() {
		return startNumber + "; " + name + "; " + rt.genResult();
	}
	
	public String getFirstStartTime() {
		return rt.getStart();
	}
}
