package pack;
/**
 * 
 * 	@author Graure Marius-Alexandru
 * Clasa folosita drept informatie pentru noduri.
 * Retine un int sau un double sau un string.
 */
public class Info {
	private String valueType;
	private int intValue;
	private double doubleValue;
	private String stringValue;
	
	public Info(){
		
	}

	public void setValue(int value){
		this.intValue = value;
	}
	
	public void setValue(double value){
		this.doubleValue = value;
	}
	
	public void setValue(String value){
		this.stringValue = value;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	public int getIntValue(){
		return intValue;
	}
	
	public double getDoubleValue(){
		return doubleValue;
	}
	
	public String getStringValue(){
		return stringValue;
	}
	/**
	 * Verifica daca int-ul primit este 0
	 * @param number numar de tip int
	 * @return true daca number este 0 / false in caz contrar
	 */
	public static boolean isZero(int number) {
		if(number == 0)
			return true;
		return false;
	}
	/**
	 * Verifica daca double-ul primit este 0
	 * @param number numar de tip double
	 * @return true daca number este 0 / false in caz contrar
	 */
	public static boolean isZero(double number) {
		if(number == 0)
			return true;
		return false;
	}
}
