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
		maxLaps = Math.max(maxLaps, finishTimes.size());
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
		if(startTimes.isEmpty()){
			res[0] = Math.max(0,finishTimes.size()-1) + "";
		} else{
			res[0] = finishTimes.size() + "";
		}
		res[1] = TotalTimeCalculator.computeDifference(start, finish);
		boolean impossibleLapTime = false;
		if(finishTimes.size() > 0) {
			for(i = 0; i < finishTimes.size(); i++){
				String currentTime = TotalTimeCalculator.computeDifference(res[2 + maxLaps + i], res[3 + maxLaps + i]);
				res[2+i] = Character.isDigit(currentTime.charAt(0)) ? currentTime : "";
				// Checks if impossible laptime
				if(!TotalTimeCalculator.possibleTotalTime(res[2 + maxLaps + i], res[3 + maxLaps + i]) && Character.isDigit(currentTime.charAt(0))) impossibleLapTime = true; 
			}
		} else {
			// Missing finish time
			res[maxLaps + 3]  = "Slut?"; 
		}
		
		StringBuilder sb = new StringBuilder();
				
		for(i = 0; i < res.length; i++){
			sb.append(res[i]).append("; ");
		}
		if(startTimes.size()>1){
			multipleStartTimesGen(sb);
		}
		if(impossibleLapTime) {
			sb.append("Omöjlig varvtid?");
		}
		String out = sb.toString();
		if(out.charAt(out.length() - 2) != ';') return out;
		return out.substring(0, out.length() - 2).trim();		
	}
	
	

	private void multipleStartTimesGen(StringBuilder sb) {
		sb.append("Flera starttider?");
		for(int i=1;i< startTimes.size();i++){
			sb.append(" " + startTimes.get(i));
		}
		
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
