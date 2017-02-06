package resultMerge;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import util.ResultWriter;

public class Output {

	private boolean html;
	private boolean sorted;
	private String path;

	public Output(JSONObject root) throws JSONException {

		html = root.optBoolean("html", false);
		sorted = root.optBoolean("sorted", false);
		path = root.getString("file");
	}

	public void write(Database db) throws Exception {

		if (html) {

			String text = db.getResult();

			List<List<String>> lines = new LinkedList<List<String>>();

			for (String line : text.split("\n")) {

				List<String> wordList = new LinkedList<>();

				for (String word : line.split("\\; ")) {

					wordList.add(word);
				}

				lines.add(wordList);
			}

			try {

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

			} catch (IOException e) {
				System.err.println("Couldn't write result to file!");
				e.printStackTrace();
			}

		} else {

			ResultWriter.write(path, db);
		}

	}

}
