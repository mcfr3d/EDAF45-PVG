package resultMerge;

import util.ResultWriter;

public class Main {
	public static void main(String[] args) {
		
		String startFilePath = "../../mapp_med_shit/starttider.txt";
		String finishFilePath = "../../mapp_med_shit/maltider.txt";
		String nameFilePath = "../../mapp_med_shit/nanmnfil.txt";
		String resultFilePath = "../../mapp_med_shit/resultat.txt";
		
		Database db = new Database();
		IOReader.readTimes(startFilePath, finishFilePath, db);
		IOReader.readNames(nameFilePath, db);
		ResultWriter.write(resultFilePath, db);
	}
}
