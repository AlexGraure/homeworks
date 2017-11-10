package kvertex;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Main {
	public static void main(String [] args){
		
		try {
			BufferedReader reader = null;
			FileInputStream input = new FileInputStream("test.in");
			reader = new BufferedReader(new InputStreamReader(input));
			PrintWriter output = new PrintWriter("test.out");

			String line = null;
			String[] tokens = null;
			/**
			 * Delimitatori pentru splitarea stringurilor
			 */
			String delimiters = " ";
			line = reader.readLine();
			tokens = line.split(delimiters);
			int u = 0, v = 0;
			int Nv = Integer.parseInt(tokens[0]);
			int Ne = Integer.parseInt(tokens[1]);
			int k = Integer.parseInt(tokens[2]);
			String Fi = "";
			String Gij = "";
			Fi = Fi.concat(V(Nv, k));
			for(int i = 0; i < Ne; i++) {
				line = reader.readLine();
				tokens = null;
				tokens = line.split(delimiters);
				
				u = Integer.parseInt(tokens[0]);
				v = Integer.parseInt(tokens[1]);
				Gij = Gij.concat("(");
				for(int j = 1; j <= k; j++){
					Gij = Gij.concat("x" + ((u - 1)*k + j) + "V");
					Gij = Gij.concat("x" + ((v - 1)*k + j) + "V");
				}
				Gij = Gij.substring(0, Gij.length() - 1);
				Gij = Gij.concat(")^");
				line = null;
			}
			Gij = Gij.substring(0, Gij.length() - 1);
			output.print(Fi + "^" + Gij);
			reader.close();
			input.close();
			output.close();
			} catch (FileNotFoundException e) {
			System.out.println("FILE_NOT_FOUND");
			} catch (IOException e) {
			e.printStackTrace();
			}	
	}		
	private static String V(int n, int k){
		String f = "";
		for (int p = 1; p <=k; p++) {
			f = f.concat(getFi(p, n, k) + "^");
		}
		f = f.substring(0, f.length() - 1);
		return f;
	}
	private static String getFi(int p, int n, int k) {
		String fi = "";
		for (int i = 1; i <= n - 1; i++) {
			for (int j = i + 1; j <= n; j++) {
				fi = fi.concat("(~x" + ((i - 1)*k + p));
				fi = fi.concat("V~x" + ((j - 1)*k + p) + ")^");
			}	
		}
		fi = fi.substring(0, fi.length() - 1);
		return fi;
	}
	
}