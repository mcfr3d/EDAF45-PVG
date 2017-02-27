package resultMerge;

import java.util.LinkedList;

import util.TotalTimeCalculator;

public class OneLapRace implements RaceType {
	private LinkedList<Time> startTimes = new LinkedList<>();
	private LinkedList<Time> finishTimes = new LinkedList<>();
	
	public void addTime(Time t) {
		if(t.isStart()) startTimes.add(t);
		else finishTimes.add(t);
	}
	
	@Override
	public String genResult() {
		StringBuilder sb = new StringBuilder();
		sb.append(totalTime()).append("; ");
		sb.append(startT()).append("; ");
		sb.append(finishT());
		return sb.toString();
		
	}
	
	public String genResultWithErrors(Database db) {
		StringBuilder sb = new StringBuilder();
		sb.append(genResult()).append("; ");
		sb.append(errors());
		String out = sb.toString();
		if (out.charAt(out.length() - 2) != ';')
			return out;
		return out.substring(0, out.length() - 2);
		
	}

	private String startT() {
		if (startTimes.size() > 0)
			return getStart();
		else
			return "Start?";
	}

	private String finishT() {
		if (finishTimes.size() > 0)
			return getFinish();
		else
			return "Slut?";

	}

	private String totalTime() {
		if (finishTimes.size() > 0 && startTimes.size() > 0) {
			return TotalTimeCalculator.computeDifference(getStart(), getFinish());
		}
		return "--.--.--";
	}

	private String errors() {
		StringBuilder sb = new StringBuilder();
		if (startTimes.size() > 1) {
			sb.append("Flera starttider?");
			boolean skipped = false;
			for (Time t : startTimes) {
				if (!skipped)
					skipped = true;
				else
					sb.append(" " + t);
			}
			sb.append("; ");
		}
		if (finishTimes.size() > 1) {
			sb.append("Flera måltider?");
			boolean skipped = false;
			for (Time t : finishTimes) {
				if (!skipped)
					skipped = true;
				else
					sb.append(" " + t);
			}
			sb.append("; ");
		}
		if(!startTimes.isEmpty() && !finishTimes.isEmpty()){
			if(!TotalTimeCalculator.possibleTotalTime(getStart(), getFinish())) {
				sb.append("Omöjlig Totaltid?; ");
			}
		}
		return sb.toString();
	}

	@Override
	public String getStart() {
		return startTimes.isEmpty() ? "" : startTimes.getFirst().toString();
	}

	@Override
	public String getFinish() {
		return finishTimes.isEmpty() ? "" : finishTimes.getFirst().toString();
	}

	@Override
	public int getLaps() {
		return Math.min(1, Math.min(startTimes.size(), finishTimes.size()));
	}

	@Override
	public int compareTo(RaceType o) {
		OneLapRace other = (OneLapRace) o;
		boolean thisValid = haveOneStartAndOneFinish();
		boolean otherValid = other.haveOneStartAndOneFinish();
		if (thisValid && otherValid) {
			return totalTime().compareTo(other.totalTime());
		}
		return thisValid ? -1 : (otherValid ? 1 : 0);
	}

	/*
	 * Checks if there only exists one start time and one finish time.
	 */
	private boolean haveOneStartAndOneFinish() {
		return startTimes.size() == 1 && finishTimes.size() == 1;
	}

}
