/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public abstract class Node implements Visitable {
	/**
	 * Implementeaza metoda din Visitable
	 */
	public Node accept(Visitor v) {
		return null;
	}

	public void add(Node expr1) {

	}

	public void add(Node prog1, Node prog2) {
		
	}

	public void add(Node expr1, Node prog1, Node prog2) {
		
	}

	public void add(Node assign1, Node expr1, Node assign2, Node prog1) {

	}

}
