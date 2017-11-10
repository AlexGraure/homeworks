package pack;
/**
 * 
 * 	@author Graure Marius-Alexandru
 *	Clasa ce extinde clasa abstracta Node si are un visitor
 */
public class OperatorNode extends Node{
	/**
	 * Constructor
	 * @param type tipul operatorului
	 */
	public OperatorNode(String type){
		super();
		switch(type){
		case "+":
			visitor = new Plus();
			break;
		case "-":
			visitor = new Minus();
			break;
		case "*":
			visitor = new Multiply();
			break;
		case "/":
			visitor = new Divide();
			break;
		}
	}

}
