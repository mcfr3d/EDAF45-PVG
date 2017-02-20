package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import resultMerge.Database;
import resultMerge.MultiLapRace;
import resultMerge.Racer;

public class ResultWriter {

	public static void write(String path, Database db) {
		try {
			writeSorted(path + "output.txt", db);
			writeUnsorted(path + "output_unsorted.txt", db);
			writeHtml(path + "output.html", db);
		} catch (IOException e) {
			System.err.println("Couldn't write result to file!");
			System.err.println(e);
		}

	}

	private static void writeSorted(String path, Database db)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.print(db.getResult(true));
		writer.close();
	}

	private static void writeUnsorted(String path, Database db)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.print(db.getResult(false));
		writer.close();
	}

	private static void writeHtml(String path, Database db) throws FileNotFoundException, UnsupportedEncodingException {
		String text = db.getResult(true);

		List<List<String>> lines = new LinkedList<List<String>>();

		for (String line : text.split("\n")) {
			List<String> wordList = new LinkedList<>();

			for (String word : line.split("\\; ")) {
				wordList.add(word);
			}
			lines.add(wordList);
		}

		PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.println("<!DOCTYPE html>");
		writer.println("  <html lang=\"sv\">");
		writer.println("    <head>");
		writer.println("      <style>");
		writer.println("        table, th, td {");
		writer.println("          border: 1px solid black;");
		writer.println("        }");
		writer.println("        caption {");
		writer.println("          font-weight: bold;");
		writer.println("          border-width: 2px;");
		writer.println("          border-color: \"#D3E51D\";");
		writer.println("          font-size: 500%;");
		writer.println("        }");
		writer.println("      </style>");
		writer.println("      <title>Page Title</title>");
		writer.println("      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		writer.println("    </head>");
		writer.println("    <body style=\"background-color: #1733AF\">");
		writer.println("    <table style=\"width:100%\" bgcolor=\"#D3E51D\">");
		writer.println("      <caption><font color=\"yellow\">Das Über HTML Resultat</font></caption>");
		
		for (List<String> line : lines) {
			writer.println("      <tr>");
			for (String word : line) {
				writer.println("        <th> " + word + "</th>");
			}
			writer.println("      </tr>");
		}
		
		writer.println("    </table>");
		writer.println("  </body>");
		writer.println("</html>");
		writer.close();

	}
}
