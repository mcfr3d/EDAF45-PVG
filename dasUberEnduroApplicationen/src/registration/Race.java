package registration;

import java.util.HashSet;

public class Race {
	HashSet<Racer> rennen; 
	int ziffer = 0;
	public Race(){
		rennen = new HashSet<Racer>(); 
	}
	public void addierenRennfahrer(){
		rennen.add(new Racer(++ziffer));
	}
	public int betrag(){
		return rennen.size();
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (Racer dasRenner : rennen){
			sb.append(dasRenner);
			sb.append("\n");

		}
		return sb.toString();
	}
}
