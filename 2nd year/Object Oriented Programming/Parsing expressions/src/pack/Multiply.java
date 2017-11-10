package pack;

/**
 * 
 * 	@author Graure Marius-Alexandru
 * 	 Clasa ce implementeaza interfata Visitor, ale carei
 * metode realizeaza inmultirea
 */
public class Multiply implements Visitor {

	public Multiply(){
	}
	
	@Override
	public Node visit(Node a, Node b) {
		return Node.getResult(this, a.getInfo(), b.getInfo());
	}
	
	@Override
	/**
	 * Metoda ce realizeaza adunarea a doua int-uri
	 */
	public Node visit(int a, int b) {//int
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		int result = a * b;
		
		System.out.println("multiply int-int " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui int
	 * si a unui double
	 */
	public Node visit(int a, double b) {//double***********************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double result = 0;
		if(Double.isNaN(b)){
			result = Double.NaN;
		}
		else {
			result = a * b;
		}
		
		System.out.println("multiply int-double " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui int
	 * si a unui string
	 */
	public Node visit(int a, String b) {//string
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		String newString = null;
		if(a <= 0 || Double.isNaN(a) || b == null){
			newString = "";
		}
		else {
			for(int i = 0; i < a; i++){
				if(newString == null){
					newString = b;
				}
				else {
					newString += b;
				}
			}
		}
		
		System.out.println("multiply int-string " + newString);
		node = NodeFactory.getInstance().createNode(newString);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui double
	 * si a unui int
	 */
	public Node visit(double a, int b) {//double***********************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double result = 0;
		if(Double.isNaN(a)){
			result = Double.NaN;
		}
		else {
			result = a * b;
		}
		
		System.out.println("multiply double-int " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui double
	 * si a unui double
	 */
	public Node visit(double a, double b) {//double***********************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double result = 0;
		if(Double.isNaN(a) || Double.isNaN(b)){
			result = Double.NaN;
		}
		else {
			result = a * b;
		}
		
		System.out.println("multiply double-double " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui double
	 * si a unui string
	 */
	public Node visit(double a, String b) {//double***********************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double result = 0;
		if(Double.isNaN(a)){
			result = Double.NaN;
		}
		else {
			result = a * b.length();
			result = (double)Math.round(result * 100) / 100;
			//result = Evaluate.round(result, 2);
		}
		
		System.out.println("multiply double-string " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui string
	 * si a unui int
	 */
	public Node visit(String a, int b) {//string
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		String newString = null;
		if(b <= 0 || Double.isNaN(b) || a == null){
			newString = "";
		}
		else {
			for(int i = 0; i < b; i++){
				if(newString == null){
					newString = a;
				}
				else {
					newString += a;					
				}
			}
		}
		
		System.out.println("multiply string-int " + newString);
		node = NodeFactory.getInstance().createNode(newString);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui string
	 * si a unui double
	 */
	public Node visit(String a, double b) {//double***********************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double result = 0;
		if(Double.isNaN(b) || a == null){
			result = Double.NaN;
		}
		else {
			result = a.length() * b;
			result = (double)Math.round(result * 100) / 100;
			//result = Evaluate.round(result, 2);
		}
		
		System.out.println("multiply string-double " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui string
	 * si a unui string
	 */
	public Node visit(String a, String b) {//int***********************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		int result;
		if(a == null || b == null){
			result = 0;
		}
		else {
			result = a.length() * b.length();
		}
		
		System.out.println("multiply string-string " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

}
