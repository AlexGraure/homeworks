/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class ForNode extends Node{
	Node assign1 = null;
	Node expr = null;
	Node assign2 = null;
	Node prog = null;
	
	public ForNode() {
		super();
		
	}
	
	@Override
	public void add(Node assign1, Node expr, Node assign2, Node prog) {
		setAssign1(assign1);
		setExpr( expr);
		setAssign2(assign2);
		setProg(prog);
	}
	
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}

	public Node getAssign1() {
		return assign1;
	}

	public void setAssign1(Node assign1) {
		this.assign1 = assign1;
	}

	public Node getExpr() {
		return expr;
	}

	public void setExpr(Node expr) {
		this.expr = expr;
	}

	public Node getAssign2() {
		return assign2;
	}

	public void setAssign2(Node assign2) {
		this.assign2 = assign2;
	}

	public Node getProg() {
		return prog;
	}

	public void setProg(Node prog) {
		this.prog = prog;
	}
	
	
}
