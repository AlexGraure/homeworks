/* Graure Marius-Alexandru 325CB*/
package permutari;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import tools.MyReader;

public class Permutari {
	private static Graph g = new Graph(26);
	/* HashMap numar-litera */
	public static HashMap<Integer, String> numbersMap = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			int i = 0;
			for (char ch = 'a'; ch <= 'z'; ch++) {
				put(i, String.valueOf(ch));
				i++;
			}
		}
	};

	public static void main(String[] args) {
		String inputFileName = "permutari.in";
		String outputFileName = "permutari.out";
		try {
			/* Citesc din fisier */
			MyReader myReader = new MyReader(inputFileName);
			int N = myReader.nextInt(); /* Numerul de cuvinte */
			String[] words = new String[N]; /* Cuvintele */
			for (int i = 0; i < N; i++) {
				words[i] = myReader.next();
			}
			/* Initializez nodurile din graf */
			g.presetGraph();
			/* Adaug muchii de ordine in graf */
			g.setGraph(words);
			/* Daca variabila impossible din graf e neschimbata */
			if (g.impossible == 0) {
				/* Obtin solutia prin sortare topologica */
				String solution = topSort();
				/* Si scriu in fisier */
				PrintWriter output = new PrintWriter(outputFileName);
				output.print(solution);
				output.close();
			} else {
				doTheImpossible(outputFileName);
			}
		} catch (FileNotFoundException e) {
			System.out.println("FILE_NOT_FOUND");
		}
	}

	/* Metoda statica ce realizeaza sortarea topologica bazata pe algoritmul lui Kahn */
	public static String topSort() {
		Integer[] V = g.getNodes(); /* Nodurile din graf */
		ArrayList<Integer> L = new ArrayList<Integer>();
		Stack<Integer> S = new Stack<Integer>();
		/* Pentru fiecare nod u din graf, daca nu intra nicio muchie in u, il pun
		pe stiva S */
		for (Integer u : V) {
			if (g.getIndegreeOf(u) == 0) {
				S.push(u);
			}
		}
		/* Cat timp stiva S nu este goala */
		while (!S.isEmpty()) {
			/* Scot nodul u de pe stiva si il adaug in lista L */
			Integer u = S.pop();
			L.add(u);
			/* Pentru fiecare nod v din lista de vecini ai lui u */
			for (Integer v : g.getNeighboursOf(u)) {
				/* Scad numarul de muchii ce intra in v si numarul de muchii
				din graf */
				g.indegree[v]--;
				g.hasEdges--;
				/* Daca nu mai intra nicio muchie in v, atunci il pun pe stiva */
				if (g.getIndegreeOf(v) == 0) {
					S.push(v);
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		/* Daca graful mai are muchii atunci returnez "Imposibil" */
		if (g.hasEdges != 0) {
			sb.append("Imposibil");
		} else { 
			/* In caz contrar, formez stringul prin alipirea literelor
			corespunzatoare nodurilor din graf */
			for (Integer u : L) {
				sb.append(numbersMap.get(u));
			}
		}
		return sb.toString();
	}

	/* Metoda ce scrie in fisier stringul "Imposibil" */
	private static void doTheImpossible(String outputFileName) throws FileNotFoundException {
		PrintWriter output = new PrintWriter(outputFileName);
		String solution = "Imposibil";
		output.print(solution);
		output.close();
	}
}
