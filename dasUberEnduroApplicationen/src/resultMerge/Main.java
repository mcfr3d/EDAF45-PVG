package resultMerge;

import java.util.LinkedList;
import java.util.List;

import util.Getopt;
import util.IOReader;
import util.ResultWriter;

public class Main {

	public static void main(String[] args) {

		try {

			int c;
			String outputFile = null;
			String type = "maraton";
			String massStartTime = null;
			String nameFile = null;
			List<String> startFiles = new LinkedList<>();
			List<String> finishFiles = new LinkedList<>();

			Getopt g = new Getopt(args, "o:t:n:s:f:m:", true);

			while ((c = g.getOption()) != -1) {

				switch (c) {

				case 'o':
					outputFile = g.getOptarg();
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

				default:
					System.out.print("getopt() returned " + c + "\n");
				}
			}

			if (outputFile != null && nameFile != null) {

				Database db = new Database(massStartTime, type.equals("varvlopp"));

				IOReader.readNames(nameFile, db);

				for (String startFile : startFiles)
					IOReader.readStart(startFile, db);
				for (String finishFile : finishFiles)
					IOReader.readFinish(finishFile, db);

				ResultWriter.write(outputFile,db);
				
			} else {
				
				System.out.println("Otillräcklig indata");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
