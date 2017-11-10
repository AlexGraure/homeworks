package pack;

/**
 * 
 * 	@author Graure Marius-Alexandru
 * 	 Clasa ce implementeaza interfata Visitor, ale carei
 * metode realizeaza impartirea
 */
public class Divide implements Visitor{

	public Divide(){
	}
	
	@Override
	public Node visit(Node a, Node b) {
		return Node.getResult(this, a.getInfo(), b.getInfo());
	}
	
	@Override
	public Node visit(int a, int b) {//int***********************************************************DOWNdated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		int result = 0;
		if(b == 0){
			return Node.doubleNaN();
		}
		else {
			result = a / b;
		}
		
		System.out.println("divide int-int " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}
	
	@Override
	public Node visit(int a, double b) {//double***********************************************************DOWNdated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double result = 0;
		if(b == 0 || Double.isNaN(b) == true){
			result = Double.NaN;
		}
		else {
			result = a / b;
		}
		
		System.out.println("divide int-double " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	public Node visit(int a, String b) {//int
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		Node node = null;
		int result = 0;
		if(b == null){
			return Node.doubleNaN();
		}
		result = a / b.length();
		System.out.println("divide int-string " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	public Node visit(double a, int b) {//double***********************************************************DOWNdated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double result = 0;
		if(Double.isNaN(a) == true || b==0){
			result = Double.NaN;
		}
		else {
			result = a / b;
		}
		
		System.out.println("divide double-int " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	public Node visit(double a, double b) {//double***********************************************************DOWNdated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double result = 0;
		if(Double.isNaN(a) || Info.isZero(a) || Double.isNaN(b) || Info.isZero(b)){
			result = Double.NaN;
		}
		else {
			result = a / b;
		}
		
		System.out.println("divide double-double " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	public Node visit(double a, String b) {//double***********************************************************DOWNdated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double result = 0;
		if(Double.isNaN(a) || b == null || b.equals("")){
			result = Double.NaN;
		}
		else {
			result = a / b.length();
			result = (double)Math.round(result * 100) / 100;
			//result = Evaluate.round(result, 2);
		}
		
		System.out.println("divide double-string " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	public Node visit(String a, int b) {//string
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		String newString = null;
		if(b == 0 || Double.isNaN(b)){
			newString = a;
		}
		else {
			int newSize = a.length() / b;
			if(newSize < 0){
				newSize *= -1;
			}
			newString = a.substring(0, newSize);
		}
		
		System.out.println("divide string-int " + newString);
		node = NodeFactory.getInstance().createNode(newString);
		return node;
	}

	@Override
	public Node visit(String a, double b) {//double***********************************************************DOWNdated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		double result = 0;
		if(a == null || Double.isNaN(b) || b == 0){
			result = Double.NaN;
		}
		else {
			result = a.length() / b;
			result = (double)Math.round(result * 100) / 100;
			//result = Evaluate.round(result, 2);
		}
		
		System.out.println("divide string-double " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

	@Override
	public Node visit(String a, String b) {//int***********************************************************DOWNdated
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
		Node node = null;
		int result = 0;
		if(a == null || b == null){
			return Node.doubleNaN();
		}
		result = a.length() / b.length();
		
		System.out.println("divide string-string " + result);
		node = NodeFactory.getInstance().createNode(result);
		return node;
	}

}
