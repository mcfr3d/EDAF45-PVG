package resultMerge;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import util.IOReader;
import util.ResultWriter;

public class ConfigReader {

	public static void readConfig(String configFilePath) throws Exception {

		
		String outputFolder = null;
		String type = "maraton";
		String massStartTime = null;
		String nameFilePath = null;
		String stipulatedTime = null;
		List<String> startFiles = new LinkedList<>();
		List<String> finishFiles = new LinkedList<>();

		try {

			FileReader reader = new FileReader(configFilePath);

			JSONObject root = new JSONObject(new JSONTokener(reader));

			outputFolder = root.optString("output folder", "");
			
			type = root.optString("race type", "maraton");
			massStartTime = root.optString("group start", null);
			nameFilePath = root.getString("name file");
			stipulatedTime = root.optString("stipulated time", null);


            if (massStartTime == null) {
    			JSONArray jsonStartFiles = root.getJSONArray("start files");
            	for(int i = 0; i < jsonStartFiles.length(); ++i)
            		startFiles.add(jsonStartFiles.getString(i));
            }
            
			JSONArray jsonFinishFiles = root.getJSONArray("finish files");
            for(int i = 0; i < jsonFinishFiles.length(); ++i)
            	finishFiles.add(jsonFinishFiles.getString(i));
			
		} catch (JSONException e) {

			System.out.println(e);
			System.exit(0);

		} catch (FileNotFoundException e) {

			System.out.println(e);
			System.exit(0);
		}
		boolean isLapRace = type.equals("varvlopp");
		Database db = new Database(massStartTime, isLapRace);
		
		if(isLapRace) {
			if(stipulatedTime != null) {
				db.setStipulatedTime(stipulatedTime);
			} else {
				throw new IllegalArgumentException("Missing stipulated time for lap race");
			}
		}

		IOReader.readNames(nameFilePath, db);
		
		for (String startFile : startFiles)
			IOReader.readStart(startFile, db);
		for (String finishFile : finishFiles)
			IOReader.readFinish(finishFile, db);

		ResultWriter.write(outputFolder, db);

	}

}
