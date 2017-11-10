/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class IfNode extends Node {

	Node expr = null;
	Node prog1 = null;
	Node prog2 = null;

	public IfNode() {
		super();
	}
	

	@Override
	public void add(Node expr, Node prog1, Node prog2) {
		setExpr(expr);
		setProg1(prog1);
		setProg2(prog2);
	}

	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}
	
	public Node getExpr() {
		return expr;
	}

	public void setExpr(Node expr) {
		this.expr = expr;
	}

	public Node getProg1() {
		return prog1;
	}

	public void setProg1(Node prog1) {
		this.prog1 = prog1;
	}

	public Node getProg2() {
		return prog2;
	}

	public void setProg2(Node prog2) {
		this.prog2 = prog2;
	}
	
	
}
