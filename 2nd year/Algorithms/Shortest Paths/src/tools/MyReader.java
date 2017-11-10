package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* Clasa folosita pentru a citi din fisier */
public class MyReader {
	BufferedReader bufferedReader;
	StringTokenizer stringToken;

	public MyReader(String inputFileName) throws FileNotFoundException {
		FileInputStream input = new FileInputStream(inputFileName);
		bufferedReader = new BufferedReader(new InputStreamReader(input));
	}

	public String next() {
		while (stringToken == null || !stringToken.hasMoreElements()) {
			try {
				stringToken = new StringTokenizer(bufferedReader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringToken.nextToken();
	}

	public int nextInt() {
		return Integer.parseInt(next());
	}

	public long nextLong() {
		return Long.parseLong(next());
	}

	public double nextDouble() {
		return Double.parseDouble(next());
	}

	public String nextLine() {
		String myString = "";
		try {
			myString = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myString;
	}
}
