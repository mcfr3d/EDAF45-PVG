package resultMerge;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class LegInfo {
	private List<Leg> legs;
	private class Leg {
		public Time minTime;
		public int multiplier;
		
		public Leg(JSONObject o, int index) {
			minTime = new Time(o.getString("minimum time"), true, index);
			multiplier = o.optInt("multiplier", 1);
		}
	}
	public LegInfo(JSONArray array) {
		legs = new LinkedList<>();
		for (int i = 0; i < array.length(); i++) {
			legs.add(new Leg(array.getJSONObject(i), i));
		}
	}
	public Time getMinimumTime(int index) {
		return legs.get(index).minTime;
	}
	public int getMultiplier(int index) {
		return legs.get(index).multiplier;
	}
	public int getNbrOfLegs() {
		return legs.size();
	}
}
