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
		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.print(db.getResult(false));
			writer.close();
		} catch (IOException e) {
			System.err.println("Couldn't write result to file!");
			System.err.println(e);
		}

	}
}
