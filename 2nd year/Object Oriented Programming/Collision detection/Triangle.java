/**
 * 
 * 	@author Graure Marius-Alexandru

 *	@see Methods
 *	@see GeometricObject
 */

public class Triangle extends GeometricObject implements Methods{
	
	protected double ID, x1, y1, x2, y2, x3, y3;
	
	/**
	 * /**
	 * 
	 * Constructor.
	 * <br>
	 * Construieste un tringhi cu parametrii primiti.
	 * Apeleaza constructorul superclasei.
	 * 
	 * @param ID
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 */
	public Triangle(double ID, double x1, double y1, double x2, double y2, double x3, double y3) {
		super(ID, Math.min(x1, x2), y3, Math.max(x1, x3), y1);
		this.setTrID(ID);
		this.setTrX1(x1);
		this.setTrY1(y1);
		this.setTrX2(x2);
		this.setTrY2(y2);
		this.setTrX3(x3);
		this.setTrY3(y3);
	}
	
	/**
	 * /**
	 * 
	 * Constructor.
	 * <br>
	 * Construieste un tringhi cu parametrii primiti.
	 * Apeleaza constructorul superclasei.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 */
	public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		super(Math.min(x1, x2), y3, Math.max(x1, x3), y1);

		this.setTrX1(x1);
		this.setTrY1(y1);
		this.setTrX2(x2);
		this.setTrY2(y2);
		this.setTrX3(x3);
		this.setTrY3(y3);
	}

	/**
	 * Implementeaza metoda din interfata {@link Methods}
	 * <br>
	 * Verifica daca punctul de coordonate x si y este
	 * continut de triunghi
	 * 
	 * @return true daca punctul este continut / false in caz contrar
	 */
	@Override
	public boolean containsPoint(double x, double y) {
		
		double A, A1, A2, A3, sum;
		
		A = getTriangleArea(this.getTrX2(), this.getTrY2(), this.getTrX1(), this.getTrY1(), this.getTrX3(), this.getTrY3());
		
		A1 = getTriangleArea(this.getTrX2(), this.getTrY2(), x, y, this.getTrX3(), this.getTrY3());
		A2 = getTriangleArea(this.getTrX3(), this.getTrY3(), x, y, this.getTrX1(), this.getTrY1());
		A3 = getTriangleArea(this.getTrX1(), this.getTrY1(), x, y, this.getTrX2(), this.getTrY2());
		sum = A1 + A2 + A3;
		
		if(A == sum){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Calculeaza aria triunghiului.
	 ** @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @return Aria triunghiului determinat de parametrii primiti
	 */
	public double getTriangleArea(double x1, double y1, double x2, double y2, double x3, double y3){
		
		double A = 0;
		A = Math.abs((x1*(y2-y3) + x2*(y3 - y1) + x3*(y1 - y2)));
		
		return A;
	}

	public double getTrID() {
		return ID;
	}

	public void setTrID(double ID) {
		this.ID = ID;
	}

	public double getTrX1() {
		return x1;
	}

	public void setTrX1(double x1) {
		this.x1 = x1;
	}

	public double getTrY1() {
		return y1;
	}

	public void setTrY1(double y1) {
		this.y1 = y1;
	}

	public double getTrX2() {
		return x2;
	}

	public void setTrX2(double x2) {
		this.x2 = x2;
	}

	public double getTrY2() {
		return y2;
	}

	public void setTrY2(double y2) {
		this.y2 = y2;
	}

	public double getTrX3() {
		return x3;
	}

	public void setTrX3(double x3) {
		this.x3 = x3;
	}

	public double getTrY3() {
		return y3;
	}

	public void setTrY3(double y3) {
		this.y3 = y3;
	}
	
	
	
}
