/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class Equality extends OpNode {

	public Equality() {
		super();
	}
	
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}

	@Override
	public Node getResult(Integer a, Integer b) {
		return new BoolNode(a == b);
	}
	
	
}
