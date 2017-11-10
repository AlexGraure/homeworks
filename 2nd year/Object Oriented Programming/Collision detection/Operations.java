import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 
 * 	@author Graure Marius-Alexandru
 *	Clasa ce contine operatiile de baza.
 */

public class Operations {

	
	/**
	 * Operatia 11
	 * <br>
	 * Citeste coordonatele si ID-ul figurii in funtie de valoarea
	 * parametrului "figure" primit si apeleaza metoda Insert din
	 * Quadtree
	 * 
	 * @param qtree Quadtree-ul primit ca parametru
	 * @param words vector de String-uri
	 * @param figure tipul figurii geometrice
	 * @param MAP ArrayList de tip {@link GeometricObject}
	 */
	public void op1(Quadtree qtree, String[] words, int figure, ArrayList<GeometricObject> MAP){
		
		if(figure == 1){
			double ID, x1, y1, x2, y2;
			ID = Double.parseDouble(words[2]);
			x1 = Double.parseDouble(words[3]);
			y1 = Double.parseDouble(words[4]);
			x2 = Double.parseDouble(words[5]);
			y2 = Double.parseDouble(words[6]);
			GeometricObject fig1 = new Rectangle(ID, x1, y1, x2, y2);
			MAP.add(fig1);
			qtree.Insert(qtree, fig1);
		}
		else {
			if(figure == 2){
				double ID, x1, y1, x2, y2, x3, y3;

				ID = Double.parseDouble(words[2]);
				x1 = Double.parseDouble(words[3]);
				y1 = Double.parseDouble(words[4]);
				x2 = Double.parseDouble(words[5]);
				y2 = Double.parseDouble(words[6]);
				x3 = Double.parseDouble(words[7]);
				y3 = Double.parseDouble(words[8]);

				GeometricObject fig2 = new Triangle(ID, x1, y1, x2, y2, x3, y3);
				MAP.add(fig2);
				qtree.Insert(qtree, fig2);
			}
			else {
				if(figure == 3){
					double ID, R, x, y;

					ID = Double.parseDouble(words[2]);
					R = Double.parseDouble(words[3]);
					x = Double.parseDouble(words[4]);
					y = Double.parseDouble(words[5]);

					GeometricObject fig3 = new Circle(ID, R, x, y);
					MAP.add(fig3);
					qtree.Insert(qtree, fig3);
				}
				else {
					if(figure == 4){
						double ID, x1, y1, x2, y2, x3, y3, x4, y4;

						ID = Double.parseDouble(words[2]);
						x1 = Double.parseDouble(words[3]);
						y1 = Double.parseDouble(words[4]);
						x2 = Double.parseDouble(words[5]);
						y2 = Double.parseDouble(words[6]);
						x3 = Double.parseDouble(words[7]);
						y3 = Double.parseDouble(words[8]);
						x4 = Double.parseDouble(words[9]);
						y4 = Double.parseDouble(words[10]);

						GeometricObject fig4 = new Rhombus(ID, x1, y1, x2, y2, x3, y3, x4, y4);
						MAP.add(fig4);
						qtree.Insert(qtree, fig4);
					}
				}
			}
		}
		
	}
	/**
	 * Operatia 22
	 * <br>
	 * Citeste ID-ul care trebuie sters si apeleaza metoda deleteID
	 * din Quadtree 
	 * 
	 * @param qtree Quadtree-ul primit ca parametru
	 * @param words vector de String-uri
	 * @param MAP ArrayList de tip {@link GeometricObject}
	 */
	public void op2(Quadtree qtree, String[] words, ArrayList<GeometricObject> MAP) {
		double ID = Double.parseDouble(words[1]);
		
		for(int i = 0; i < MAP.size(); i++){
			if(MAP.get(i).getID() == ID){
				qtree.deleteID(qtree, MAP.get(i));
				MAP.remove(MAP.get(i));
			}
		}
		/*GeometricObject fig = null;
		qtree.deleteID(qtree, MAP[(int)ID]);
		*/
	}
	
	/**
	 * 
	 * Operatia 33
	 * <br>
	 * Citeste abscisa si ordonata punctului si apeleaza metoda
	 * PCol (PointCollision) din Quadtree 
	 *  
	 * @param qtree Quadtree-ul primit ca parametru
	 * @param words vector de String-uri
	 * @param output PrintWriter file
	 * */
	
	public void op3(Quadtree qtree, String[] words, PrintWriter output) {
		PRINT printer = new PRINT();
		double x, y;
		double[] IDs = new double[10000];
		
		x = Double.parseDouble(words[1]);
		y = Double.parseDouble(words[2]);
		
		qtree.PCol(qtree, x, y, IDs);
		
		int index = getIndex(IDs);
		double[] copy = new double[index];
		System.arraycopy(IDs, 0, copy, 0, index);
		IDs = copy;
		
		printer.printSolution(IDs, output);
	}

	/**
	 * 
	 * @param IDs vector(double) ce retine valoarea ID-urilor figurilor
	 * @return indexul primului element nul
	 */
	
	private int getIndex(double[] IDs) {
		for(int i = 0; i < IDs.length; i++){
			if (IDs[i] == 0){
				if(i == 0){
					return 1;
				}
				return i;
			}
		}
		return 0;
	}

	/**
	 * Operatia 44
	 * <br>
	 * Citeste coordonatele figurii geometrice (tipul este determinat
	 * de valoarea parametrului "figure" primit).
	 * 
	 * @param qtree Quadtree-ul primit ca parametru
	 * @param words vector de String-uri
	 * @param figure tipul figurii geometrice
	 * @param output PrintWriter file
	 */
	
	public void op4(Quadtree qtree, String[] words, int figure, PrintWriter output) {
		
		PRINT printer = new PRINT();
		
		if(figure == 1){

			double x1, y1, x2, y2;
			double[] IDs = new double[10000];
			
			x1 = Double.parseDouble(words[2]);
			y1 = Double.parseDouble(words[3]);
			x2 = Double.parseDouble(words[4]);
			y2 = Double.parseDouble(words[5]);

			GeometricObject fig = new Rectangle(x1, y1, x2, y2);
			qtree.FCol(qtree, fig, IDs);
			
			int index = getIndex(IDs);
			double[] copy = new double[index];
			System.arraycopy(IDs, 0, copy, 0, index);
			IDs = copy;
			
			printer.printSolution(IDs, output);
		}

		else {
			/**Daca trebuie inserat un triunghi**/
			if(figure == 2){

				double x1, y1, x2, y2, x3, y3;
				double[] IDs = new double[10000];
				
				x1 = Double.parseDouble(words[2]);
				y1 = Double.parseDouble(words[3]);
				x2 = Double.parseDouble(words[4]);
				y2 = Double.parseDouble(words[5]);
				x3 = Double.parseDouble(words[6]);
				y3 = Double.parseDouble(words[7]);

				GeometricObject fig = new Triangle(x1, y1, x2, y2, x3, y3);
				qtree.FCol(qtree, fig, IDs);
				
				int index = getIndex(IDs);
				double[] copy = new double[index];
				System.arraycopy(IDs, 0, copy, 0, index);
				IDs = copy;
				
				printer.printSolution(IDs, output);
			}

			else {
				/**Daca trebuie inserat un cerc**/
				if(figure == 3){
					double[] IDs = new double[10000];
					double R, x, y;

					R = Double.parseDouble(words[2]);
					x = Double.parseDouble(words[3]);
					y = Double.parseDouble(words[4]);

					GeometricObject fig = new Circle(R, x, y);
					qtree.FCol(qtree, fig, IDs);
					
					int index = getIndex(IDs);
					double[] copy = new double[index];
					System.arraycopy(IDs, 0, copy, 0, index);
					IDs = copy;
					
					printer.printSolution(IDs, output);
				}

				else {
					/**Daca trebuie inserat un romb**/
					if(figure == 4){
						double[] IDs = new double[10000];
						double x1, y1, x2, y2, x3, y3, x4, y4;

						x1 = Double.parseDouble(words[2]);
						y1 = Double.parseDouble(words[3]);
						x2 = Double.parseDouble(words[4]);
						y2 = Double.parseDouble(words[5]);
						x3 = Double.parseDouble(words[6]);
						y3 = Double.parseDouble(words[7]);
						x4 = Double.parseDouble(words[8]);
						y4 = Double.parseDouble(words[9]);

						GeometricObject fig = new Rhombus(x1, y1, x2, y2, x3, y3, x4, y4);
						qtree.FCol(qtree, fig, IDs);
						
						int index = getIndex(IDs);
						double[] copy = new double[index];
						System.arraycopy(IDs, 0, copy, 0, index);
						IDs = copy;
						
						printer.printSolution(IDs, output);
					}
				}
			}
		}
		
	}
	
}