package resultMerge;

import java.util.ArrayList;
import java.util.Arrays;
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
	private List<String> columnHeaders;

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

	public String getResult() {
		StringBuilder sb = new StringBuilder();
		HashMap<String, HashSet<Racer>> raceClasses = new HashMap<>();
		for (Racer r : racers.values()) {
			String raceC = r.getRacerClass();
			if (!raceClasses.containsKey(r.getRacerClass())) {
				raceClasses.put(raceC, new HashSet<>());
			}
			raceClasses.get(raceC).add(r);
		}

		for (String s : raceClasses.keySet()) {
			sb.append(genResultForClass(s, raceClasses.get(s)));
		}

		return sb.toString();
	}

	private String genResultForClass(String raceClass, HashSet<Racer> racersInClass) {
		int maxLaps = 0;
		for (Racer r : racersInClass) {
			maxLaps = Math.max(maxLaps, r.getLaps());
		}
		StringBuilder sb = new StringBuilder();
		if (!raceClass.equals(""))
			sb.append(raceClass).append('\n');
		sb.append(genHeader(maxLaps)).append('\n');
		// should be done dependent on what race we have
		MultiLapRace.setMaxLaps(maxLaps);

		for (Racer r : racersInClass) {
			sb.append(r.toString()).append('\n');
		}

		return sb.toString();
	}

	private String genHeader(int laps) {
		if (!multiLap) {
			return "StartNr; Namn; Totaltid; Starttid; Måltid";
		}
		StringBuilder header = new StringBuilder("StartNr; Namn; #Varv; TotalTid;");
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

	public void setColumnHeaders(String[] columnHeaders) {

		this.columnHeaders = Arrays.asList(columnHeaders);
	}

}
