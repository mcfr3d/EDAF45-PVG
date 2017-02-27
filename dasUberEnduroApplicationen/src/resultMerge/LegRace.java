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
		
		public String getStartWithErrors(StringBuilder errors, int i) {
			if (start.size() > 1) {
				errors.append("Flera starttider Etapp" + i);
				for (int j = 1; j < start.size(); j++) {
					errors.append(" " + start.get(j));
				}
				errors.append("; ");
			}
			return getStart(); 
		}
		
		public String getFinishWithErrors(StringBuilder errors, int i) {
			if (finish.size() > 1) {
				errors.append("Flera måltider Etapp" + i);
				for (int j = 1; j < finish.size(); j++) {
					errors.append(" " + finish.get(j));
				}
				errors.append("; ");
			}
			return getFinish(); 
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
		StringBuilder errors = new StringBuilder();
		sb.append(genResult()).append("; ");
		int i = 1;
		
		for (Leg l : legs) { // adds start and finish for each leg
			sb.append(l.getStartWithErrors(errors, i)).append("; ");
			sb.append(l.getFinishWithErrors(errors, i)).append("; ");
			if (db != null) {
				try {
					Time minimumTime = db.getLegInfo().getMinimumTime(i - 1);
					Time diff = Time.diff(new Time(l.getFinish()), new Time(l.getStart()));

					if (diff.getTimeAsInt() < minimumTime.getTimeAsInt()) {
						errors.append("Omöjlig tid Etapp" + i).append("; ");
					}
				} catch (Exception ee) {
				}
			}
			i++;
		}
		sb.append(errors);
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
	
	public Time getTotalTime() {
		Time time = totalTime();
		if(time.getTimeAsInt() == 0) return null;
		return time;
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
