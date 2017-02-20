package resultMerge;

import java.util.LinkedList;

public class EtappRace implements RaceType {
	private class Etapp {
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

		public Time getEtappTime() {
			if (!(start.isEmpty() || finish.isEmpty())) {
				try {
					return Time.diff(finish.getFirst(), start.getFirst());
				} catch (Exception e) {
				}
			}
			return null;
		}
	}

	private Etapp[] etapper;

	public EtappRace(int antalEtapper) {
		etapper = new Etapp[antalEtapper];
		for (int i = 0; i < antalEtapper; i++)
			etapper[i] = new Etapp();
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
		String totTimeStr = totalTime().getTimeAsInt() == 0 ? "--.--.--" : totalTime().toString();
		sb.append(totTimeStr.toString()).append("; ");
		
		for (int i = 0; i < etapper.length; i++) {
			Time etappTime = etapper[i].getEtappTime();
			if (etappTime != null) {
				sb.append(etappTime.toString());
			}
			sb.append("; ");
		}
		String out = sb.toString();
		return out.substring(0, out.length() - 2);
	}

	@Override
	public String genResultWithErrors() {
		StringBuilder sb = new StringBuilder();
		sb.append(genResult()).append("; ");
		String comments = "";
		int i = 1;
		for (Etapp e : etapper) { // adds start and finish for each etapp
			sb.append(e.getStart()).append("; ");
			sb.append(e.getFinish()).append("; ");
			if (Time.diff(new Time(e.getFinish()), new Time(e.getStart())).getTimeAsInt() < new Time("00.15.00").getTimeAsInt()) {
				comments += " etapp " + i + " omöjlig tid";
			}
		}
		//TODO: add errors (mutiple start/finish times and so on.)
		sb.append(comments + "; ");
		String out = sb.toString();
		return out.substring(0, out.length() - 2);
		
	}
	
	@Override
	public int getLaps() {
		int counter = 0;
		for (int i = 0; i < etapper.length; i++) {
			Time etappTime = etapper[i].getEtappTime();
			if (etappTime != null) {
				counter++;
			}
		}
		return counter;
	}

	private Time totalTime() {
		Time totaltime = new Time(0);
		for (int i = 0; i < etapper.length; i++) {
			Time etappTime = etapper[i].getEtappTime();
			if (etappTime != null) {
				totaltime = totaltime.add(etappTime);
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
		int etappNbr = t.getEtappNbr() - 1;

		if (t.isStart())
			etapper[etappNbr].addStart(t);
		else
			etapper[etappNbr].addFinish(t);
	}

	@Override
	public int compareTo(RaceType o) {
		EtappRace er = (EtappRace) o;
		int etappdiff = er.getLaps() - getLaps();
		if(etappdiff != 0) return etappdiff;
		int timediff = totalTime().getTimeAsInt() - er.totalTime().getTimeAsInt();
		return timediff;
	}

	

}
