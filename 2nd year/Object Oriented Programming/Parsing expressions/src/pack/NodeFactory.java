package pack;
/**
 * 
 * 	@author Graure Marius-Alexandru
 * 	 
 */
public class NodeFactory {

	private static NodeFactory INSTANCE = null;
	/**
	 *Constructor privat 
	 */
	private NodeFactory() {
	}
	/**
	 * Metoda statica
	 * @return o instanta pentru NodFactory
	 */
	public static NodeFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new NodeFactory();
		}
		return INSTANCE;
	}
	/**
	 * Metoda ce intoarce un nod nou in functie de tipul nodului
	 * @param nodeName numele nodului
	 * @param hMap HashMap cu toate variabilele
	 * @return un nod nou in functie de tipul nodului
	 */
	public Node createNode(String nodeName, MAP hMap) {
		if(nodeName.equals("+") || nodeName.equals("-") || nodeName.equals("/") || nodeName.equals("*")){
			return new OperatorNode(nodeName);
		}
		else {
			String valueType = hMap.getMap().get(nodeName);
			return new OperandNode(nodeName, valueType,  hMap);
		}
	}
	/**
	 * Metoda ce apeleaza constructorul OperandNode
	 * @param value 
	 * @return un nod nou cu informatie de tip int
	 */
	public Node createNode(int value) {
		return new OperandNode("int", value);
	}
	/**
	 * Metoda ce apeleaza constructorul OperandNode
	 * @param value 
	 * @return un nod nou cu informatie de tip double
	 */
	public Node createNode(double value) {
		return new OperandNode("double", value);
	}
	/**
	 * Metoda ce apeleaza constructorul OperandNode
	 * @param value 
	 * @return un nod nou cu informatie de tip string
	 */
	public Node createNode(String value) {
		return new OperandNode("string", value);
	}
}
