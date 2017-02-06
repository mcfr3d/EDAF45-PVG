package resultMerge;

import java.util.LinkedList;
import java.util.List;

public class Racer implements Comparable<Racer>{

	private String name;
	private String racerClass;
	private int startNumber;
	private List<String> optionalData = new LinkedList<>();
	
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
	
	public String getFinishTime() {
		return rt.getFinish();
	}
	
	public int getLaps() {
		return rt.getLaps();
	}
	
	public void addOptionalData(String data) {
		
		optionalData.add(data);
	}
	public int compareTo(Racer racer) {
		
		// Compare nbr of laps
		if(racer.getLaps() != this.getLaps()){
			return racer.getLaps() - this.getLaps(); 
		}
		
		// Compare total time
		String resThis = TotalTimeCalculator.computeDifference(rt.getStart(), rt.getFinish());
		String resInput = TotalTimeCalculator.computeDifference(racer.rt.getStart(), racer.rt.getFinish());
		int timeDiff = resThis.compareTo(resInput);
		if(timeDiff != 0){
			return timeDiff;
		}
		
		// Compare start nbr
		if(this.startNumber != racer.startNumber) {
			return this.startNumber - racer.startNumber;
		}
		return 0;
	}
}
