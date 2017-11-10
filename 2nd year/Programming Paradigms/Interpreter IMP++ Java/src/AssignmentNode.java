/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class AssignmentNode extends Node {
	
	Node symbol = null;
	Node expr = null;
	
	public AssignmentNode() {
		super();
	}
	
	@Override
	public void add(Node symbol, Node expr) {
		setSymbol((ExprNode) symbol);
		setExpr((ExprNode) expr);
	}
	
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}

	public Node getSymbol() {
		return symbol;
	}

	public void setSymbol(ExprNode symbol) {
		this.symbol = symbol;
	}

	public Node getExpr() {
		return expr;
	}

	public void setExpr(ExprNode expr) {
		this.expr = expr;
	}

}
