package resultMerge;

import java.util.LinkedList;

import util.TotalTimeCalculator;

public class OneLapRace implements RaceType {

	private LinkedList<String> startTimes = new LinkedList<>();
	private LinkedList<String> finishTimes = new LinkedList<>();

	@Override
	public void addStart(String start) {
		startTimes.add(start);
	}

	@Override
	public void addFinish(String finish) {
		finishTimes.add(finish);
	}

	@Override
	public String genResult() {
		StringBuilder sb = new StringBuilder();
		sb.append(totalT()).append("; ");
		sb.append(startT()).append("; ");
		sb.append(finishT()).append("; ");
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

	private String totalT() {
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
			for (String s : startTimes) {
				if (!skipped)
					skipped = true;
				else
					sb.append(" " + s);
			}
			sb.append("; ");
		}
		if (finishTimes.size() > 1) {
			sb.append("Flera måltider?");
			boolean skipped = false;
			for (String s : finishTimes) {
				if (!skipped)
					skipped = true;
				else
					sb.append(" " + s);
			}
			sb.append("; ");
		}
		if (!TotalTimeCalculator.possibleTotalTime(getStart(), getFinish())) {
			sb.append("Omöjlig Totaltid?; ");
		}
		return sb.toString();
	}

	@Override
	public String getStart() {
		return startTimes.isEmpty() ? "" : startTimes.getFirst();
	}

	@Override
	public String getFinish() {
		return finishTimes.isEmpty() ? "" : finishTimes.getFirst();
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
			String thisDelta = TotalTimeCalculator.computeDifference(getStart(), getFinish());
			String otherDelta = TotalTimeCalculator.computeDifference(other.getStart(), other.getFinish());
			return thisDelta.compareTo(otherDelta);
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
