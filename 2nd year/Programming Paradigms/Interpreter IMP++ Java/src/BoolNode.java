/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class BoolNode extends Node {
	Boolean cond = false;
	
	public BoolNode(Boolean cond) {
		super();
		this.cond = cond;
	}

	public Boolean getCond() {
		return cond;
	}

	public void setCond(Boolean cond) {
		this.cond = cond;
	}
	
	
}
