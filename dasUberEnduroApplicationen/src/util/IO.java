package util;

public class IO {

	private String path;
	
	public IO() {
		
	}
	
	public void write(String s) throws IOException{
		if (path == null) {
			System.exit(1);
		}
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.println(s);
		writer.close();
	}
	
	public void initFile() throws IOException{
		write("StartNr; Namn; Totaltid; Starttid; Måltid");
	}

	
}
