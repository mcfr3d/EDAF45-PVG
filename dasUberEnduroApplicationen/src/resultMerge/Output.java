package resultMerge;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import util.ResultWriter;

public class Output {

	private boolean html;
	private boolean sorted;
	private String path;
	
	public Output(JSONObject root) throws JSONException {
		
		html = root.optBoolean("html",false);
		sorted = root.optBoolean("sorted",false);
		path = root.getString("file");
	}
	
	public void write(Database db) throws Exception {
		
		if(html) {
					
			try {
				PrintWriter writer = new PrintWriter(path, "UTF-8");
				writer.print("snafkjnasf");
				writer.print(db.getResult(false));
				writer.print("asdjknasd");
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
