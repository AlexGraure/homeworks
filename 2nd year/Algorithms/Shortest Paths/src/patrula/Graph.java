/* Graure Marius-Alexandru 325CB*/
package patrula;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import tools.MyReader;

public class Graph {
	/* Numarul de noduri din graf */
	private int numNodes;
	/* Numarul de muchii din graf */
	private int numEdges;
	/* Matrice de adiacenta */
	private Integer[][] edgesMatrix;
	/* Vector de culori */
	private Color[] visited;
	/* Vector de distante */
	private Integer[] dists;
	/* Lista de liste de noduri */
	/* Lista de cai */
	private List<List<Integer>> paths;

	/* Constructor*/
	public Graph(MyReader myReader) {
		int N = myReader.nextInt();
		setNodeCount(N);
		setColors();
		setMatrix();
		int M = myReader.nextInt();
		numEdges = M;
		for (int edgeIdx = 1; edgeIdx <= numEdges; edgeIdx++) {
			int node1 = myReader.nextInt();
			int node2 = myReader.nextInt();
			insertEdge(node1, node2);
		}
		paths = new ArrayList<List<Integer>>();
	}

	/* Metoda ce intoarce path-urile gasite */
	public List<List<Integer>> getPaths() {
		dists = getDistances();
		List<Integer> path = new LinkedList<Integer>();
		computeAllPaths(numNodes, (LinkedList<Integer>) path);
		return paths;
	}

	/* Metoda ce calculeaza vectorul de distante */
	private Integer[] getDistances() {
		/* Setez vectorul de distante */
		dists = setDistances();
		/* Setez nodul de plecare ca fiind 1 */
		Integer s = 1;
		/* Setez culoarea sursei ca fiind Grey*/
		/* in curs de vizitare */
		visited[s] = Color.Grey;
		Queue<Integer> queue = new LinkedList<Integer>();
		/* Adaug nodul de plecare in coada */
		queue.add(s);
		while (!queue.isEmpty()) {
			/* Extrag nodul din coada cat timp aceasta nu este vida */
			Integer u = queue.poll();
			for (int v = 1; v < edgesMatrix.length; v++) {
				if (edgesMatrix[u][v] != 0 && visited[v] == Color.White) {
						dists[v] = dists[u] + 1;
						visited[v] = Color.Grey;
						queue.add(v);
				}
			}
			/* Setez culoarea nodului u ca fiind Black */
			/* am terminat de vizitat nodul u */
			visited[u] = Color.Black;
		}
		return dists;
	}

	/* Metoda ce calculeaza toate caile */
	private void computeAllPaths(int u, LinkedList<Integer> path) {
		/* Adaug nodul u in lista */
		path.add(u);
		/* Daca am ajuns la 1, copiez lista si o adaug in lista de cai */
		if (u == 1) {
			List<Integer> pathCopy = new LinkedList<Integer>();
			copyPath(path, pathCopy);
			paths.add(pathCopy);
		} else {
			for (int v = 1; v < numNodes + 1; v++) {
				/* Daca numarul de muchii u->v este diferit de 0 */
				/* si distanta corespunzatoare lui v este cu 1 mai */
				/* mica decat distanta corespunzatoare lui u, deci */
				/* u si v sunt vecini, atunci recursiv calculez calea */
				/* folosindu-ma de v */
				if (edgesMatrix[u][v] != 0 && dists[u] - dists[v] == 1) {
					computeAllPaths(v, path);
				}
			}
		}
		/* Scot ultimul element */
		path.removeLast();
	}

	/* Metoda ce copiaza o lista */
	private void copyPath(LinkedList<Integer> path, List<Integer> pathCopy) {
		for (Integer u : path) {
			pathCopy.add(u);
		}
	}

	/* Metoda ce insereaza o muchie */
	public void insertEdge(int fromNodeIdx, int toNodeIdx) {
		edgesMatrix[fromNodeIdx][toNodeIdx]++;
		edgesMatrix[toNodeIdx][fromNodeIdx]++;
	}

		/* Metoda ce returneaza numarul de muchii */
	public long getNumEdges(int u, int v) {
		return edgesMatrix[u][v];
	}

	/* Metoda ce seteaza numarul de noduri din graf */
	public void setNodeCount(int nr) {
		this.numNodes = nr;
	}

	/* Metoda returneaza numarul de noduri din graf */
	public int getNodeCount() {
		return numNodes;
	}

	/* Metoda ce seteaza vectorul de culori */
	/* Prestabilit la White */
	private void setColors() {
		visited = new Color[numNodes + 1];
		for (int i = 0; i < visited.length; i++) {
			visited[i] = Color.White;
		}
	}

	/* Metoda ce seteaza matricea de adiacenta */
	/* Prestabilit la 0 */ 
	private void setMatrix() {
		edgesMatrix = new Integer[numNodes + 1][numNodes + 1];
		for (int i = 1; i < numNodes + 1; i++) {
			for (int j = 1; j < numNodes + 1; j++) {
				edgesMatrix[i][j] = 0;
			}
		}
	}

	/* Metoda ce seteaza distantele, prestabilit la 0*/
	private Integer[] setDistances() {
		Integer[] dists = new Integer[numNodes + 1];
		for (int i = 0; i < dists.length; i++) {
			dists[i] = 0;
		}
		return dists;
	}

	/* Metoda ce afiseaza graful */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder("Print Graph:\n");
		for (int i = 1; i < numNodes + 1; i++) {
			sb.append("OutEdges for " + i + " -> ");
			for (int j = 1; j < numNodes + 1; j++) {
				if (edgesMatrix[i][j] != 0) {
					sb.append(j);
					sb.append(" | ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/* Metoda ce afiseaza caile */
	public void printPaths() {
		for (int i = 0; i < paths.size(); i++) {
			System.out.print("Path " + (i + 1) + ": ");
			List<Integer> path = paths.get(i);
			for (int j = 0; j < path.size() - 1; j++) {
				System.out.print(path.get(j) + "->");
			}
			System.out.println(path.get(path.size() - 1));
		}

	}
}