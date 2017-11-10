/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class Inequality extends OpNode {
	
	public Inequality() {
		super();
	}
	
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}
	
	@Override
	public Node getResult(Integer a, Integer b) {
		return new BoolNode(a < b);
	}
}
