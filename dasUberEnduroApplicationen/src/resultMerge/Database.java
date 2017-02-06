package resultMerge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import util.TotalTimeCalculator;

public class Database {
	private HashMap<Integer, Racer> racers;
	private List<String> raceClasses;
	private boolean multiLap;
	private boolean massStart = false;
	private String massStartTime;
	private String stipulatedTime = "00.00.00";
	private String[] columnHeaders;

	// kept so previous tests works. tests should be refactored.
	// creates a db for OneLapRace without massStart.
	public Database() {
		this(null, false);
	}

	public Database(String massStartTime, boolean multiLap) {
		if (massStartTime != null) {
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

	public void addRacer(int startNo, String name, String raceClass) {
		Racer r = new Racer(startNo, multiLap);
		r.setName(name);
		r.setRacerClass(raceClass);
		if (racers.containsKey(startNo)) {
			System.err.println("A racer with the startNo" + startNo + " was already in database");
		} else {
			racers.put(startNo, r);
		}
	}

	private Racer getRacer(int driver) {
		if (!racers.containsKey(driver))
			addRacer(driver, "", "Ej Anmäld");
		return racers.get(driver);
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

	public void addOptionalData(int driver, String data) {

		getRacer(driver).addOptionalData(data);
	}

	public int size() {
		return racers.size();
	}
	public void setStipulatedTime(String stipulatedTime) {
		this.stipulatedTime = stipulatedTime;
	}

	public String getResult(boolean sort) {
		StringBuilder sb = new StringBuilder();
		HashMap<String, HashSet<Racer>> raceClasses = new HashMap<>();
		for (Racer r : racers.values()) {
			String raceC = r.getRacerClass();
			if (!raceClasses.containsKey(raceC)) {
				raceClasses.put(raceC, new HashSet<>());
			}
			raceClasses.get(raceC).add(r);
		}		
		for (String s : raceClasses.keySet()) {
			sb.append(genResultForClass(s, raceClasses.get(s), sort));
		}

		return sb.toString();
	}

	private String genResultForClass(String raceClass, HashSet<Racer> racersInClass, boolean sort) {

		int maxLaps = 0;
		for (Racer r : racersInClass) {
			maxLaps = Math.max(maxLaps, r.getLaps());
		}
		StringBuilder sb = new StringBuilder();
		if (!raceClass.equals(""))
			sb.append(raceClass).append('\n');
		// should be done dependent on what race we have
		MultiLapRace.setMaxLaps(maxLaps); 

		if(sort) {			
			// Adding placement to header
			sb.append("Plac; ");
			sb.append(genHeader(maxLaps)).append('\n');
			
			// Sorting and writing
			writeSortedResult(sb, maxLaps, racersInClass);
		} else {
			sb.append(genHeader(maxLaps)).append('\n');
			for (Racer r : racersInClass) {
				sb.append(r.toString()).append('\n');
			}
		}

		return sb.toString();
	}

	private void writeSortedResult(StringBuilder sb, int maxLaps, HashSet<Racer> racers) {
		ArrayList<Racer> sortedRacerList = new ArrayList<>();
		ArrayList<Racer> invalidStipulatedTime = new ArrayList<>();
		
		for (Racer r : racers) {
			
			String rTime = TotalTimeCalculator.computeDifference(r.getFirstStartTime(), r.getFinishTime());
			if(rTime.compareTo(stipulatedTime) > 0) {
				if (sortedRacerList.isEmpty()) {
					sortedRacerList.add(r);
				} else {
					int index = 0;
					while (index < sortedRacerList.size() && r.compareTo(sortedRacerList.get(index)) > 0)
						index++;
					sortedRacerList.add(index, r);
				}				
			} else {
				invalidStipulatedTime.add(r);
			}
		}
		
		for(int i = 1; i <= sortedRacerList.size(); i++) {
			sb.append(i + "; " + sortedRacerList.get(i-1).toString()).append('\n');
		}
		for(int i = 0; i < invalidStipulatedTime.size(); i++) {
			sb.append(invalidStipulatedTime.get(i).toString()).append('\n');
		}
	}

	private String genHeader(int laps) {
		StringBuilder sb = new StringBuilder();
		sb.append(genRacerHeader());
		sb.append(genRaceTypeHeader(laps));
		return sb.toString();
	}

	private String genRacerHeader() {
		StringBuilder sb = new StringBuilder();
		for (String s : columnHeaders) {
			sb.append(s + "; ");
		}
		return sb.toString();
	}

	private String genRaceTypeHeader(int laps) {
		if (!multiLap) {
			return "Totaltid; Starttid; Måltid";
		} else {
			StringBuilder header = new StringBuilder("#Varv; TotalTid;");
			for (int i = 1; i <= laps; i++) {
				header.append(" Varv" + i + ";");
			}
			header.append(" Start;");
			for (int i = 1; i < laps; i++) {
				header.append(" Varvning" + i + ";");
			}
			header.append(" Mål");
			return header.toString();
		}
	}

	public void setColumnHeaders(String[] columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

}
