import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Points {
	public static void main(String[] args) {
		String inputFileName = "points.in";
		String outputFileName = "points.out";
		try {
			MyReader myReader = new MyReader(inputFileName);
			long N = 0, M = 0;
			long[] points = null;
			ArrayList<Interval> intervals = new ArrayList<Interval>();
			//citesc numarul de puncte
			N = myReader.nextLong();
			//citesc numarul de intervale
			M = myReader.nextLong();
			points = new long[(int) N];
			//citesc punctele
			for (int i = 0; i < N; i++) {
				points[i] = myReader.nextLong();
			}
			//citesc intervalele
			for (int i = 0; i < M; i++) {
				long start = myReader.nextLong();
				long end = myReader.nextLong();
				intervals.add(new Interval(start, end));
			}
			//sortez intervalele dupa inceput
			Collections.sort(intervals, new Comparator<Interval>() {
				public int compare(Interval i1, Interval i2) {
					return Long.valueOf(i1.startingPoint).compareTo(i2.startingPoint);
				}
			});
			//aplic algoritmul Greedy pe puncte si intervale
			int intervalsNr = greedy(points, intervals);
			PrintWriter output = new PrintWriter(outputFileName);
			output.println(intervalsNr);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("FILE_NOT_FOUND");
		}
	}

	private static int greedy(long[] points, ArrayList<Interval> intervals) {
		int intNr = 0;
		int n = points.length;
		int m = intervals.size();
		int index = 0;
		long right = -1;
		for (int i = 0; i < n; i++) {
			long myPoint = points[i];
			//daca punctul meu este sub marginea dreapta
			//continui in for
			if (myPoint <= right) {
				continue;
			}
			//incrementez nr de intervale
			intNr++;
			for (int j = index; j < m; j++) {
				if (myPoint > intervals.get(j).startingPoint) {
					//upatez marginea dreapta
					if (intervals.get(j).endingPoint > right) {
						right = intervals.get(j).endingPoint;
					}
				} else {
					//modific indexul pt intervale
					index = j;
					break;
				}
			}
		}
		return intNr;
	}
}
