package resultMerge;
import java.util.HashMap;

public class Database {
	private HashMap<Integer, Racer> racers;

	public Database() {
		racers = new HashMap<>();
	}

	private Racer getRacer(int driver) {
				
		if(racers.containsKey(driver)) return racers.get(driver);
			
		Racer r = new Racer(driver);
		racers.put(driver,r);
		
		return r;
	}
	
	public void addStart(int driver, String time){
		
		Racer r = getRacer(driver);
		
		r.addStart(time);
	}

	public void addFinish(int driver, String time){

		Racer r = getRacer(driver);
		
		r.addFinish(time);
	}

	public void setName(int driver,String name){

		Racer r = getRacer(driver);
		
		r.setName(name);
	}
	
	public HashMap<Integer, Racer> getRacers() {
		return racers;
	}
	
	public int size() {
		return racers.size();
	}
}
