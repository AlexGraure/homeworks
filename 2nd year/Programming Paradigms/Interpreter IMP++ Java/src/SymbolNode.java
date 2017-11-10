/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class SymbolNode extends Node{
	String symbol;
	
	public SymbolNode() {
		super();
	}
	
	public SymbolNode(String symbol) {
		super();
		setSymbol(symbol);
	}
	
	@Override
	public Node accept(Visitor v) {
		return v.visit(this);
	}

	public String getString() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	
}
