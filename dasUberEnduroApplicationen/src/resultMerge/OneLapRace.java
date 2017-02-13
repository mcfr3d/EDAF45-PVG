package resultMerge;

import java.util.LinkedList;

import util.TotalTimeCalculator;

public class OneLapRace implements RaceType {
	private LinkedList<String> startTimes = new LinkedList<>();
	private LinkedList<String> finishTimes = new LinkedList<>();
	private String start = "";
	private String finish = "";
	@Override
	public void addStart(String start) {
		if(startTimes.isEmpty()) this.start = start;
		startTimes.add(start);

	}

	@Override
	public void addFinish(String finish) {
		if(finishTimes.isEmpty()) this.finish = finish;
		finishTimes.add(finish);
	}
	
	public void addTime(Time t) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String genResult() {
		StringBuilder sb = new StringBuilder();
		sb.append(totalT()).append("; ");
		sb.append(startT()).append("; ");
		sb.append(finishT()).append("; ");
		sb.append(errors());
		String out = sb.toString();
		if(out.charAt(out.length() - 2) != ';') return out;
		return out.substring(0, out.length() - 2);
	}
	private String startT() {
		if(startTimes.size() > 0) return start;
		else return "Start?";
	}
	
	private String finishT() {
		if(finishTimes.size() > 0) return finish;
		else return "Slut?";
		
	}
	private String totalT() {
		if(finishTimes.size() > 0 && startTimes.size() > 0) {
			return TotalTimeCalculator.computeDifference(start, finish);
		}
		return "--.--.--";
	}
	private String errors() {
		StringBuilder sb = new StringBuilder();
		if(startTimes.size() > 1) {
			sb.append("Flera starttider?");
			boolean skipped = false;
			for(String s: startTimes) {
				if(!skipped) skipped = true;
				else sb.append(" " + s);
			}
			sb.append("; ");
		}
		if(finishTimes.size() > 1) {
			sb.append("Flera måltider?");
			boolean skipped = false;
			for(String s: finishTimes) {
				if(!skipped) skipped = true;
				else sb.append(" " + s);
			}
			sb.append("; ");
		}
		if(!TotalTimeCalculator.possibleTotalTime(start, finish)) {
			sb.append("Omöjlig Totaltid?; ");
		}
		return sb.toString();
	}

	@Override
	public String getStart() {
		return start;
	}

	@Override
	public String getFinish() {
		return finish;
	}
	
	@Override
	public int getLaps() {
		return Math.min(1, Math.min(startTimes.size(), finishTimes.size()));
	}

}
