package resultMerge;

public class TotalTimeCalculator {

	private static int toSeconds(String time) throws Exception {

		String[] s = time.split("\\.");

		if (s.length != 3)
			throw new Exception("Syntax error !");

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
				throw new Exception("Negative !");

			int seconds = diffSeconds % 60;
			int minutes = (diffSeconds / 60) % 60;
			int hours = (diffSeconds / (60 * 60)) % 60;

			return String.format("%02d.%02d.%02d", hours, minutes, seconds);

		} catch (Exception e) {

			return "--.--.--";
		}

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
}
