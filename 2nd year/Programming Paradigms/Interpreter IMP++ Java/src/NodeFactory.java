/**
 * 
 * @author Graure Marius-Alexandru
 * 
 */
public class NodeFactory {

	private static NodeFactory INSTANCE = null;

	private NodeFactory() {
	}

	public static NodeFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new NodeFactory();
		}
		return INSTANCE;
	}

	public Node createNode(String key) {
		switch (key) {
		case ";":
			return new SemicoloneNode();
		case "=":
			return new AssignmentNode();
		case "==":
			return new Equality();
		case "<":
			return new Inequality();
		case "*":
			return new Multiply();
		case "+":
			return new Plus();
		case "for":
			return new ForNode();
		case "if":
			return new IfNode();
		case "assert":
			return new AssertNode();
		case "return":
			return  new ReturnNode();
		default:
			if(isInteger(key) == true) {
				return new ValueNode(Integer.parseInt(key));
			} else {
				return new SymbolNode(key);
			}
		}

	}

	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}
