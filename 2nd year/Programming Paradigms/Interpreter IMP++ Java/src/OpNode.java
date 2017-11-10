/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class OpNode extends Node{
	Node expr1 = null;
	Node expr2 = null;
	
	public OpNode() {
		super();
	}
	
	@Override
	public void add(Node expr1, Node expr2) {
		setExpr1((ExprNode) expr1);
		setExpr2((ExprNode) expr2);
	}
	
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}
	
	public Node getExpr1() {
		return expr1;
	}

	public void setExpr1(ExprNode expr1) {
		this.expr1 = expr1;
	}

	public Node getExpr2() {
		return expr2;
	}

	public void setExpr2(ExprNode expr2) {
		this.expr2 = expr2;
	}
	
	public Node getResult(Integer a, Integer b) {
		return null;
	}
	
}
