import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 
 * 	@author Graure Marius-Alexandru
 *	Clasa ce defineste un printer propriu.
 */

public class PRINT {

	/**
	 * 
	 * Sorteaza si afiseaza vectorul de ID-uri sau NIL daca acesta este nul
	 * 
	 * @param IDs vector de ID-uri (double)
	 * @param out PrintWriter file
	 */
	public void printSolution(double[] IDs, PrintWriter out){
		if(vectorNotNull(IDs) == 1){
			Arrays.sort(IDs);
			printVector(IDs, out);
		}
		else	{out.println("NIL");
		System.out.println("NIL");}
	}
	
	/**
	 * Verifica daca vectorul este nul.
	 * @param IDs
	 * @return 1 (daca vectorul nu este nul) sau 0 (daca vectorul este nul)
	 */
	
	private int vectorNotNull(double[] IDs) {
		for (int i = 0; i < IDs.length; i++) {
			if(IDs[i] != 0){
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * Afiseaza valorile din vector (facand cast la int)
	 * 
	 * @param IDs vector de ID-uri (double) de figuri geometrice
	 * @param out PrintWriter file
	 */
	
	private void printVector(double[] IDs, PrintWriter out) {
		System.out.print((int)IDs[0]);
		out.print((int)IDs[0]);
		for(int i = 1; i < IDs.length; i++){
			out.print(" " + (int)IDs[i]);
			System.out.print(" " + (int)IDs[i]);
		}
		System.out.println();
		out.println();
	}
	
}
