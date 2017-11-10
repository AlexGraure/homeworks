/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class ValueNode extends Node{
	Integer value;
	
	public ValueNode() {
		super();
	}
	
	public ValueNode(Integer value) {
		super();
		setValue(value);
	}
	
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}

	public int getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}	
