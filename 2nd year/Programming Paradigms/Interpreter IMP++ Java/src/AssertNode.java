/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class AssertNode extends Node {

	Node expr = null;
	
	public AssertNode() {
		super();
	}
	
	@Override
	public void add(Node expr1) {
		setExpr((ExprNode) expr1);
	}

	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}

	public Node getExpr() {
		return expr;
	}

	public void setExpr(ExprNode expr) {
		this.expr = expr;
	}

}
