/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class Plus extends OpNode {
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}
	
	public Plus() {
		super();
	}

	@Override
	public Node getResult(Integer a, Integer b) {
		Node node = new ValueNode(a + b);
		return node;
	}
	
	

}
