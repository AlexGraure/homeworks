/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class Multiply extends OpNode {
	
	public Multiply() {
		super();
	}
	
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}

	@Override
	public Node getResult(Integer a, Integer b) {
		Node node = new ValueNode(a * b);
		return node;
	}
	
}
