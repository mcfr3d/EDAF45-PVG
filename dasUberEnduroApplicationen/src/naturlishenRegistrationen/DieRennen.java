package naturlishenRegistrationen;

import java.util.HashSet;

public class DieRennen {
	HashSet<Rennfahrer> rennen; 
	int ziffer = 0;
	public DieRennen(){
		rennen = new HashSet<Rennfahrer>(); 
	}
	public void addierenRennfahrer(){
		rennen.add(new Rennfahrer(++ziffer));
	}
	public int betrag(){
		return rennen.size();
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (Rennfahrer dasRenner : rennen){
			sb.append(dasRenner);
			sb.append("\n");

		}
		return sb.toString();
	}
}
