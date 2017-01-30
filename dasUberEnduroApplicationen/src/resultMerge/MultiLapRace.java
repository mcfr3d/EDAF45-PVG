package resultMerge;

import java.util.LinkedList;
import java.util.TreeSet;

public class MultiLapRace implements RaceType {
	public static int maxLaps = 0;
	//public static String raceTime = ""; should be used for errorhandling.
	private LinkedList<String> startTimes = new LinkedList<>();
	private TreeSet<String> finishTimes = new TreeSet<>();
	private String start = "";
	private String finish = "";
	@Override
	public void addStart(String start) {
		if(startTimes.isEmpty()) {
			this.start = start;
		}
		startTimes.add(start);
		maxLaps = Math.max(maxLaps, finishTimes.size());
	}

	@Override
	public void addFinish(String finish) {
		finishTimes.add(finish);
		this.finish = finishTimes.last();
		if(startTimes.size() > 0){
			maxLaps = Math.max(maxLaps, finishTimes.size());
		}
	}

	@Override
	public String genResult() {
		String[] res = new String[maxLaps*2 + 3];
		for(int i = 0; i < res.length; i++){
			res[i] = "";
		}
		res[maxLaps + 2] = startT();
		int i = maxLaps + 3;
		for(String s : finishTimes){
			res[i++] = s;
		}
		res[0] = finishTimes.size() + "";
		res[1] = TotalTimeCalculator.computeDifference(start, finish);
		for(i = 0; i < finishTimes.size(); i++){
			res[2+i] = TotalTimeCalculator.computeDifference(res[2 + maxLaps + i], res[3 + maxLaps + i]);
		}
		
		StringBuilder sb = new StringBuilder();
				
		for(i = 0; i < res.length; i++){
			sb.append(res[i]).append("; ");
		}
		String out = sb.toString();
		if(out.charAt(out.length() - 2) != ';') return out;
		return out.substring(0, out.length() - 2);		
	}

	@Override
	public String getStart() {
		
		return start;
	}

	@Override
	public String getFinish() {
		return finish;
	}
	private String startT() {
		if(startTimes.size() > 0) return start;
		else return "Start?";
	}
	
	//Should only be used during testing
	public void resetStatic() {
		maxLaps = 0;
	}

}
