package resultMerge;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class EtappInfo {
	private List<Etapp> etapper;
	private class Etapp {
		public Time minTime;
		public int multiplier;
		
		public Etapp(JSONObject o, int index) {
			minTime = new Time(o.getString("minimum time"), true, index);
			multiplier = o.optInt("multiplier", 1);
		}
	}
	public EtappInfo(JSONArray array) {
		etapper = new LinkedList<>();
		for (int i = 0; i < array.length(); i++) {
			etapper.add(new Etapp(array.getJSONObject(i), i));
		}
	}
	public Time getMinimumTime(int index) {
		return etapper.get(index).minTime;
	}
	public int getMultiplier(int index) {
		return etapper.get(index).multiplier;
	}
}
