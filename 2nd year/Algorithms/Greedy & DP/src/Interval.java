public class Interval implements Comparable<Interval> {
	/*
	Clasa de intervale
	*/
	long startingPoint = 0;
	long endingPoint = 0;
	
	public Interval(long startingPoint, long endingPoint) {
		this.startingPoint = startingPoint;
		this.endingPoint = endingPoint;
	}

	@Override
	public int compareTo(Interval interval) {
		long compareStart = ((Interval) interval).startingPoint;
		return (int)(this.startingPoint - compareStart);
	}
}
