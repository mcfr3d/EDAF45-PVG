package resultMerge;

public class Racer {
  private String starttime, finishtime, name;
  private int startnumber;
  public Racer(int startnumber) {
    this.startnumber = startnumber;
  }
  public void addStart(String starttime) {
    this.starttime = starttime;
  }
  public void addFinish(String finishtime) {
    this.finishtime = finishtime;
  }
}
