/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public interface Visitable {
	/**
	 * Aceasta metoda accepta un Visitor ce aplica metoda visit pe nodul curent
	 * 
	 * @param visitor
	 *            Visitor (ConcreteVisitor)
	 * @return un OperandNode
	 */
	public Node accept(Visitor visitor);
	
}
