package resultMerge;


public class Time {
	private int time;
	private boolean isStart;
	private int etapp;

	public Time(int time) {
		this(time, false, -1);
	}
	
	public Time(int time, boolean start) {
		this(time, start, -1);
	}

	public Time(int time, boolean start, int etapp) {
		if(time < 0) throw new IllegalArgumentException("Negative Time");
		this.time = time;
		this.isStart = start;
		this.etapp = etapp;
	}
	
	public Time(String t) {
		this(0, false, -1);
		time = toSeconds(t);
	}
	
	public Time(String t, boolean start) {
		this(0, start, -1);
		time = toSeconds(t);
	}

	public Time(String t, boolean start, int etapp) {
		this(0, start, etapp);
		time = toSeconds(t);
	}
	
	public static String diffAsString(Time start, Time finish) {
		try {
			return diff(start, finish).toString();
		} catch (Exception e) {
			return "--.--.--";
		}
	}
	
	public static Time diff(Time start, Time finish) {		
		return new Time(finish.time - start.time);
	}
	
	public String toString() {
		int[] hhmmss = new int[3];
		hhmmss[2] = time % 60;
		hhmmss[1] = (time / 60) % 60;
		hhmmss[0] = (time / (60 * 60)) % 60;
		return String.format("%02d.%02d.%02d", hhmmss[0], hhmmss[1], hhmmss[2]);
	}
	
	private int toSeconds(String time) {
		String[] s = time.split("\\.");
		if (s.length != 3)
			throw new IllegalArgumentException("Time is not on format hh.mm.ss");

		int t = 0;
		for (int i = 0; i < 3; ++i)
			t = t * 60 + Integer.parseInt(s[i]);

		return t;
	}
	
	public boolean isStart() {
		return isStart;
	}
	
	public int getEtappNbr() {
		return etapp;
	}

	public int getTimeAsInt() {
		return time;
	}
	
	public Time add(Time etappTime)  {
		return new Time(this.time + etappTime.time);
	}
}
