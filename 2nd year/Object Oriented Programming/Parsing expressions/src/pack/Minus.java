package pack;

/**
 * 
 * 	@author Graure Marius-Alexandru
 * 	 Clasa ce implementeaza interfata Visitor, ale carei
 * metode realizeaza scaderea
 */
public class Minus implements Visitor{

	public Minus(){
	}
	
	@Override
	public Node visit(Node a, Node b) {
		return Node.getResult(this, a.getInfo(), b.getInfo());
	}
	
	@Override
	/**
	 * Metoda ce realizeaza adunarea a doua int-uri
	 */
	public Node visit(int a, int b) {//int\
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		int dif = a - b;
		
		System.out.println("minus int-int " + dif);
		node = NodeFactory.getInstance().createNode(dif);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui int
	 * si a unui double
	 */
	public Node visit(int a, double b) {//double*********************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double dif = 0;
		if(Double.isNaN(b)){
			dif = Double.NaN;
		}
		else {
			dif = a - b;
		}
		
		System.out.println("minus int-double " + dif);
		node = NodeFactory.getInstance().createNode(dif);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui int
	 * si a unui string
	 */
	public Node visit(int a, String b) {//int************************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		int dif = 0;
		if(b == null){
			dif = a;
		}
		else {
			dif = a - b.length();
		}
		
		System.out.println("minus int-string " + dif);
		node = NodeFactory.getInstance().createNode(dif);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui double
	 * si a unui int
	 */
	public Node visit(double a, int b) {//double************************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double dif = 0;
		if(Double.isNaN(a)){
			dif = Double.NaN;
		}
		else {
			dif = a - b;
		}
		
		System.out.println("minus double-int " + dif);
		node = NodeFactory.getInstance().createNode(dif);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui double
	 * si a unui double
	 */
	public Node visit(double a, double b) {//double************************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double dif = 0;
		if(Double.isNaN(a) || Double.isNaN(b)){
			dif = Double.NaN;
		}
		else {
			dif = a - b;
		}
		
		System.out.println("minus double-double " + dif);
		node = NodeFactory.getInstance().createNode(dif);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui double
	 * si a unui string
	 */
	public Node visit(double a, String b) {//double************************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double dif = 0;
		if(Double.isNaN(a)){
			dif = Double.NaN;
		}
		else {
			if(b == null){
				dif = a;
			}
			else {
				dif = a - b.length();
			}
			//dif = Evaluate.round(dif, 2);
		}
		
		System.out.println("minus double-string " + dif);
		node = NodeFactory.getInstance().createNode(dif);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui string
	 * si a unui int
	 */
	public Node visit(String a, int b) {//String
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		String dif = null;
		if(a == null || b >= a.length()){
			dif = "";
		}
		else {
			if(b < 0){
				dif = a;
				for (int i = 0; i < (b * (-1)); i++) {
					dif = dif + "#";
				}
			}
			else {
				if(Double.isNaN(b)){
					dif = a;
				}
				else {
					int newSize = a.length() - b;
					if(newSize < 0){
						newSize *= -1;
					}
					dif = a.substring(0, newSize);
				}
			}
		}
		
		System.out.println("minus string-int " + dif);
		node = NodeFactory.getInstance().createNode(dif);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui string
	 * si a unui double
	 */
	public Node visit(String a, double b) {//double************************************************************updated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double dif = 0;
		if(Double.isNaN(b)){
			dif = Double.NaN;
		}
		else {
			if(a == null){
				dif -= b;
			}
			else {
				dif = a.length() - b;
			}
			dif = (double)Math.round(dif * 100) / 100;
			//dif = Evaluate.round(dif, 2);
		}
		
		System.out.println("minus string-double " + dif);
		node = NodeFactory.getInstance().createNode(dif);
		return node;
	}

	@Override
	/**
	 * Metoda ce realizeaza adunarea a unui string
	 * si a unui string
	 */
	public Node visit(String a, String b) {//int
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		int dif = 0;
		if(a == null){
			if(b == null){
				dif = 0;
			}
			else {
				dif -= b.length();
			}
		}
		else {
			if(b == null){
				dif = a.length();
			}
			else {
				dif = a.length() - b.length();
			}
		}
	
		System.out.println("minus string-string " + dif);
		node = NodeFactory.getInstance().createNode(dif);
		return node;
	}

}
