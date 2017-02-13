package util;

import java.util.ArrayList;
import java.util.List;

public class Chart {

	private List<List<String>> rows;

	public Chart() {

		rows = new ArrayList<>();
	}
	public Chart(String s) {
		this();
		fromString(s);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (List<String> row : rows) {
			for (String word : row) {
				sb.append(word);
				sb.append("; ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append("\n");
		}
		return sb.toString();
	}

	public void fromString(String s) {
		String lines[] = s.split("\\r?\\n");
		for (int i = 0; i < lines.length; ++i) {
			String[] words = lines[i].split(";");
			List<String> l = new ArrayList<>();
			for (int j = 0; j < words.length; j++)
				l.add(words[j].trim());
			rows.add(l);
		}
	}

	public List<List<String>> getRows() {
		
		return rows;
	}
}
