package pack;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
/**
 * 
 * 	@author Graure Marius-Alexandru
 *		Clasa in care se realizeaza citirea si apelarea "comenzilor"
 */
public class READ {
	/**
	 * Obiect de tip {@link MAP} ce tine evidenta datelor
	 */
	private MAP hMap;
	
	public READ() {
		hMap = new MAP();
	}

	public void Read(String file_name) {
		
		try {
			BufferedReader reader = null;
			FileInputStream input = new FileInputStream(file_name);
			reader = new BufferedReader(new InputStreamReader(input));
			PrintWriter output = new PrintWriter("arbore.out");

			String line = null;
			String[] tokens = null;
			/**
			 * Delimitatori pentru splitarea stringurilor
			 */
			String delimiters = "[ \"=;\t\n]+";
			String command = null;

			
			while ((line = reader.readLine()) != null) {
				tokens = null;
				tokens = line.split(delimiters);
				command = tokens[0];
				/**
				 * Tratez fiecare caz de comanda in parte
				 */
				
				switch(command){
				case "eval":
					/**
					 * Daca trebuie evaluata o expresie, se apeleaza functia ”eval” din
					 * clasa Evaluate
					 */
					Evaluate.getInstance().eval(tokens, gethMap(), output);
					break;
				default:
					/**
					 * Daca nu trebuie evaluata o expresie inseamna ca trebuie adaugata
					 * o noua valoare in hMAP
					 */
					addToMap(tokens);
					break;
				}
				line = null;
			}
			reader.close();
			input.close();
			output.close();
			} catch (FileNotFoundException e) {
			System.out.println("FILE_NOT_FOUND");
			} catch (IOException e) {
			e.printStackTrace();
			}	
	}		

	/**
	 * Metoda ce adauga un nume si o valoare in hashmap, in functie de tipul de date
	 * @param tokens vector de stringuri ce tine tipul, numele si valoarea
	 * datelor
	 */
	private void addToMap(String[] tokens){
		/**
		 * Se retine tipul si numele variabilei
		 */
		String nodeType = tokens[0];
		String nodeName = tokens[1];
		String nodeValue = null;
		/**
		 * In functie de tip, se adauga intr-unul din tabele
		 */
		switch(nodeType){
		case "int":
			nodeValue = tokens[2];
			gethMap().getIntMap().put(nodeName, Integer.parseInt(nodeValue));
			break;
		case "double":
			nodeValue = tokens[2];
			gethMap().getDoubleMap().put(nodeName, Double.parseDouble(nodeValue));
			break;
		case "string":
			/**
			 * Se verifica daca este un string compus
			 */
			for(int i = 2; i < tokens.length; i++){
				if(nodeValue == null){
					nodeValue = tokens[i];
				}
				else {
					nodeValue = nodeValue + " " + tokens[i];
				}
			}
			gethMap().getStringMap().put(nodeName, nodeValue);
			break;
		}
		gethMap().getMap().put(nodeName, nodeType);
	}

	private MAP gethMap() {
		return hMap;
	}
	
}