package resultMerge;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import util.Getopt;
import util.IOReader;
import util.ResultWriter;

public class Main {

	public static void main(String[] args) throws IOException {

		Getopt g = new Getopt(args, "o:t:n:s:f:m:", true);

		int c;
		String outputFile = null;
		String type = null;
		String massStartTime = null;
		String nameFile = null;
		List<String> startFiles = new LinkedList<>();
		List<String> finishFiles = new LinkedList<>();

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

		if (outputFile != null && nameFile != null && type != null) {

			Database db = new Database(massStartTime, type.equals("Varvlopp"));

			IOReader.readNames(nameFile, db);
			
			for (String startFile : startFiles)
				IOReader.readStart(startFile, db);
			for (String finishFile : finishFiles)
				IOReader.readFinish(finishFile, db);

			ResultWriter.write(outputFile, db);
		}

	}

}
