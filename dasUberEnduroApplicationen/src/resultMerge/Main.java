package resultMerge;

import java.util.LinkedList;
import java.util.List;

import util.Getopt;
import util.IOReader;
import util.ResultWriter;

public class Main {

	public static void main(String[] args) {

		if (args.length == 0) {
			try {
				String configFilePath = "config.json";
				ConfigReader.readConfig(configFilePath);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		} else {
			try {

				int c;
				String outputFolder = "";
				String type = "maraton";
				String massStartTime = null;
				String nameFile = null;
				String stipulatedTime = null;
				String configFilePath = null;
				List<String> startFiles = new LinkedList<>();
				List<String> finishFiles = new LinkedList<>();

				Getopt g = new Getopt(args, "o:t:n:s:f:m:l:c:", true);
				while ((c = g.getOption()) != -1) {

					switch (c) {

					case 'l':
						stipulatedTime = g.getOptarg();
						break;

					case 'o':
						outputFolder = g.getOptarg();
						break;

					case 'm':
						massStartTime = g.getOptarg();
						break;

					case 's':
						startFiles.add(g.getOptarg());
						break;

					case 'f':
						finishFiles.add(g.getOptarg());
						break;

					case 't':
						type = g.getOptarg();
						break;

					case 'n':
						nameFile = g.getOptarg();
						break;

					case 'c':
						configFilePath = g.getOptarg();
						break;
						
					default:
						System.out.print("getopt() returned " + c + "\n");
					}
				}
				if (configFilePath != null) {
					ConfigReader.readConfig(configFilePath);
				} else if (nameFile != null) {

					System.out.println(type.equals("varvlopp"));
					Database db = new Database(massStartTime, type.equals("varvlopp"));

					IOReader.readNames(nameFile, db);
					if (type.equals("varvlopp")) {
						if (stipulatedTime != null) {
							db.setStipulatedTime(stipulatedTime);
						} else {
							System.out.println("Otillräcklig indata");
						}
					}

					for (String startFile : startFiles)
						IOReader.readStart(startFile, db);
					for (String finishFile : finishFiles)
						IOReader.readFinish(finishFile, db);

					ResultWriter.write(outputFolder, db);

				} else {

					System.out.println("Otillräcklig indata");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
