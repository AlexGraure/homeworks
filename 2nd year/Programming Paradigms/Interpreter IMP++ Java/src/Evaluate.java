
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 
 * @author Graure Marius-Alexandru Clasa ce contine metoda de evaluare a unei
 *         expresii
 */
public class Evaluate {

	private static Evaluate INSTANCE = null;

	private Evaluate() {

	}

	public static Evaluate getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Evaluate();
		}
		return INSTANCE;
	}

	public void eval(String[] tokens, PrintWriter out) {
		Stack<Node> stack = new Stack<>();
		LinkedList<String> queue = new LinkedList<String>();
		queue = getQueue(tokens);
		queue = Reverse(queue);

		String op = null;
		Node tree = null;
		while (!queue.isEmpty()) {
			tree = null;
			op = queue.poll();
			tree = NodeFactory.getInstance().createNode(op);
			tree = getNode(tree, stack);
			stack.push(tree);
		}
		
		tree = stack.pop();
		Visitor v = new ConcreteVisitor();
		tree.accept(v);
		printResult(v, out);
		return;
	}

	private void printResult(Visitor v, PrintWriter out) {
		ArrayList<Integer> errors = new ArrayList<Integer>();
		errors.add(((ConcreteVisitor) v).checkFailed);
		errors.add(((ConcreteVisitor) v).missingReturn);
		errors.add(((ConcreteVisitor) v).assertFailed);
		if(noError(errors) == true) {
			print(((ConcreteVisitor)v).result, out);
		} else {
			int index = getMaxErrorIndex(errors);
			switch(index) {
			case 0:
				out.print("Check failed");
				break;
			case 1:
				out.print("Missing return");
				break;
			case 2:
				out.print("Assert failed");
				break;
			}
		}
	}

	private int getMaxErrorIndex(ArrayList<Integer> errors) {
		int max = 0;
		if(errors.get(1) > errors.get(max)) {
			max = 1;
		}
		if(errors.get(2) > errors.get(max)) {
			max = 2;
		}
		return max;
	}

	private void print(Node result, PrintWriter out) {
		out.print(((ValueNode)result).getValue());
	}

	private boolean noError(ArrayList<Integer> errors) {
		for (int i = 0; i < errors.size(); i++) {
			if(errors.get(i) != 0) {
				return false;
			}
		}
		return true;
	}

	private Node getNode(Node node, Stack<Node> stack) {
		Node symbol = null;
		Node assign1 = null;
		Node assign2 = null;
		Node expr1 = null;
		Node expr2 = null;
		Node prog1 = null;
		Node prog2 = null;
		
		if (node instanceof ForNode) {
			assign1 = stack.pop();
			expr1 = stack.pop();
			assign2 = stack.pop();
			prog1 = stack.pop();
			node.add(assign1, expr1, assign2, prog1);
			return new ProgNode((ForNode) node);
		} else {
			if (node instanceof IfNode) {
				expr1 = stack.pop();
				prog1 = stack.pop();
				prog2 = stack.pop();
				node.add(expr1, prog1, prog2);
				return new ProgNode((IfNode) node);
			} else {
				if (node instanceof AssertNode) {
					expr1 = stack.pop();
					node.add(expr1);
					return new ProgNode((AssertNode) node);
				} else {
					if (node instanceof SemicoloneNode) {
						prog1 = stack.pop();
						prog2 = stack.pop();
						node.add(prog1, prog2);
						return new ProgNode((SemicoloneNode) node);
					} else {
						if (node instanceof ReturnNode) {
							expr1 = stack.pop();
							node.add(expr1);
							return new ProgNode((ReturnNode) node);
						} else {
							if (node instanceof AssignmentNode) {
								expr1 = stack.pop();
								symbol = stack.pop();
								node.add(expr1, symbol);
								return new ProgNode((AssignmentNode) node);
							} else {
								if(node instanceof OpNode) {
									expr1 = stack.pop();
									expr2 = stack.pop();
									node.add(expr1, expr2);
									return new ExprNode((OpNode) node);
								} else {
									if(node instanceof SymbolNode) {
										return new ExprNode((SymbolNode) node);
									} else {
										if(node instanceof ValueNode) {
											return new ExprNode((ValueNode) node);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	private LinkedList<String> Reverse(LinkedList<String> queue) {
		LinkedList<String> reversedQueue = new LinkedList<String>();
		while (!queue.isEmpty()) {
			String op = queue.removeLast();
			reversedQueue.addLast(op);
		}
		return reversedQueue;
	}

	private LinkedList<String> getQueue(String[] tokens) {
		LinkedList<String> queue = new LinkedList<>();
		for (int i = 1; i < tokens.length; i++) {
			queue.add(tokens[i]);
		}
		return queue;
	}

	public static Node Calculate(ConcreteVisitor v, OpNode op, Node expr1, Node expr2) {
		if(expr1 instanceof SymbolNode) {
			Integer a = v.backup.get((((SymbolNode)expr1).getString()));
			if(a == null) { 
				v.checkFailed = 3;
				return null;
			}
			if(expr2 instanceof SymbolNode) {
				Integer b = v.backup.get((((SymbolNode)expr2).getString()));
				if(b == null) { 
					v.checkFailed = 3;
					return null;
				}
				return op.getResult(a, b);
			} else {
				if(expr2 instanceof ValueNode) {
					Integer b = ((ValueNode)expr2).getValue();
					return op.getResult(a, b);
				}
			}
		} else {
			if(expr1 instanceof ValueNode) {
				Integer a = ((ValueNode)expr1).getValue();
				if(expr2 instanceof SymbolNode) {
					Integer b = v.backup.get((((SymbolNode)expr2).getString()));
					if(b == null) { 
						v.checkFailed = 3;
						return null;
					}
					return op.getResult(a, b);
				} else {
					if(expr2 instanceof ValueNode) {
						Integer b = ((ValueNode)expr2).getValue();
						return op.getResult(a, b);
					}
				}
			}
		}
		return null;
	}

}
