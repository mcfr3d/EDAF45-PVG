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
	  
	  String start = startTimes.isEmpty() ? "Start?" : (startTimes.size() == 1 ? startTimes.getFirst() : "Flera Starttider?");
	  String finish = finishTimes.isEmpty() ? "Slut?" : (finishTimes.size() == 1 ? finishTimes.getFirst() : "Flera Sluttider?");
	  String delta = TotalTimeCalculator.computeDifference(start, finish);
	  
	  return "" + startNumber + "; " + name + "; " + delta + "; " + start + "; " + finish;
  }
  
  public String getErrors() {
	  
	  StringBuilder sb = new StringBuilder();
	  
	  //append if 0 starts, 0 finish, >1 start, >1 finish
	  return sb.toString();
  }
  
}
