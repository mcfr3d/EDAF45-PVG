package resultMerge;

import java.util.Collections;
import java.util.Comparator;
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

	/**
	 * Adds a time object to the correct list If a starttime is added after
	 * finish times it moves time objects from meanTime to finishTimes if
	 * stipulated Time is exceeded.
	 */
	public void addTime(Time t) {
		if (t.isStart()) {
			if (startTimes.isEmpty()) {
				for (Time m : meanTimes) {
					if (Time.diff(m, t).getTimeAsInt() > stipulatedTime) {
						finishTimes.add(m);
					}
				}
				for (Time f : finishTimes) {
					meanTimes.remove(f);
				}
			}
			startTimes.add(t);
		} else {
			if (!startTimes.isEmpty() && Time.diff(t, startTimes.getFirst()).getTimeAsInt() > stipulatedTime) {
				finishTimes.add(t);
			} else {
				meanTimes.add(t);
			}
		}
	}

	private String totalTime() {
		try { 
			return Time.diff(finishTimes.getFirst(), startTimes.getFirst()).toString();
		} catch(Exception e) {
			return "--.--.--";
		}
	}
	
	public Time getTotalTime() {
		String time = totalTime();
		try {
			return new Time(time);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Generates the results in the order: nbrOfLaps; totalTime; LapTimes
	 * 
	 * @return A string in the format nbrOfLaps; totalTime; LapTimes
	 */
	@Override
	public String genResult() {
		Collections.sort(meanTimes, new Comparator<Time>() {

			@Override
			public int compare(Time a, Time b) {
				return a.toString().compareTo(b.toString());
			}
		});
		StringBuilder sb = new StringBuilder();
		sb.append(realLaps()).append("; ");
		sb.append(totalTime()).append("; ");
		LinkedList<Time> times = new LinkedList<>();
		if(!startTimes.isEmpty()) times.add(startTimes.getFirst());
		for(Time t: meanTimes) times.addLast(t);
		if(!finishTimes.isEmpty()) times.addLast(finishTimes.getFirst());
		int i = 0;
		if(startTimes.isEmpty()) {
			i++;
			sb.append("; ");
		}
		for (; i < maxLaps; i++) {
			if (times.size() > 1) {
				Time t = times.removeFirst();
				sb.append(Time.diff(times.getFirst(), t).toString());
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
	public String genResultWithErrors(Database db) {
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
		int laps = realLaps();
		if(laps <= index) return null;
		if(index == 0) {
			if(startTimes.isEmpty()) return null;
			if(meanTimes.isEmpty()) return Time.diff(finishTimes.getFirst(), startTimes.getFirst());
			else return Time.diff(meanTimes.getFirst(), startTimes.getFirst());
		} else if(laps < index -1) {
			return Time.diff(meanTimes.get(index), meanTimes.get(index-1));
		} else {
			if(finishTimes.isEmpty()) {
				return Time.diff(meanTimes.get(index), meanTimes.get(index-1));
			} else {
				return Time.diff(finishTimes.getFirst(), meanTimes.getLast());
			}
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
		for (int i = 0; i < realLaps(); i++) {
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

	private int realLaps() {
		int laps = meanTimes.size();
		if(!finishTimes.isEmpty()) laps++;
		if(startTimes.isEmpty()) laps--;
		return laps;
	}
	
	//should only be used for header generation
	public int getLaps() {
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
		if (this.genResultWithErrors(null).contains("?")) {
			if (other.genResultWithErrors(null).contains("?")) {
				return 0;
			} else {
				return 1;
			}
		} else if (other.genResultWithErrors(null).contains("?")) {
			return -1;
		}
		int diff = realLaps() - other.realLaps();
		if (diff != 0)
			return -diff; // sort on descending order of laps
		return totalTime().compareTo(other.totalTime());
	}

	public static void setStipulatedTime(Time time) {
		stipulatedTime = time.getTimeAsInt();
	}

}
