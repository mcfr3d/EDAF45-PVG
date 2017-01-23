package resultMerge;
import java.util.HashMap;

public class Database {
	private HashMap<Integer, Racer> racers;

	public Database(){
		racers = new HashMap<>();
	}

	public void addStart(int driver, String time){
		if(racers.containsKey(driver)) {
			racers.get(driver).addStart(time);
		} else {
			Racer r = new Racer(driver);
			r.addStart(time);
			racers.put(driver, r);
		}
	}

	public void addFinish(int driver, String time){
		if(racers.containsKey(driver)) {
			racers.get(driver).addFinish(time);
		} else {
			Racer r = new Racer(driver);
			r.addFinish(time);
			racers.put(driver, r);
		}
	}
}
