package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import resultMerge.Database;
import resultMerge.Racer;

public class ResultWriter {

	public static void write(String path, Database db) {

		Map<Integer, Racer> racers = db.getRacers();
		List<String> raceClasses = db.getRaceClasses();
		Map<String, List<Racer>> classifiedRacers = new HashMap<String, List<Racer>>();

		if (raceClasses.isEmpty()) {
			try {
				PrintWriter writer = new PrintWriter(path, "UTF-8");
				writer.println("StartNr; Namn; Totaltid; Starttid; Måltid");
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
					writer.println("StartNr; Namn; Totaltid; Starttid; Måltid");
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
