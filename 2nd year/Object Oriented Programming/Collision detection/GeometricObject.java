/**
 * 
 * 	@author Graure Marius-Alexandru
 *
 *	@see Methods
 * 
 */

public class GeometricObject implements Methods{

	private double x1, y1, x2, y2, ID;
	/**
	 * Constructor
	 */
	public GeometricObject(){
	
	}
	/**
	 * 
	 * Constructor. 
	 * <br>
	 * Construieste un dreptunghi cu parametrii primiti.
	 * 
	 * @param ID este ID-ul obiectului geometric 
	 * @param x1 x-ul minim
	 * @param y1 y-ul minim
	 * @param x2 x-ul maxim
	 * @param y2 y-ul maxim
	 */
	public GeometricObject(double ID, double x1, double y1, double x2, double y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.ID = ID;
	}
	
	/**
	 * 
	 * Constructor.
	 * <br>
	 * Construieste un dreptunghi cu parametrii primiti.
	 * 
	 * @param x1 x-ul minim
	 * @param y1 y-ul minim
	 * @param x2 x-ul maxim
	 * @param y2 y-ul maxim
	 */
	
	public GeometricObject(double x1, double y1, double x2, double y2){
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	
	/**
	 * Implementeaza metoda din interfata {@link Methods}
	 * <br>
	 * Verifica daca punctul determinat de parametrii  primiti, x si y, 
	 * se afla in interiorul dreptunghiului.
	 * 
	 * @return true sau false
	 */
	@Override
	public boolean containsPoint(double x, double y) {
		if (x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return ID-ul dreptunghiului
	 */
	
	public double getID() {
		return this.ID;
	}
	
	/**
	 * Seteaza ID-ul
	 * @param ID este setat
	 */
	public void setID(double ID) {
		this.ID = ID;
	}
	
	/**
	 * 
	 * @return x1
	 */
	public double getX1() {
		return x1;
	}
	
	/**
	 * Seteaza abscisa
	 * @param x1 abscisa minima
	 */
	public void setX1(double x1) {
		this.x1 = x1;
	}

	/**
	 * 
	 * @return y1
	 */
	public double getY1() {
		return y1;
	}

	/**
	 * 
	 * @param y1 este setat
	 */
	public void setY1(double y1) {
		this.y1 = y1;
	}

	/**
	 * 
	 * @return x2
	 */
	public double getX2() {
		return x2;
	}

	/**
	 * 
	 * @param x2 este setat
	 */
	public void setX2(double x2) {
		this.x2 = x2;
	}
	
	/**
	 * 
	 * @return y2
	 */
	public double getY2() {
		return y2;
	}

	/**
	 * 
	 * @param y2 este setat
	 */
	public void setY2(double y2) {
		this.y2 = y2;
	}
}
