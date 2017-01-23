package resultMerge;

import util.IOReader;
import util.ResultWriter;

public class Main {

	public static void run(String dir) {

		String startFilePath = dir + "/starttider.txt";
		String finishFilePath = dir + "/maltider.txt";
		String nameFilePath = dir + "/namnfil.txt";
		String resultFilePath = dir + "/resultat.txt";

		Database db = new Database();
		try {
			IOReader.readStart(startFilePath, db);
			IOReader.readFinish(finishFilePath, db);
			IOReader.readNames(nameFilePath, db);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultWriter.write(resultFilePath, db);
	}

	public static void main(String[] args) {
		run("mapp_med_shit");
	}
}
