package resultMerge;

import java.util.LinkedList;
import java.util.List;
import util.TotalTimeCalculator;

public class Racer implements Comparable<Racer> {

	private String name;
	private String racerClass;
	private int startNumber;
	private List<String> optionalData = new LinkedList<>();

	private RaceType rt;

	// kept so previous tests works. tests should be refactored.
	// creates a racer for OneLapRace.
	public Racer(int startNumber) {
		this(startNumber, Database.ONE_LAP_RACE);
	}

	public Racer(int startNumber, int raceType) {
		this.startNumber = startNumber;
		switch (raceType) {
		case Database.ONE_LAP_RACE :
			rt = new OneLapRace();
			break;
		case Database.MULTI_LAP_RACE :
			rt = new MultiLapRace();
			break;
		}
	}
	
	public Racer(int startNumber, int raceType, int nbrOfLegs) {
		this.startNumber = startNumber;
		rt = new LegRace(nbrOfLegs);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addTime(Time t) {
		rt.addTime(t);
	}

	public void setRacerClass(String raceClass) {
		this.racerClass = raceClass;
	}

	public String getRacerClass() {
		return racerClass;
	}

	public int getStartNumber() {
		return startNumber;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String s : optionalData) {
			sb.append(s + "; ");
		}
		if (name == null || name.equals(""))
			return startNumber + "; ";
		else
			return startNumber + "; " + name + "; " + sb.toString();
	}
	
	public String result() {
		return toString() + rt.genResult();
	}
	public String resultWithErrors(Database db) {
		return toString() + rt.genResultWithErrors(db);
	}

	public String getFirstStartTime() {
		return rt.getStart();
	}

	public String getFinishTime() {
		return rt.getFinish();
	}

	public int getLaps() {
		return rt.getLaps();
	}
	
	public Time getTotalTime() {
		return rt.getTotalTime();
	}

	public void addOptionalData(String data) {

		optionalData.add(data);
	}

	public int compareTo(Racer other) {
		int i = rt.compareTo(other.rt);
		return i == 0 ? (this.startNumber - other.startNumber) : i;
	}
}
