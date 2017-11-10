package pack;
/**
 * 
 * 	@author Graure Marius-Alexandru
 * Clasa abstracta ce defineste nodul
 */
public abstract class Node {
	private Node leftSon;
	private Node rightSon;
	private Info info;
	public Visitor visitor;
	/**
	 * Constructor general
	 */
	public Node(){
		this.info = new Info();
	}
	/**
	 * Metoda ce verifica daca nodul curent este Operand/Operator
	 * Daca este Operator, atunci aplica metoda visit din visitor-ul propriu.
	 * In caz contrar (daca este Operand), returneaza nodul curent.
	 * @return un nod cu informatia rezultata
	 */
	 public Node Calculate(){
		 	if(this.visitor == null){
		 		return this;
		 	}
		 	return visitor.visit(this.getLeftSon().Calculate(), this.getRightSon().Calculate());
	 }
	 /**
	  * Metoda statica ce verifica cazurile tipurilor de valori ale lui a si b
	  * si apeleaza metoda visit (a lui visitor) corespunzatoare
	  * @param visitor
	  * @param a obiect de tip Info
	  * @param b obiect de tip Info
	  * @return nodul cu informatia rezultata
	  */
	 public static Node getResult(Visitor visitor, Info a, Info b){
		 Node node = null;
			switch(a.getValueType()){
			case "int":
				switch(b.getValueType()){
				case "int":
					node = visitor.visit(a.getIntValue(), b.getIntValue());
					break;
				case "double":
					node = visitor.visit(a.getIntValue(), b.getDoubleValue());
					break;
				case "string":
					node = visitor.visit(a.getIntValue(), b.getStringValue());
					break;
				}
				break;
			case "double":
				switch(b.getValueType()){
				case "int":
					node = visitor.visit(a.getDoubleValue(), b.getIntValue());
					break;
				case "double":
					node = visitor.visit(a.getDoubleValue(), b.getDoubleValue());
					break;
				case "string":
					node = visitor.visit(a.getDoubleValue(), b.getStringValue());
					break;
				}
				break;
			case "string":
				switch(b.getValueType()){
				case "int":
					node = visitor.visit(a.getStringValue(), b.getIntValue());
					break;
				case "double":
					node = visitor.visit(a.getStringValue(), b.getDoubleValue());
					break;
				case "string":
					node = visitor.visit(a.getStringValue(), b.getStringValue());
					break;
				}
				break;
			}
			
			return node;
		}
	
	public void setSons(Node leftSon, Node rightSon){
		this.leftSon = leftSon;
		this.rightSon = rightSon;
	}
	
	public Info getInfo(){
		return this.info;
	}
	
	public Node getLeftSon() {
		return leftSon;
	}

	public Node getRightSon() {
		return rightSon;
	}
	/**
	 *
	 * @return un nod cu informatia Double.NaN
	 */
	public static Node doubleNaN(){
		Node node = null;
		double info = Double.NaN;
		node = NodeFactory.getInstance().createNode(info);
		return node;
	}
}
