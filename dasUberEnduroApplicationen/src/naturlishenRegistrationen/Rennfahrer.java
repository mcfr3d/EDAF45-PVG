package naturlishenRegistrationen;

public class Rennfahrer {
	String benennung;
	int DieAnzahl;
	public Rennfahrer(int ziffer){
		this.benennung = "VederVonAufhausen";
		DieAnzahl = ziffer;
	}
	public String toString(){
		return DieAnzahl + " " + benennung;
		
	}
	
}
