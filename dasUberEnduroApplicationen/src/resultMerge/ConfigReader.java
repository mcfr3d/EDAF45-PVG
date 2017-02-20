package resultMerge;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		int nbrOfEtapps = 0;
		String massStartTime = null;
		String nameFilePath = null;
		String stipulatedTime = null;
		Map<Integer,LinkedList<String>> startFiles = new HashMap<>();
		Map<Integer,LinkedList<String>> finishFiles = new HashMap<>();
		boolean isLapRace = false, isEtappRace = false;
		try {

			FileReader reader = new FileReader(configFilePath);

			JSONObject root = new JSONObject(new JSONTokener(reader));

			outputFolder = root.optString("output folder", "");

			type = root.optString("race type", "maraton");
			isLapRace = type.equals("varvlopp");
			isEtappRace = type.equals("etapplopp");
			massStartTime = root.optString("group start", null);
			nameFilePath = root.getString("name file");
			stipulatedTime = root.optString("stipulated time", null);
			nbrOfEtapps = root.optInt("number of etapps", 1);

			if (massStartTime == null) {
				JSONArray jsonStartFiles = root.getJSONArray("start files");
				for (int i = 0; i < jsonStartFiles.length(); ++i) {
					int etapp;
					String file;
					if(isEtappRace) {
						JSONObject jo = jsonStartFiles.getJSONObject(i);
						etapp = jo.optInt("etapp", -1);
						file = jo.getString("file");
					} else {
						etapp = -1;
						file = jsonStartFiles.getString(i);
					}
					
					if(startFiles.containsKey(etapp)) {
						startFiles.get(etapp).add(file);
					} else {
						LinkedList<String> f = new LinkedList<>();
						f.add(file);
						startFiles.put(etapp, f);
					}	
				}
			}

			JSONArray jsonFinishFiles = root.getJSONArray("finish files");
			for (int i = 0; i < jsonFinishFiles.length(); ++i) {
				int etapp;
				String file;
				if(isEtappRace) {
					JSONObject jo = jsonFinishFiles.getJSONObject(i);
					etapp = jo.optInt("etapp", -1);
					file = jo.getString("file");
				} else {
					etapp = -1;
					file = jsonFinishFiles.getString(i);
				}
				
				if(finishFiles.containsKey(etapp)) {
					finishFiles.get(etapp).add(file);
				} else {
					LinkedList<String> f = new LinkedList<>();
					f.add(file);
					finishFiles.put(etapp, f);
				}
			}

		} catch (JSONException e) {

			System.out.println(e);
			System.exit(0);

		} catch (FileNotFoundException e) {

			System.out.println(e);
			System.exit(0);
		}
		

		Database db;
		if (isEtappRace) {
			db = new Database(massStartTime, Database.ETAPP_RACE);
			if (nbrOfEtapps != 0) {
				db.setNumberEtapps(nbrOfEtapps);
			} else {
				throw new IllegalArgumentException("Missing nbr of etapps for etapp race");
			}
		} else if (isLapRace) {
			db = new Database(massStartTime, Database.MULTI_LAP_RACE);
			if (stipulatedTime != null) {
				db.setStipulatedTime(stipulatedTime);
			} else {
				throw new IllegalArgumentException("Missing stipulated time for lap race");
			}
		} else {
			db = new Database(massStartTime, Database.ONE_LAP_RACE);
		}

		IOReader.readNames(nameFilePath, db);

		for (int etapp : startFiles.keySet()) {
			for(String s: startFiles.get(etapp))
				IOReader.readStart(s, db, etapp);
		}
		for (int etapp : finishFiles.keySet()) {
			for(String s: finishFiles.get(etapp))
				IOReader.readFinish(s, db, etapp);
		}
		
		ResultWriter.write(outputFolder, db);

	}

}
