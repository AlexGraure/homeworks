/* Graure Marius-Alexandru 325CB*/
package permutari;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/* 
Clasa pentru graf
*/
public class Graph {

	/* lista de liste de noduri adiacente */
	private List<List<Integer>> adjList;

	/* numarul de muchii ce intra intr-un nod */
	public int[] indegree;
	private Integer[] nodes;
	/* HashMap pentru fiecare litera - numar*/
	private static HashMap<String, Integer> lettersMap = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 1L;
		{
			int i = 0;
			for (char ch = 'a'; ch <= 'z'; ch++) {
				put(String.valueOf(ch), i);
				i++;
			}
		}
	};
	public int hasEdges = 0; /* Numarul de muchii din graf */
	public int impossible = 0; /* Daca este 1 atunci este imposibil */

	/*Construieste un graf cu nrNodes noduri */
	public Graph(int nrNodes) {
		adjList = new ArrayList<List<Integer>>();
		for (int i = 0; i < nrNodes; i++) {
			adjList.add(i, new ArrayList<Integer>());
		}
		indegree = new int[nrNodes];
		for (int i = 0; i < nrNodes; i++) {
			indegree[i] = 0;
		}
	}

	/* Initializeaza nodurile din graf */
	public void presetGraph() {
		nodes = new Integer[26];
		int i = 0;
		while (i < 26) {
			nodes[i] = i;
			i++;
		}
	}
	
	/* Returneaza vectorul de noduri din graf */
	public Integer[] getNodes() {
		return this.nodes;
	}

	/* Seteaza muchiile grafului */
	public void setGraph(String[] words) {
		int i = 0;
		while (i < words.length - 1) {
			String word1 = words[i++];
			String word2 = words[i];
			int diff = 0;
			if (word1.length() > word2.length()) {
				for (int j = 0; j < word2.length(); j++) {
					if (word1.charAt(j) != word2.charAt(j)) {
						diff = 1;
						String ch1 = String.valueOf(word1.charAt(j));
						String ch2 = String.valueOf(word2.charAt(j));
						Integer from = lettersMap.get(ch1);
						Integer to = lettersMap.get(ch2);
						this.addEdge((int) from, (int) to);
						break;
					}
				}
				if (diff != 1) {
					this.impossible = 1;
					break;
				}
			} else {
				for (int j = 0; j < word1.length(); j++) {
					if (word1.charAt(j) != word2.charAt(j)) {
						String ch1 = String.valueOf(word1.charAt(j));
						String ch2 = String.valueOf(word2.charAt(j));
						Integer from = lettersMap.get(ch1);
						Integer to = lettersMap.get(ch2);
						this.addEdge((int) from, (int) to);
						break;
					}
				}
			}
		}
	}
	
	/* Adauga o muchie in graf */
	void addEdge(int from, int to) {
		hasEdges++;
		adjList.get(from).add(to);
		indegree[to]++;
	}

	/* Obtine numarul de muchii ce intra intr-un nod */
	int getIndegreeOf(int i) {
		return indegree[i];
	}

	/* Returneaza vecinii nodului node */
	List<Integer> getNeighboursOf(int node) {
		return adjList.get(node);
	}

	/* Returneaza numarul de noduri din graf */
	int getTotalNumOfNodes() {
		return adjList.size();
	}
}
