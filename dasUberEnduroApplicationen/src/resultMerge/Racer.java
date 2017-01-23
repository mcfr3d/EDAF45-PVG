package resultMerge;

public class Racer {
	
  private String startTime, finishTime, name;
  private int startNumber;
  
  public Racer(int startNumber) {
    this.startNumber = startNumber;
  }
  
  public void addStart(String startTime) {
    this.startTime = startTime;
  }
  
  public void addFinish(String finishTime) {
    this.finishTime = finishTime;
  }
  
  public String getStart() {
	  return startTime;
  }

  public String getFinish() {
	  return finishTime;
  }
  
}
