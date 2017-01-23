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
	  
	  String start = startTimes.isEmpty() ? "Start?" : (startTimes.size() == 1 ? startTimes.getFirst() : "Flera starttider?");
	  String finish = finishTimes();
	  
	  return "" + startNumber + "; " + name + "; " + TotalTimeCalculator.computeDifference(start, finish) + "; " + start + "; " + finish;
  }
  
  private String finishTimes() {
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

public String getErrors() {
	  
	  StringBuilder sb = new StringBuilder();
	  
	  //append if 0 starts, 0 finish, >1 start, >1 finish
	  return sb.toString();
  }
  
}
