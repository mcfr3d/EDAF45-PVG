package resultMerge;

import java.util.HashMap;

public class Database {
	private HashMap<Integer, Racer> racers;

	private boolean massStart = false;
	private String massStartTime;

	public Database() {
		racers = new HashMap<>();
	}

	// TODO: check that format of time is ok.
	public void setMassStart(String time) {
		massStart = true;
		massStartTime = time;
		for (Racer r : racers.values()) {
			r.addStart(time);
		}
	}

	private Racer getRacer(int driver) {

		if (racers.containsKey(driver))
			return racers.get(driver);

		Racer r = new Racer(driver);
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

		Racer r = getRacer(driver);
		r.setRacerClass(raceClass);
	}

	public HashMap<Integer, Racer> getRacers() {
		return racers;
	}

	public int size() {
		return racers.size();
	}

}
