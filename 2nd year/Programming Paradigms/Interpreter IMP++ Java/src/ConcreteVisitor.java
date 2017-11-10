import java.util.HashMap;

/**
 * 
 * @author Graure Marius-Alexandru
 *
 */
public class ConcreteVisitor implements Visitor {
	HashMap<String, Integer> backup = new HashMap<String, Integer>();
	Node result = null;
	int checkFailed = 0;
	int missingReturn = 2;
	int assertFailed = 0;

	@Override
	public Node visit(ExprNode expr) {
		return expr.getNode().accept(this);
	}

	@Override
	public Node visit(OpNode op) {
		Node expr1 = op.getExpr1().accept(this);
		Node expr2 = op.getExpr2().accept(this);
		return Evaluate.Calculate(this, op, expr1, expr2);
	}

	@Override
	public Node visit(ValueNode node) {
		return node;
	}

	@Override
	public Node visit(SymbolNode node) {
		return node;
	}

	@Override
	public Node visit(ProgNode prog) {
		return prog.getNode().accept(this);
	}

	@Override
	public Node visit(ForNode For) {
		Node indexAssign = For.getAssign1().accept(this);
		Node cond = For.getExpr().accept(this);
		if (cond != null) {
			Boolean boolCond = ((BoolNode) cond).getCond();
			while (boolCond) {
				For.getProg().accept(this);
				For.getAssign2().accept(this);

				cond = For.getExpr().accept(this);
				boolCond = ((BoolNode) cond).getCond();
			}
		}
		this.backup.remove(((SymbolNode) indexAssign).getString());
		return null;
	}

	@Override
	public Node visit(IfNode condition) {
		Node bool = condition.getExpr().accept(this);
		if (((BoolNode) bool).getCond() == true) {
			condition.getProg1().accept(this);
		} else {
			condition.getProg2().accept(this);
		}
		return null;
	}

	@Override
	public Node visit(ReturnNode node) {
		Node result = node.getExpr().accept(this);
		if (result == null) {
			this.checkFailed = 3;
		} else {
			Integer value = this.backup.get(((SymbolNode) result).getString());
			this.result = new ValueNode(value);
		}
		this.missingReturn = 0;
		return null;
	}

	@Override
	public Node visit(AssertNode assertNode) {
		Node bool = assertNode.getExpr().accept(this);
		if (bool != null) {
			if (((BoolNode) bool).getCond() == false) {
				this.assertFailed = 1;
			}
		}
		return null;
	}

	@Override
	public Node visit(AssignmentNode assign) {
		Node symbol = assign.getSymbol().accept(this);
		Node expr = assign.getExpr().accept(this);
		if (expr instanceof SymbolNode) {
			if (expr != null) {
				int value = backup.get(((SymbolNode) (((ExprNode) expr).node)).getString());
				this.backup.put(((SymbolNode) symbol).getString(), value);
			}
		} else {
			if (expr != null) {
				int value = ((ValueNode) (expr)).getValue();
				this.backup.put(((SymbolNode) symbol).getString(), value);
			} else {
				this.checkFailed = 3;
			}
		}
		return symbol;
	}

	@Override
	public Node visit(SemicoloneNode node) {
		node.getProg1().accept(this);
		node.getProg2().accept(this);
		return null;
	}

}