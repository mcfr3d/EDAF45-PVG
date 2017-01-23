package resultMerge;

public class TotalTimeCalculator {

	private static int toSeconds(String time) throws Exception {

		String[] s = time.split("\\.");

		if (s.length != 3)
			throw new Exception("Syntax error !");

		int t = (Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1])) * 60 + Integer.parseInt(s[2]);

		return t;
	}

	public static String computeDifference(String start, String finish) {

		try {

			int s = toSeconds(start);
			int f = toSeconds(finish);

			int d = f - s;

			if (d < 0)
				throw new Exception("Negative !");

			int seconds = d % 60;
			int minutes = (d / 60) % 60;
			int hours = (d / (60 * 60)) % 60;

			return String.format("%02d.%02d.%02d", hours, minutes, seconds);

		} catch (Exception e) {

			return "--.--.--";
		}

	}

}
