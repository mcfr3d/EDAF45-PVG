package resultMerge;

import util.IOReader;
import util.ResultWriter;

public class Main {
	public static void main(String[] args) {
		
		String startFilePath = "mapp_med_shit/starttider.txt";
		String finishFilePath = "mapp_med_shit/maltider.txt";
		String nameFilePath = "mapp_med_shit/namnfil.txt";
		String resultFilePath = "mapp_med_shit/resultat.txt";
		
		Database db = new Database();
		try {
			IOReader.readStart(startFilePath, db); 
			IOReader.readFinish(finishFilePath, db);
			IOReader.readNames(nameFilePath, db);			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		ResultWriter.write(resultFilePath, db);
	}
}
