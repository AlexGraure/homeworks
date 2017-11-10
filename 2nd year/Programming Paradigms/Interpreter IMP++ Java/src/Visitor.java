/**
 * 
 * @author Graure Marius-Alexandru
 * 
 */
public interface Visitor {

	public Node visit(ValueNode node);

	public Node visit(SymbolNode node);

	public Node visit(SemicoloneNode node);

	public Node visit(ReturnNode node);

	public Node visit(AssertNode node);

	public Node visit(AssignmentNode node);

	public Node visit(ExprNode node);

	public Node visit(OpNode node);

	public Node visit(ForNode node);

	public Node visit(IfNode node);

	public Node visit(ProgNode node);
}
