package resultMerge;

import java.util.LinkedList;

public class Racer {

  private LinkedList<String> startTimes, finishTimes;
  private String name;
  private int startNumber;
  
  public Racer(int startNumber) {
    this.startNumber = startNumber;
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
	  
	  String start = startTimes.isEmpty() ? "Start?" : (startTimes.size() == 1 ? startTimes.getFirst() : "Flera Startider?");
	  String finish = finishTimes.isEmpty() ? "Slut?" : (finishTimes.size() == 1 ? finishTimes.getFirst() : "Flera Sluttider?");
	  
	  return "" + startNumber + "; " + name + "; --.--.--; " + start + "; " + finish;
  }
  
  public String getErrors() {
	  
	  StringBuilder sb = new StringBuilder();
	  
	  //append if 0 starts, 0 finish, >1 start, >1 finish
	  return sb.toString();
  }
  
}
