/* Graure Marius-Alexandru 325CB*/
package patrula;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import tools.MyReader;

public class Patrula {
	public static void main(String[] args) {
		String inputFileName = "patrula.in";
		String outputFileName = "patrula.out";
		try {
			MyReader myReader = new MyReader(inputFileName);
			Graph g = new Graph(myReader);
			/* Obtin caile minime */
			List<List<Integer>> paths = g.getPaths();
			PrintWriter output = new PrintWriter(outputFileName);
			/* Obtin media */
			double average = getAverage(paths, g, output);
			output.format("%.3f", average);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("FILE_NOT_FOUND");
		}
	}

	/* Metoda ce calculeaza media */
	private static double getAverage(List<List<Integer>> paths, Graph g, PrintWriter output) {
		/* Numarul de drumuri */
		long nrStreets = 0;
		/* Vector ce total de drumuri corespunzatoare */
		/* fiecarui nod */
		long[] map = new long[g.getNodeCount() + 1];
		map = setMap(map);
		for (List<Integer> path : paths) {
			long nr = 1;
			for (int i = 0; i < path.size() - 1; i++) {
				int u = path.get(i);
				int v = path.get(i + 1);
				nr = nr * g.getNumEdges(u, v);
			}
			nrStreets += nr;
			for (int i = 0; i < path.size(); i++) {
				Integer u = path.get(i);
				if (i == 0 || i == path.size() - 1) {
					map[u] += nr;
				} else {
					map[u] += 2 * nr;
				}
			}
		}
		output.println(nrStreets);
		long maxIlStreets = getMax(map);
		return (double) maxIlStreets / nrStreets;
	}

	/* Metoda ce returneaza numarul maxim de drumuri */
	private static long getMax(long[] map) {
		long max = 0;
		for (long x : map) {
			if (x > max) {
				max = x;
			}
		}
		return max;
	}

	/* Metoda ce seteaza vectorul de drumuri */
	private static long[] setMap(long[] map) {
		for (int i = 1; i < map.length; i++) {
			map[i] = 0;
		}
		return map;
	}
}