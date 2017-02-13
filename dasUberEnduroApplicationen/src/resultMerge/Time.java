package resultMerge;

public class Time {
	private int time;
	private boolean isStart;
	private int etapp;

	public Time(int time, boolean start) throws Exception {
		this(time, start, -1);
	}

	public Time(int time, boolean start, int etapp) throws Exception {
		if(time < 0) throw new Exception("Negative Time");
		this.time = time;
		this.isStart = start;
		this.etapp = etapp;
	}
	public Time(String t, boolean start) throws Exception{
		this(0, start, -1);
		time = toSeconds(t);
	}

	public Time(String t, boolean start, int etapp) throws Exception {
		this(0, start, etapp);
		time = toSeconds(t);
	}
	
	public static String diff(Time t1, Time t2) {
		try{
			return new Time(t2.time-t1.time, false).toString();
		}catch(Exception e) {
			return "--.--.--";
		}
	}
	
	public String toString() {
		int[] hhmmss = new int[3];
		hhmmss[2] = time % 60;
		hhmmss[1] = (time / 60) % 60;
		hhmmss[0] = (time / (60 * 60)) % 60;
		return String.format("%02d.%02d.%02d", hhmmss[0], hhmmss[1], hhmmss[2]);
	}
	
	private int toSeconds(String time) throws Exception {
		String[] s = time.split("\\.");
		if (s.length != 3)
			throw new Exception("Syntax error!");

		int t = 0;
		for (int i = 0; i < 3; ++i)
			t = t * 60 + Integer.parseInt(s[i]);

		return t;
	}
}
