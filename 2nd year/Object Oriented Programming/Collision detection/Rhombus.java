/**
 * 
 * 	@author Graure Marius-Alexandru
 *
 *	@see Methods
 *	@see GeometricObject
 */
public class Rhombus extends GeometricObject implements Methods{
	
	protected double ID, x1, y1, x2, y2, x3, y3, x4, y4;

	/**
	 * 
	 * Constructor.
	 * <br>
	 * Construieste un romb cu parametrii primiti.
	 * Apeleaza constructorul superclasei.
	 * 
	 * @param ID ID-ul rombului
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 */
	public Rhombus(double ID, double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		
		super(ID, x2, y1, x4, y3);
		this.setRmbID(ID);
		this.setRmbX1(x1);
		this.setRmbY1(y1);
		this.setRmbX2(x2);
		this.setRmbY2(y2);
		this.setRmbX3(x3);
		this.setRmbY3(y3);
		this.setRmbX4(x4);
		this.setRmbY4(y4);
	}
	
	
	/**
	 * 
	 * Constructor.
	 * <br>
	 * Construieste un romb cu parametrii primiti.
	 * Apeleaza constructorul superclasei.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 */
	public Rhombus(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		
		super(x2, y1, x4, y3);
		
		this.setRmbID(ID);
		this.setRmbX1(x1);
		this.setRmbY1(y1);
		this.setRmbX2(x2);
		this.setRmbY2(y2);
		this.setRmbX3(x3);
		this.setRmbY3(y3);
		this.setRmbX4(x4);
		this.setRmbY4(y4);
	}
	/**
	 * Implementeaza metoda din interfata {@link Methods}
	 * <br>
	 * Verifica daca punctul de coordonate x si y este
	 * continut de romb
	 * 
	 * @return true daca punctul este continut / false in caz contrar
	 */
	@Override
	public boolean containsPoint(double x, double y) {
		
		double A1, A2, A3, A4,  A, sum;
		
		A1 = getTriangleArea(this.getRmbX2(), this.getRmbY2(), x, y, this.getRmbX1(), this.getRmbY1());
		A2 = getTriangleArea(this.getRmbX1(), this.getRmbY1(), x, y, this.getRmbX4(), this.getRmbY4());
		A3 = getTriangleArea(this.getRmbX4(), this.getRmbY4(), x, y, this.getRmbX3(), this.getRmbY3());
		A4 = getTriangleArea(this.getRmbX3(), this.getRmbY3(), x, y, this.getRmbX2(), this.getRmbY2());
		
		A = getTriangleArea(this.getRmbX2(), this.getRmbY2(), this.getRmbX3(), this.getRmbY3(), this.getRmbX4(), this.getRmbY4());
		A += getTriangleArea(this.getRmbX4(), this.getRmbY4(), this.getRmbX1(), this.getRmbY1(), this.getRmbX2(), this.getRmbY2());
		sum = A1 + A2 + A3 + A4;
		if (A == sum) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Calculeaza aria triunghiului.
	 *
	 * @return Aria triunghiului determinat de parametrii primiti
	 */
	public double getTriangleArea(double x1, double y1, double x2, double y2, double x3, double y3){
		
		double A = 0;
		A = Math.abs((x1*(y2-y3) + x2*(y3 - y1) + x3*(y1 - y2)));
		
		return A;
	}

	public double getRmbID() {
		return ID;
	}

	public void setRmbID(double ID) {
		this.ID = ID;
	}

	public double getRmbX1() {
		return x1;
	}

	public void setRmbX1(double x1) {
		this.x1 = x1;
	}

	public double getRmbY1() {
		return y1;
	}

	public void setRmbY1(double y1) {
		this.y1 = y1;
	}

	public double getRmbX2() {
		return x2;
	}

	public void setRmbX2(double x2) {
		this.x2 = x2;
	}

	public double getRmbY2() {
		return y2;
	}

	public void setRmbY2(double y2) {
		this.y2 = y2;
	}

	public double getRmbX3() {
		return x3;
	}

	public void setRmbX3(double x3) {
		this.x3 = x3;
	}

	public double getRmbY3() {
		return y3;
	}

	public void setRmbY3(double y3) {
		this.y3 = y3;
	}

	public double getRmbX4() {
		return x4;
	}

	public void setRmbX4(double x4) {
		this.x4 = x4;
	}

	public double getRmbY4() {
		return y4;
	}

	public void setRmbY4(double y4) {
		this.y4 = y4;
	}
	
	
}
