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
	  
	  String start = getStartTimes();
	  String finish = getFinishTimes();
	  String delta = TotalTimeCalculator.computeDifference(start, finish);

	  return "" + startNumber + "; " + name + "; " + delta + "; " + start + "; " + finish;
  }
  
  private String getFinishTimes() {
	StringBuilder sb = new StringBuilder();
		if (finishTimes.size() == 0) {
				sb.append("Slut?");
		} else if (finishTimes.size() == 1) {
				sb.append(finishTimes.getFirst());
		} else {
			sb.append("Flera måltider? ");
			for (String time : finishTimes) {
				sb.append(time + "; ");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.deleteCharAt(sb.length() - 1);
	}	
	return sb.toString();
}
  
  private String getStartTimes() {
		StringBuilder sb = new StringBuilder();
			if (startTimes.size() == 0) {
					sb.append("Start?");
			} else if (startTimes.size() == 1) {
					sb.append(startTimes.getFirst());
			} else {
				sb.append("Flera starttider? ");
				for (String time : startTimes) {
					sb.append(time + "; ");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);
		}	
		return sb.toString();
	}

public String getErrors() {
	  
	  StringBuilder sb = new StringBuilder();
	  
	  //append if 0 starts, 0 finish, >1 start, >1 finish
	  return sb.toString();
  }
  
}
