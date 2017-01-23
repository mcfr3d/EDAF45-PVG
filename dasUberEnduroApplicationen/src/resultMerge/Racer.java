package resultMerge;

import java.util.LinkedList;

public class Racer {
  private LinkedList<String> starttimes, finishtimes;
  private String name;
  private int startnumber;
  
  public Racer(int startnumber) {
    this.startnumber = startnumber;
  }
  
  public void addStart(String starttime) {
    starttimes.add(starttime);
  }
  
  public void addFinish(String finishtime) {
    finishtimes.add(finishtime);
  }
  public String toString() {
	  return ""+startnumber + "; --.--.--; " + starttimes.getFirst() + "; " + finishtimes.getFirst();
  }
  
  public String getErrors() {
	  StringBuilder sb = new StringBuilder();
	 
	  
	  //append if 0 starts, 0 finish, >1 start, >1 finish
	  return sb.toString();
  }
  
}
