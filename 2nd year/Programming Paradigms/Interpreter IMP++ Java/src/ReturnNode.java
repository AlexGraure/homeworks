/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class ReturnNode extends Node{
	Node expr = null;
	
	public ReturnNode() {
		super();
	}
	
	@Override
	public void add(Node expr) {
		setExpr((ExprNode) expr);
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
