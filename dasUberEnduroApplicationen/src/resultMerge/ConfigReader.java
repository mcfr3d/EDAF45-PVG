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
		int nbrOfLegs = 0;
		String massStartTime = null;
		String nameFilePath = null;
		String stipulatedTime = null;
		Map<Integer,LinkedList<String>> startFiles = new HashMap<>();
		Map<Integer,LinkedList<String>> finishFiles = new HashMap<>();
		boolean isLapRace = false, isLegRace = false;
		LegInfo info = null;
		
		try {

			FileReader reader = new FileReader(configFilePath);

			JSONObject root = new JSONObject(new JSONTokener(reader));

			outputFolder = root.optString("output folder", "");

			type = root.optString("race type", "maraton");
			isLapRace = type.equals("varvlopp");
			isLegRace = type.equals("etapplopp");
			massStartTime = root.optString("group start", null);
			nameFilePath = root.getString("name file");
			stipulatedTime = root.optString("stipulated time", null);
			nbrOfLegs = root.optInt("number of etapps", 1);

			if (massStartTime == null) {
				JSONArray jsonStartFiles = root.getJSONArray("start files");
				for (int i = 0; i < jsonStartFiles.length(); ++i) {
					int Leg;
					String file;
					if(isLegRace) {
						JSONObject jo = jsonStartFiles.getJSONObject(i);
						Leg = jo.optInt("etapp", -1);
						file = jo.getString("file");
					} else {
						Leg = -1;
						file = jsonStartFiles.getString(i);
					}
					
					if(startFiles.containsKey(Leg)) {
						startFiles.get(Leg).add(file);
					} else {
						LinkedList<String> f = new LinkedList<>();
						f.add(file);
						startFiles.put(Leg, f);
					}	
				}
			}
			
			if (isLegRace) {
				info = new LegInfo(root.getJSONArray("etapper"));
			}

			JSONArray jsonFinishFiles = root.getJSONArray("finish files");
			for (int i = 0; i < jsonFinishFiles.length(); ++i) {
				int Leg;
				String file;
				if(isLegRace) {
					JSONObject jo = jsonFinishFiles.getJSONObject(i);
					Leg = jo.optInt("etapp", -1);
					file = jo.getString("file");
				} else {
					Leg = -1;
					file = jsonFinishFiles.getString(i);
				}
				
				if(finishFiles.containsKey(Leg)) {
					finishFiles.get(Leg).add(file);
				} else {
					LinkedList<String> f = new LinkedList<>();
					f.add(file);
					finishFiles.put(Leg, f);
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
		if (isLegRace) {
			db = new Database(massStartTime, Database.LEG_RACE);
			db.setLegInfo(info);
			if (nbrOfLegs != 0) {
				db.setNumberLegs(nbrOfLegs);
			} else {
				throw new IllegalArgumentException("Missing nbr of Legs for Leg race");
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

		for (int leg : startFiles.keySet()) {
			for(String s: startFiles.get(leg))
				IOReader.readStart(s, db, leg);
		}
		for (int leg : finishFiles.keySet()) {
			for(String s: finishFiles.get(leg))
				IOReader.readFinish(s, db, leg);
		}
		
		ResultWriter.write(outputFolder, db);

	}

}
