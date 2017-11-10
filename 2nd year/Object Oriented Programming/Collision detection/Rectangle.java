/**
 * 
 * 	@author Graure Marius-Alexandru
 *
 *	@see Methods
 *	@see GeometricObject
 * 
 */

public class Rectangle extends GeometricObject implements Methods{

	protected double ID, x1, y1, x2, y2;
	
	/**
	 * 
	 * Constructor
	 * <br>
	 * Construieste un dreptunghi folosind parametrii primiti.
	 * Apeleaza constructorul superclasei {@link GeometricObject}
	 * 
	 * @param ID este ID-ul dreptunghiului
	 * @param x1 abscisa minima
	 * @param y1 ordonata minima
	 * @param x2 abscisa maxima
	 * @param y2 ordonata maxima
	 */
	public Rectangle(double ID, double x1, double y1, double x2, double y2) {
		super(ID, x1, y1, x2, y2);
		this.setRectID(ID);
		this.setRectX1(x1);
		this.setRectY1(y1);
		this.setRectX2(x2);
		this.setRectY2(y2);
	}
	
	/**
	 * 
	 * Constructor
	 * <br>
	 * Construieste un dreptunghi folosind parametrii primiti.
	 * Apeleaza constructorul superclasei {@link GeometricObject}
	 * 
	 * @param x1 abscisa minima
	 * @param y1 ordonata minima
	 * @param x2 abscisa maxima
	 * @param y2 ordonata maxima
	 */
	public Rectangle(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
		this.setRectID(ID);
		this.setRectX1(x1);
		this.setRectY1(y1);
		this.setRectX2(x2);
		this.setRectY2(y2);
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
		if (x >= this.getRectX1() && x <= this.getRectX2() && y >= this.getRectY1() && y <= this.getRectY2()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return ID-ul dreptunghiului
	 */
	public double getRectID() {
		return ID;
	}
	
	/**
	 * Seteaza ID-ul
	 * @param ID este setat
	 */
	public void setRectID(double ID) {
		this.ID = ID;
	}

	/**
	 * 
	 * @return x1
	 */
	public double getRectX1() {
		return x1;
	}

	/**
	 * Seteaza abscisa
	 * @param x1 abscisa minima
	 */
	public void setRectX1(double x1) {
		this.x1 = x1;
	}

	/**
	 * 
	 * @return y1
	 */
	public double getRectY1() {
		return y1;
	}

	/**
	 * 
	 * @param y1 este setat
	 */
	public void setRectY1(double y1) {
		this.y1 = y1;
	}

	/**
	 * 
	 * @return x2
	 */
	public double getRectX2() {
		return x2;
	}
	
	/**
	 * 
	 * @param x2 este setat
	 */
	public void setRectX2(double x2) {
		this.x2 = x2;
	}

	/**
	 * 
	 * @return y2
	 */
	public double getRectY2() {
		return y2;
	}

	/**
	 * 
	 * @param y2 este setat
	 */
	public void setRectY2(double y2) {
		this.y2 = y2;
	}
	
	
	
}

