import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 
 * 	@author Graure Marius-Alexandru
 *	Clasa in care se realizeaza citirea
 */
public class READ {

	/**
	 * Se citesc coordonatele delimitaroare si se creeaza cadranul (arborele)
	 * <br>
	 * Se citeste fiecare linie si se extrag comenzile (11, 22, 33, 44)
	 * <br>
	 * 
	 * @throws IOException
	 */
	public void Read() throws IOException{

		int command = 0, figure = 0;
		
		try {
			BufferedReader reader = null;
			PrintWriter output = new PrintWriter("quadtree.out");
			FileInputStream input = new FileInputStream("quadtree.in");
			reader = new BufferedReader(new InputStreamReader(input));
			
			String line = null;
			line = reader.readLine();
			String[] words = line.split(" ");
			
			/**Citesc dimensiunile ecranului**/
			
			double Xmin = 0, Ymin = 0, Xmax = 0, Ymax = 0;
			Xmin = Double.parseDouble(words[0]);
			Ymin = Double.parseDouble(words[1]);
			Xmax = Double.parseDouble(words[2]);
			Ymax = Double.parseDouble(words[3]);
			
			Operations op = new Operations();
			ArrayList<GeometricObject> MAP = new ArrayList<GeometricObject>();
			GeometricObject frame = new GeometricObject(Xmin, Ymin, Xmax, Ymax);
			Quadtree qtree = new Quadtree(frame, 4);
			
			/**Citesc**/
			line = null;
			while((line = reader.readLine()) != null) {
				/**Citesc comanda si tratez fiecare caz in parte**/
				words = null;
				words = line.split(" ");
				
				command = Integer.parseInt(words[0]);
				
				/**Comanda = insert**/
				if(command == 11){
					/**Verific ce figura trebuie inserata**/
					figure = Integer.parseInt(words[1]);
					/**Daca trebuie inserat un dreptunghi**/
					op.op1(qtree, words, figure, MAP);
				}
				else {
					/**Comanda = delete**/
					if(command == 22){
						op.op2(qtree, words, MAP);
					}
					else {
						/**Comanda = Point_Collision**/
						if(command == 33){
							op.op3(qtree, words, output);
						}
						else {
							/**Comanda = Figures_Collision**/
							if(command == 44){
								figure = Integer.parseInt(words[1]);
								op.op4(qtree, words, figure, output);
							}
						}
					}
				}
				line = null;
			}
			reader.close();
			input.close();
			output.close();
		}	catch (FileNotFoundException e) {
			System.out.println("FILE_NOT_FOUND");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}