/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class SemicoloneNode extends Node{
	Node prog1 = null;
	Node prog2 = null;
	
	public SemicoloneNode() {
		super();
	}
	
	@Override
	public void add(Node prog1, Node prog2) {
		setProg1((ProgNode) prog1);
		setProg2((ProgNode) prog2);
	}
	
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}

	public Node getProg1() {
		return prog1;
	}

	public void setProg1(ProgNode prog1) {
		this.prog1 = prog1;
	}

	public Node getProg2() {
		return prog2;
	}

	public void setProg2(ProgNode prog2) {
		this.prog2 = prog2;
	}
	
	
	
}
