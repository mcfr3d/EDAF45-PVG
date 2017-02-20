package resultMerge;

import java.util.LinkedList;
import java.util.TreeSet;

import util.TotalTimeCalculator;

public class MultiLapRace implements RaceType {
	private static int maxLaps = 0;
	// public static String raceTime = ""; should be used for errorhandling.
	private LinkedList<Time> startTimes = new LinkedList<>();
	private LinkedList<Time> finishTimes = new LinkedList<>();
	private LinkedList<Time> meanTimes = new LinkedList<>();
	private static int stipulatedTime = 60 * 60;

	@Override
	public void addStart(String start) {
		Time t = new Time(start, true);
		addTime(t);
	}

	@Override
	public void addFinish(String finish) {
		Time t = new Time(finish);
		addTime(t);
	}

	/**
	 * Adds a time object to the correct list If a starttime is added after
	 * finish times it moves time objects from meanTime to finishTimes if
	 * stipulated Time is exceeded.
	 */
	public void addTime(Time t) {
		if (t.isStart()) {
			if (startTimes.isEmpty()) {
				for (Time m : meanTimes) {
					if (Time.diff(t, m).getTimeAsInt() > stipulatedTime) {
						finishTimes.add(m);
					}
				}
				for (Time f : finishTimes) {
					meanTimes.remove(f);
				}
			}
			startTimes.add(t);
		} else {
			if (!startTimes.isEmpty() && Time.diff(startTimes.getFirst(), t).getTimeAsInt() > stipulatedTime) {
				finishTimes.add(t);
			} else {
				meanTimes.add(t);
			}
		}
	}

	private String totalTime() {
		if (startTimes.isEmpty() || finishTimes.isEmpty())
			return "--.--.--";
		String tmp = Time.diffAsString(startTimes.getFirst(), finishTimes.getFirst());
		return tmp;
	}

	/**
	 * Generates the results in the order: nbrOfLaps; totalTime; LapTimes
	 * 
	 * @return A string in the format nbrOfLaps; totalTime; LapTimes
	 */
	@Override
	public String genResult() {
		StringBuilder sb = new StringBuilder();
		sb.append(getLaps()).append("; ");
		sb.append(totalTime()).append("; ");
		for (int i = 0; i < maxLaps; i++) {
			if (getLapTime(i) != null) {
				sb.append(getLapTime(i));
			}
			sb.append("; ");
		}
		String out = sb.toString();
		return out.substring(0, out.length() - 2);

	}

	/**
	 * Generates the result + lapTimes; finishTime; errors.
	 * 
	 * @return the result + lapTimes; finishTime; errors.
	 */
	public String genResultWithErrors() {
		StringBuilder sb = new StringBuilder();
		sb.append(genResult()).append("; ");
		sb.append(startT()).append("; ");
		for (int i = 0; i < maxLaps - 1; i++) {
			sb.append(getMeanTime(i)).append("; ");
		}
		sb.append(finishT()).append("; ");
		sb.append(errors());
		String out = sb.toString();
		return (out.length() == 0) ? "" : out.substring(0, out.length() - 2);
	}

	private String getMeanTime(int index) {
		if (index >= meanTimes.size())
			return "";
		return meanTimes.get(index).toString();
	}

	private Time getLapTime(int index) {
		int laps = getLaps();
		if (index >= laps && finishTimes.size() != 0) {
			return null;
		} else if (index == laps - 1) {
			if (finishTimes.isEmpty()) {
				return null;
			} else if (laps != 1) {
				return Time.diff(meanTimes.get(index - 1), finishTimes.getFirst());
			} else {
				return Time.diff(startTimes.getFirst(), finishTimes.getFirst());
			}
		} else if (startTimes.size() > 0 && meanTimes.size() > 0) {
			if(index == 0) {
				return Time.diff(startTimes.getFirst(), meanTimes.getFirst());
			} else {
				return Time.diff(meanTimes.get(index - 1), meanTimes.get(index));
			}
		} else {
			return null;
		}
	}

	/**
	 * returns the errors in the order: impossibleLapTime, multipleStartTimes
	 * 
	 * @return the errors impossibleLapTime + "; " + multipleStartTimes
	 */
	private String errors() {
		StringBuilder sb = new StringBuilder();
		sb.append(impossibleLapTime());
		sb.append(multipleStartTimes());
		return sb.toString();
	}

	/**
	 * Returns "Omöjlig vartid?" if at least one lap time is less than 15
	 * minutes.
	 * 
	 * @return "Omöjlig vartid?" if at least one lap time is less than 15
	 *         minutes else an empty string
	 */
	private String impossibleLapTime() {
		for (int i = 0; i < getLaps(); i++) {
			Time t = getLapTime(i);
			if (t != null && t.getTimeAsInt() < 15 * 60) {
				return "Omöjlig varvtid?; ";
			}
		}
		return "";
	}

	/**
	 * Returns "Flera starttider?" followed by the extra start times if there
	 * are multiple start times.
	 * 
	 * @return "Flera starttider?" with the extra start times with a " " as
	 *         seperator
	 */
	private String multipleStartTimes() {
		StringBuilder sb = new StringBuilder();
		if (startTimes.size() > 1) {
			sb.append("Flera starttider? ");
			for (int i = 1; i < startTimes.size(); i++) {
				sb.append(startTimes.get(i) + " ");
			}
			sb.append("; ");
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

	private String startT() {
		return startTimes.isEmpty() ? "Start?" : getStart();
	}

	private String finishT() {
		return finishTimes.isEmpty() ? "Slut?" : getFinish();
	}

	// Should only be used during testing
	public void resetStatic() {
		maxLaps = 0;
	}

	public int getLaps() {
		if (startTimes.isEmpty())
			return 0;
		if (finishTimes.isEmpty())
			return meanTimes.size();
		else
			return meanTimes.size() + 1;
	}

	public static void setMaxLaps(int laps) {
		maxLaps = laps;
	}

	/**
	 * Sorts first on laps in descending order, then time in ascending order.
	 */
	@Override
	public int compareTo(RaceType o) {
		MultiLapRace other = (MultiLapRace) o;
		if (this.genResultWithErrors().contains("?")) {
			if (other.genResultWithErrors().contains("?")) {
				return 0;
			} else {
				return 1;
			}
		} else if (other.genResultWithErrors().contains("?")) {
			return -1;
		}
		int diff = getLaps() - o.getLaps();
		if (diff != 0)
			return -diff; // sort on descending order of laps
		return totalTime().compareTo(other.totalTime());
	}

	public static void setStipulatedTime(Time time) {
		stipulatedTime = time.getTimeAsInt();
	}

	/*
	 * True if: one start time atleast one finish time all laps longer than
	 * minimum laptime
	 * 
	 * private boolean isValid() { if (startTimes.size() != 1 ||
	 * finishTimes.size() < 1) return false; String prev =
	 * startTimes.getFirst(); for (String finish : finishTimes) { if
	 * (!TotalTimeCalculator.possibleTotalTime(prev, finish)) return false; prev
	 * = finish; } return true; }
	 */

}