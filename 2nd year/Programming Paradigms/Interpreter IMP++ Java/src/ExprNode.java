/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class ExprNode extends Node{
	Node node = null;
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}
	
	public ExprNode(SymbolNode symbol) {
		super();
		setSNode(symbol);
	}
	
	public ExprNode(ValueNode value) {
		super();
		setVNode(value);
	}
	
	public ExprNode(OpNode op) {
		super();
		setOpNode(op);
	}

	public Node getNode() {
		return node;
	}

	
	public void setSNode(SymbolNode symbol) {
		this.node = symbol;
	}
	
	public void setVNode(ValueNode value) {
		this.node = value;
	}
	
	public void setOpNode(OpNode op) {
		this.node = op;
	}
	
	
}
