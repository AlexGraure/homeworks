package pack;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Stack;
/**
 * 
 * 	@author Graure Marius-Alexandru
 * Clasa ce contine metoda de evaluare a unei expresii
 */
public class Evaluate {

	private static Evaluate INSTANCE = null;
	/**
	 * Constructor privat
	 */
	private Evaluate(){
		
	}
	/**
	 * Metoda statica
	 * @return o instanta pentru Evaluate
	 */
	public static Evaluate getInstance(){
		if(INSTANCE == null) {
			INSTANCE = new Evaluate();
		}
		return INSTANCE;
	}
	/**
	 * Metoda ce face evaluarea unei expresii reprezentata prin
	 * vectorul de stringuri primit
	 * @param tokens vector de stringuri ce contine expresia ce urmeaza
	 * a fi evaluata
	 * @param hMap HashMap cu variabilele si valorile lor
	 * @param out PrintWriter
	 */
	public void eval(String[] tokens, MAP hMap, PrintWriter out){
		Stack< Node> stack = new Stack<>();
		LinkedList<String> queue = new LinkedList<>();
		queue = getQueue(tokens);
		/**
		 * Operand sau operator
		 */
		String op = null; 
		Node node = null;
		Node leftSon = null;
		Node rightSon = null;
		/**
		 * Se extrage din coada obtinuta informatia si
		 * se creeaza noul nod.
		 * Daca este OperatorNode se scot ultimele 
		 * doua noduri si devin ”fiii” noului nod si 
		 * se adauga pe stiva noul nod.
		 * In caz contrar, doar se adauga pe stiva.
		 * 
		 */
		while(queue.isEmpty() == false){
			node = null;
			op = queue.poll();
			node = NodeFactory.getInstance().createNode(op, hMap);
			if(node instanceof OperatorNode && stack.size() >= 2){
				rightSon = stack.pop();
				leftSon = stack.pop();
				node.setSons(leftSon, rightSon);
			}
			stack.push(node);
		}
		Node tree = null;
		node = stack.pop();
		tree = node.visitor.visit(node.getLeftSon().Calculate(), node.getRightSon().Calculate());
		printResult(tree, out);
		return;
	}
	/**
	 * Metoda ce afiseaza informatia nodului primit ca argument
	 * @param tree nod ce reprezinta radacina arborelui
	 * @param out PrintWriter
	 */
	private void printResult(Node tree, PrintWriter out) {
		Info info = tree.getInfo();
		switch(info.getValueType()){
		case "int":
			out.println(info.getIntValue());
			break;
		case "double":
			if(Double.isNaN(info.getDoubleValue())){
				out.println("NaN");
			}
			else {
				Double value = info.getDoubleValue();
				value = (double)Math.round(value * 100) / 100;
				//value = Evaluate.round(value, 2);
				out.println(value);
			}
			break;
		case "string":
			out.println(info.getStringValue());
			break;
		}
		System.out.println();
	}
	/**
	 * Metoda ce rotunjeste numarul primit ca argument
	 * @param value numarul ce urmeaza a fi rotunjit
	 * @param places cate zecimale se pastreaza
	 * @return numarul rotunjit
	 */
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	/**
	 * Metoda ce aplica algoritmul Shunting-Yard pe vectorul
	 * de stringuri primit
	 * @param tokens vector de stringuri
	 * @return coada obtinuta in urma aplicarii algoritmului
	 * Shunting-Yard pe vectorul de stringuri primit
	 */
	private LinkedList<String> getQueue(String[] tokens) {
		LinkedList<String> queue = new LinkedList<>();
		Stack<String> stack = new Stack<>();
		String stack_item = null;
		int OK = 1;
		for (int i = 1; i < tokens.length; i++) {
			OK = 1;
			String item = tokens[i];
			if(item.equals("+") || item.equals("-") || item.equals("*") || item.equals("/")){
				if(stack.isEmpty()){
					stack.push(item);
				}
				else {
					AddOperator(item, stack, queue);
				}
			}
			else {
				if(item.equals("(")){
					stack.push(item);
				}
				else {
					if(item.equals(")")){
						while(OK == 1){
							stack_item = stack.pop();
							if(stack_item.equals("(")){
								OK = 0;
							}
							else {
								queue.add(stack_item);
							}
						}
					}
					else {
						queue.add(item);
					}
				}
			}
		}	
		while(stack.isEmpty() == false){
			stack_item = stack.pop();
			queue.add(stack_item);
		}
		return queue;
	}

	/**
	 * Metoda ce adauga operatorul in coada
	 * @param item ce urmeaza a fi adaugat in coada
	 * @param stack stiva
	 * @param queue coada
	 */
	private void AddOperator(String item, Stack<String> stack, LinkedList<String> queue) {
		if(stack.isEmpty()){
			stack.push(item);
			return;
		}
		
		String stack_item = stack.pop();
		int priority = Priority(item, stack_item);
		
		if(priority == 0 || priority == -1){
			queue.add(stack_item);
			AddOperator(item, stack, queue);
			return;
		}
		else	{
			stack.push(stack_item);
			stack.push(item);
			return;
		}
		
	}
	/**
	 * 
	 * @param a string
	 * @param b string
	 * @return 0 (daca operatorii au aceeasi prioritate)
	 * 1 (daca operatorul a are prioritate mai mare ca 
	 * operatorul b)
	 * -1 (daca operatorul a are prioritate mai mica decat
	 * operatorul b)
	 * 3 (daca unul din operatori este de fapt o paranteza)
	 */
	private int Priority(String a, String b){
		switch(a){
		case "+":
			if(b.equals("-") || b.equals("+")){
				return 0;
			}
			if(b.equals("*") || b.equals("/")){
				return -1;
			}
			return 3;
		case "-":
			if(b.equals("+") || b.equals("-")){
				return 0;
			}
			if(b.equals("*") || b.equals("/")){
				return -1;
			}
			return 3;
		case "*":
			if(b.equals("/") || b.equals("*")){
				return 0;
			}
			if(b.equals("+") || b.equals("-")){
				return 1;
			}
			return 3;
		case "/":
			if(b.equals("*") || b.equals("/")){
				return 0;
			}
			if(b.equals("+") || b.equals("-")){
				return 1;
			}
			return 3;
		case "(":
			return 3;
		case")":
			return 3;
		}
		return 0;
	}
	
}
