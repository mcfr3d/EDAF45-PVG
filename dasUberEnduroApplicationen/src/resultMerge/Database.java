package resultMerge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Database {
	private HashMap<Integer, Racer> racers;
	private List<String> raceClasses;
	private boolean multiLap;
	private boolean massStart = false;
	private String massStartTime;
	private List<String> columnHeaders;

	//kept so previous tests works. tests should be refactored.
	// creates a db for OneLapRace without massStart.
	public Database() {
		this(null, false);
	}
	
	public Database(String massStartTime, boolean multiLap) {
		if(massStartTime != null) {
			this.massStart = true;
			this.massStartTime = massStartTime;
		}
		this.multiLap = multiLap;
		racers = new HashMap<>();
		raceClasses = new ArrayList<String>();
	}

	// TODO: check that format of time is ok.
	public boolean setMassStart(String time) {

		boolean correctFormat = false;
		try {
			TotalTimeCalculator.timeFormater(time);
			correctFormat = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (correctFormat) {
			massStart = true;
			massStartTime = time;
			for (Racer r : racers.values()) {
				r.addStart(time);
			}
		}
		
		return correctFormat;
	}

	private Racer getRacer(int driver) {

		if (racers.containsKey(driver))
			return racers.get(driver);

		Racer r = new Racer(driver, multiLap);
		racers.put(driver, r);

		return r;
	}

	public void addStart(int driver, String time) {

		Racer r = getRacer(driver);

		r.addStart(time);
	}

	public void addFinish(int driver, String time) {

		Racer r = getRacer(driver);
		if (massStart)
			r.addStart(massStartTime);
		r.addFinish(time);
	}

	public void setName(int driver, String name) {

		Racer r = getRacer(driver);

		r.setName(name);
	}

	public void setRacerClass(int driver, String raceClass) {
		if (!raceClasses.contains(raceClass)) {
			raceClasses.add(raceClass);
		}
		Racer r = getRacer(driver);
		r.setRacerClass(raceClass);
	}
	
	public boolean isMultiLapRace() {
		return multiLap;
	}
	
	public List<String> getRaceClasses() {
		return raceClasses;
	}

	public HashMap<Integer, Racer> getRacers() {
		return racers;
	}

	public void addOptionalData(int driver,String data) {
		
		getRacer(driver).addOptionalData(data);
	}
	
	public int size() {
		return racers.size();
	}

	public void setColumnHeaders(String[] columnHeaders) {
		
		this.columnHeaders = Arrays.asList(columnHeaders);
	}

}
