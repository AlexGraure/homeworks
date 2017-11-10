package pack;
/**
 * 
 * 	@author Graure Marius-Alexandru
 *	Clasa ce extinde clasa abstracta Node si contine informatie de tip Info
 */
public class OperandNode extends Node {
	
	public OperandNode(){
	}

	/**
	 * Constructor ce creeaza un nod nou in functie de parametrii primiti
	 * @param nodeName numele nodului
	 * @param valueType tipul valorii retinute
	 * @param hMap HashMap
	 */
	public OperandNode(String nodeName, String valueType, MAP hMap) {
		super();
		super.getInfo().setValueType(valueType);
		switch(valueType){
		case "int":
			super.getInfo().setValue(hMap.getIntMap().get(nodeName));
			break;
		case "double":
			super.getInfo().setValue(hMap.getDoubleMap().get(nodeName));
			break;
		case "string":
			super.getInfo().setValue(hMap.getStringMap().get(nodeName));
			break;
		}
	}
	
	/**
	 * Constructor ce creeaza un nod cu informatie de tip int
	 * @param valueType tipul valorii retinute
	 * @param nodeValue numele nodului
	 */
	public OperandNode(String valueType, int nodeValue){
		super.getInfo().setValueType(valueType);
		super.getInfo().setValue(nodeValue);
	}
	/**
	 * Constructor ce creeaza un nod cu informatie de tip double
	 * @param valueType tipul valorii retinute
	 * @param nodeValue numele nodului
	 */
	public OperandNode(String valueType, double nodeValue){
		super.getInfo().setValueType(valueType);
		super.getInfo().setValue(nodeValue);
	}
	/**
	 * Constructor ce creeaza un nod cu informatie de tip string
	 * @param valueType tipul valorii retinute
	 * @param nodeValue numele nodului
	 */
	public OperandNode(String valueType, String nodeValue){
		super.getInfo().setValueType(valueType);
		super.getInfo().setValue(nodeValue);
	}
	
}	
