package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import resultMerge.Database;
import resultMerge.MultiLapRace;
import resultMerge.Racer;

public class ResultWriter {

	public static void write(String path, Database db) {

		Map<Integer, Racer> racers = db.getRacers();
		List<String> raceClasses = db.getRaceClasses();
		Map<String, List<Racer>> classifiedRacers = new HashMap<String, List<Racer>>();
		//StartNr; Namn; #Varv; TotalTid; Varv1; Varv2; Varv3; Start; Varvning1; Varvning2; Mål
		String header = "";
		if (db.isMultiLapRace()) {
			int nbrLaps = MultiLapRace.maxLaps;
			StringBuilder sb = new StringBuilder();
			sb.append("StartNr; Namn; #Varv; TotalTid; ");
			for (int i = 1; i <= nbrLaps; i++) {
				sb.append("Varv" + i + "; ");
			}
			sb.append("Start; ");
			for (int i = 1; i < nbrLaps; i++) {
				sb.append("Varvning"+ i + "; ");
			}
			sb.append("Mål");
			header = sb.toString();
		} else {
			header = "StartNr; Namn; Totaltid; Starttid; Måltid";
		}

		if (raceClasses.isEmpty()) {
			try {
				PrintWriter writer = new PrintWriter(path, "UTF-8");
				writer.println(header);
				for (Map.Entry<Integer, Racer> racer : racers.entrySet()) {
					writer.println(racer.getValue());
				}
				writer.close();
			} catch (IOException e) {
				System.exit(1);
			}
		} else {

			for (String raceClass : raceClasses) {
				classifiedRacers.put(raceClass, new ArrayList<Racer>());
			}

			for (Map.Entry<Integer, Racer> racer : racers.entrySet()) {
				String currentRacerClass = racer.getValue().getRacerClass();
				List<Racer> currentRacerClassList = classifiedRacers.get(currentRacerClass);
				currentRacerClassList.add(racer.getValue());
			}

			try {
				PrintWriter writer = new PrintWriter(path, "UTF-8");

				for (Map.Entry<String, List<Racer>> racerClassList : classifiedRacers.entrySet()) {
					String racerClass = racerClassList.getKey();
					writer.println(racerClass);
					writer.println(header);
					for (Racer racer : racerClassList.getValue()) {
						writer.println(racer);
					}
				}
				writer.close();

			} catch (IOException e) {
				System.exit(1);
			}
		}
	}
}
