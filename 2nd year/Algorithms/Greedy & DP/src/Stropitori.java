import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Stropitori {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String inputFileName = "stropitori.in";
		String outputFileName = "stropitori.out";
		try {
			MyReader myReader = new MyReader(inputFileName);
			String line = null;
			int n = 0, S = 0;
			//citesc numele stadionului
			if ((line = myReader.nextLine()) != null) {
				@SuppressWarnings("unused")
				String stadium = line;
			}
			//citesc numarul stropitorilor si dimensiunea stadionului
			n = myReader.nextInt();
			S = myReader.nextInt();
			long[] X = null;
			X = new long[n];
			long[] P = null;
			P = new long[n];
			//salvez punctele
			for (int i = 0; i < n; i++) {
				X[i] = myReader.nextLong();
			}
			for (int i = 0; i < n; i++) {
				P[i] = myReader.nextLong();
			}
			int nrStropitori = greedy(n, S, X, P);
			PrintWriter output = new PrintWriter(outputFileName);
			//printez rezultatul
			output.println(nrStropitori);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("FILE_NOT_FOUND");
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Took " + (endTime - startTime) + " ms");
	}

	private static int greedy(int n, int S, long[] X, long[] P) {
		int count = 0;
		String previousMode = null;
		for (int i = 0; i < n; i++) {
			//presetez modul pe blocat
			String mode = "B";
			long left = X[i] - P[i];
			long right = X[i] + P[i];
			if (i > 0) {
				//verific daca precedenta stropitoare stropeste in dreapta
				//si modific modul daca este cazul
				if (previousMode.equals("R")) {
					long previous = X[i - 1] + P[i - 1];
					if (left > X[i - 1] && left >= 0 && left > previous) {
						mode = "L";
					}
				} else {
					if (left > X[i - 1] && left >= 0) {
						mode = "L";
					}
				}
			} else {
				if (X[i] - P[i] >= 0) {
					mode = "L";
				}
			}
			//daca modul este tot B, verific daca poate stropi in dreapta
			//si modific modul daca este cazul
			if (mode.equals("B")) {
				if (i < n - 1) {
					if (right < X[i + 1] && right <= S) {
						mode = "R";
					}
				} else {
					if (right <= S) {
						mode = "R";
					}
				}
			}
			previousMode = mode;
			//daca modul este diferit de B, incrementez nr de stropitori
			if (!mode.equals("B")) {
				count++;
			}
		}
		return count;
	}
}
