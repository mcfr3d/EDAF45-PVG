package resultMerge;

import java.time.LocalTime;

public class TotalTimeCalculator {

	private static int toSeconds(String time) throws Exception {

		String[] s = time.split("\\.");

		if (s.length != 3)
			throw new Exception("Syntax error!");

		int t = 0;

		for (int i = 0; i < 3; ++i)
			t = t * 60 + Integer.parseInt(s[i]);

		return t;
	}

	public static String computeDifference(String start, String finish) {

		try {

			int startSeconds = toSeconds(start);
			int finishSeconds = toSeconds(finish);

			int diffSeconds = finishSeconds - startSeconds;

			if (diffSeconds < 0)
				throw new Exception("Negative!");

			int[] time = secondsToTime(diffSeconds);
			return String.format("%02d.%02d.%02d", time[0], time[1], time[2]);

		} catch (Exception e) {

			return "--.--.--";
		}

	}

	private static int[] secondsToTime(int seconds) {
		int[] time = new int[3];
		time[2] = seconds % 60;
		time[1] = (seconds / 60) % 60;
		time[0] = (seconds / (60 * 60)) % 60;
		return time;
	}

	public static String timeFormater(String input) throws Exception {
		int tempSeconds = toSeconds(input);
		int[] time = secondsToTime(tempSeconds);
		return String.format("%02d.%02d.%02d", time[0], time[1], time [2]);
	}

	public static boolean possibleTotalTime(String start, String finish) {

		try {
			int s = toSeconds(start);
			int f = toSeconds(finish);
			int d = f - s;
			if (d < 15 * 60) {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static String getCurrentTime() {
		LocalTime currentTime = LocalTime.now();
		String hour = String.format("%02d", currentTime.getHour());
		String minute = String.format("%02d", currentTime.getMinute());
		String second = String.format("%02d", currentTime.getSecond());
		return hour + "." + minute + "." + second;
	}
}
