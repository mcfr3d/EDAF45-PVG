package resultMerge;

import java.util.LinkedList;

public class Racer {

	private LinkedList<String> startTimes, finishTimes;
	private String name;
	private int startNumber;

	public Racer(int startNumber) {
		this.startNumber = startNumber;

		startTimes = new LinkedList<>();
		finishTimes = new LinkedList<>();

	}

	public void setName(String name) {
		this.name = name;
	}

	public void addStart(String startTime) {
		startTimes.add(startTime);
	}

	public void addFinish(String finishTime) {
		finishTimes.add(finishTime);
	}

	public String toString() {

		String start = startTimes.isEmpty() ? "Start?" : startTimes.getFirst();
		String finish = finishTimes.isEmpty() ? "Slut?" : finishTimes.getFirst();
		String delta = TotalTimeCalculator.computeDifference(start, finish);
		String moreStartTimes = "";
		String moreFinishTimes = "";
		if (startTimes.size() > 1) {
			moreStartTimes = multipleStartTimes();
		}
		if (finishTimes.size() > 1) {
			moreFinishTimes = multipleFinishTimes();
		}

		return "" + startNumber + "; " + name + "; " + delta + "; " + start + "; " + finish + moreStartTimes
				+ moreFinishTimes;
	}

	private String multipleFinishTimes() {
		StringBuilder sb = new StringBuilder();
		sb.append("; Flera måltider? ");
		for (int i = 1; i < finishTimes.size(); i++) {
			sb.append(finishTimes.get(i) + " ");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private String multipleStartTimes(){
		StringBuilder sb = new StringBuilder();
		sb.append("; Flera starttider? ");
		for (int i = 1; i < startTimes.size(); i++){
			sb.append(startTimes.get(i) + " ");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	public String getErrors() {

		StringBuilder sb = new StringBuilder();

		// append if 0 starts, 0 finish, >1 start, >1 finish
		return sb.toString();
	}

}
