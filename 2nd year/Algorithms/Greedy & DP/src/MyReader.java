import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MyReader {
	BufferedReader bufferedReader;
	StringTokenizer stringToken;

	public MyReader(String inputFileName) throws FileNotFoundException {
		FileInputStream input = new FileInputStream(inputFileName);
		bufferedReader = new BufferedReader(new InputStreamReader(input));
	}

	String next() {
		while (stringToken == null || !stringToken.hasMoreElements()) {
			try {
				stringToken = new StringTokenizer(bufferedReader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringToken.nextToken();
	}

	int nextInt() {
		return Integer.parseInt(next());
	}

	long nextLong() {
		return Long.parseLong(next());
	}

	double nextDouble() {
		return Double.parseDouble(next());
	}

	String nextLine() {
		String myString = "";
		try {
			myString = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myString;
	}
}
