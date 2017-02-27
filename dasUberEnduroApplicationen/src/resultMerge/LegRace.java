package resultMerge;

import java.util.LinkedList;

public class LegRace implements RaceType {
	private class Leg {
		LinkedList<Time> start = new LinkedList<>();
		LinkedList<Time> finish = new LinkedList<>();

		public void addStart(Time t) {
			start.add(t);
		}

		public void addFinish(Time t) {
			finish.add(t);
		}

		public String getStart() {
			return start.isEmpty() ? "Start?" : start.getFirst().toString();
		}

		public String getFinish() {
			return finish.isEmpty() ? "Slut?" : finish.getFirst().toString();
		}

		public Time getLegTime() {
			if (!(start.isEmpty() || finish.isEmpty())) {
				try {
					return Time.diff(finish.getFirst(), start.getFirst());
				} catch (Exception e) {
				}
			}
			return null;
		}
	}

	private Leg[] legs;

	public LegRace(int nbrLegs) {
		legs = new Leg[nbrLegs];
		for (int i = 0; i < nbrLegs; i++)
			legs[i] = new Leg();
	}

	@Override
	public void addStart(String start) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFinish(String finish) {
		// TODO Auto-generated method stub

	}

	@Override
	public String genResult() {
		StringBuilder sb = new StringBuilder();
		sb.append(getLaps()).append("; ");
		Time totalTime = totalTime();
		String totTimeStr = totalTime.getTimeAsInt() == 0 ? "--.--.--" : totalTime.toString();
		sb.append(totTimeStr.toString()).append("; ");

		for (int i = 0; i < legs.length; i++) {
			Time legTime = legs[i].getLegTime();
			if (legTime != null) {
				sb.append(legTime.toString());
			}
			sb.append("; ");
		}
		String out = sb.toString();
		return out.substring(0, out.length() - 2);
	}

	@Override
	public String genResultWithErrors(Database db) {
		StringBuilder sb = new StringBuilder();
		sb.append(genResult()).append("; ");
		String errors = "";
		int i = 0;
		for (Leg l : legs) { // adds start and finish for each leg
			sb.append(l.getStart()).append("; ");
			sb.append(l.getFinish()).append("; ");
			if (db != null) {
				try {
					Time minimumTime = db.getLegInfo().getMinimumTime(i);
					Time diff = Time.diff(new Time(l.getFinish()), new Time(l.getStart()));
					if (diff.getTimeAsInt() < minimumTime.getTimeAsInt()) {
						errors += " etapp " + (i + 1) + " omöjlig tid";
					}
				} catch (Exception ee) {
				}
			}
		}
		// TODO: add errors (mutiple start/finish times and so on.)
		errors = errors.trim();
		if (!errors.equals(""))
			sb.append(errors + "; ");
		String out = sb.toString();
		return out.substring(0, out.length() - 2);

	}

	@Override
	public int getLaps() {
		int counter = 0;
		for (int i = 0; i < legs.length; i++) {
			Time legTime = legs[i].getLegTime();
			if (legTime != null) {
				counter++;
			}
		}
		return counter;
	}

	private Time totalTime() {
		Time totaltime = new Time(0);
		for (int i = 0; i < legs.length; i++) {
			Time legTime = legs[i].getLegTime();
			if (legTime != null) {
				totaltime = totaltime.add(legTime);
			}
		}
		return totaltime;
	}

	@Override
	public String getStart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFinish() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTime(Time t) throws ArrayIndexOutOfBoundsException {
		int legNbr = t.getLegNbr() - 1;

		if (t.isStart())
			legs[legNbr].addStart(t);
		else
			legs[legNbr].addFinish(t);
	}

	@Override
	public int compareTo(RaceType o) {
		LegRace lr = (LegRace) o;
		int legDiff = lr.getLaps() - getLaps();
		if (legDiff != 0)
			return legDiff;
		int timediff = totalTime().getTimeAsInt() - lr.totalTime().getTimeAsInt();
		return timediff;
	}

}
