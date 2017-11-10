package pack;

/**
 * 
 * 	@author Graure Marius-Alexandru
 * 	 Clasa ce implementeaza interfata Visitor, ale carei
 * metode realizeaza adunarea
 */
public class Plus implements Visitor {

	public Plus(){
	}
	
	@Override
	public Node visit(Node a, Node b) {
		return Node.getResult(this, a.getInfo(), b.getInfo());
	}
	
	@Override
	/**
	 * Metoda ce realizeaza adunarea a doua int-uri
	 */
	public Node visit(int a, int b) { //return int 
		Node node = null;
		int sum = a + b;
		
		node = NodeFactory.getInstance().createNode(sum);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui int
	 * si a unui double
	 */
	public Node visit(int a, double b) {//double***********************************************************DOWNdated
		Node node = null;
		double sum = 0;
		if(Double.isNaN(b)){
			sum = Double.NaN;
		}
		else {
			sum = a + b;
		}
		
		System.out.println("plus int-double " + sum);
		node = NodeFactory.getInstance().createNode(sum);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui int
	 * si a unui string
	 */
	public Node visit(int a, String b) {//string***********************************************************DOWNdated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		String newString =  null;
		if(b == null){
			newString = Integer.toString(a);
		}
		else {
			newString = a + b;
		}
		
		System.out.println("plus int-string " + newString);
		node = NodeFactory.getInstance().createNode(newString);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui double
	 * si a unui int
	 */
	public Node visit(double a, int b) {//double***********************************************************DOWNdated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double sum = 0;
		if(Double.isNaN(a)){
			sum = Double.NaN;
		}
		else {
			sum = a + b;
		}
		
		System.out.println("plus double-int " + sum);
		node = NodeFactory.getInstance().createNode(sum);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui double
	 * si a unui double
	 */
	public Node visit(double a, double b) {//double***********************************************************DOWNdated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double sum = 0;
		if(Double.isNaN(a) || Double.isNaN(b)){
			sum = Double.NaN;
		}
		else {
			sum = a + b;
		}
		
		System.out.println("plus double-double " + sum);
		node = NodeFactory.getInstance().createNode(sum);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui double
	 * si a unui string
	 */
	public Node visit(double a, String b) {
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		String newString = null;
		if(b == null){
			if(Double.isNaN(a)){
				newString = "NaN";
			}
			else {
				a = (double)Math.round(a * 100) / 100;
				newString = Double.toString(a);
			}
		}
		else {
			if(Double.isNaN(a)){
				newString = "NaN" + b;
			}
			else {
				a = (double)Math.round(a * 100) / 100;
				newString = a + b; 
			}
		}
		
		node = NodeFactory.getInstance().createNode(newString);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui string
	 * si a unui int
	 */
	public Node visit(String a, int b) {
		
		Node node = null;
		String newString = null;
		if(a == null){
			newString = Integer.toString(b);
		}
		else {
			newString = a + b;
		}
		
		node = NodeFactory.getInstance().createNode(newString);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui string
	 * si a unui double
	 */
	public Node visit(String a, double b) {
		
		Node node = null;
		String newString = null;
		if(a == null){
			if(Double.isNaN(b)){
				newString = "NaN";
			}
			else {
				b = (double)Math.round(b * 100) / 100;
				newString = Double.toString(b);
			}
		}
		else {
			if(Double.isNaN(b)){
				newString = a + "NaN";
			}
			else {
				b = (double)Math.round(b * 100) / 100;
				newString = a + b; 
			}
		}
		
		System.out.println("plus string-double " + newString);
		node = NodeFactory.getInstance().createNode(newString);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui string
	 * si a unui string
	 */
	public Node visit(String a, String b) {
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		String newString = null;
		if(a == null){
			if(b == null){
				newString = "";
			}
			else {
				newString = b;
			}
		}
		else {
			if (b == null){
				newString = a;
			}
			else {
				newString = a + b;
			}
		}
		
		System.out.println("plus string-string " + newString);
		node = NodeFactory.getInstance().createNode(newString);
		return node;
	}

}
