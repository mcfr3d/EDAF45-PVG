package resultMerge;

public class Racer {

	private String name;
	private String racerClass;
	private int startNumber;
	
	private RaceType rt;

	//kept so previous tests works. tests should be refactored.
	//creates a racer for OneLapRace.
	public Racer(int startNumber) {
		this(startNumber, false);
	}
	public Racer(int startNumber, boolean multiLap) {
		this.startNumber = startNumber;
		rt = multiLap ? new MultiLapRace(): new OneLapRace();
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
	
	//DUMMY
	public void setRacerClass(String raceClass) {
		this.racerClass = raceClass;
	}
	
	//DUMMY
	public String getRacerClass() {
		return racerClass;
	}

	public String toString() {
		return startNumber + "; " + name + "; " + rt.genResult();
	}
	
	public String getFirstStartTime() {
		return rt.getStart();
	}
}
