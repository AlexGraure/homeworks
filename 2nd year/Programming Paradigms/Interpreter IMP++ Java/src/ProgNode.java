/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class ProgNode extends Node {
	Node node = null;

	public ProgNode(IfNode ifNode) {
		super();
		setNode(ifNode);
	}
	
	public ProgNode(ForNode forNode) {
		super();
		setNode(forNode);
	}
	
	public ProgNode(AssertNode assertNode) {
		super();
		setNode(assertNode);
	}
	
	public ProgNode(SemicoloneNode semiNode) {
		super();
		setNode(semiNode);
	}

	public ProgNode(ReturnNode retNode) {
		super();
		setNode(retNode);
	}
	
	public ProgNode(AssignmentNode assignNode) {
		super();
		setNode(assignNode);
	}
	
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}
	
	
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
	

}
