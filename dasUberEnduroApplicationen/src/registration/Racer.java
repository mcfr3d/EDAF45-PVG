package registration;

public class Racer {
	String benennung;
	int DieAnzahl;
	public Racer(int ziffer){
		this.benennung = "VederVonAufhausen";
		DieAnzahl = ziffer;
	}
	public String toString(){
		return DieAnzahl + " " + benennung;
		
	}
	
}
