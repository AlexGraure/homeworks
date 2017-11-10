/**
 * 
 * 	@author Graure Marius-Alexandru
 *
 *	@see Methods
 *	@see GeometricObject
 * 
 */
public class Circle extends GeometricObject implements Methods{

	protected double ID, R, x, y;
	/**
	 * Constructor
	 * <br>
	 * Construieste un cerc de folosind parametrii primiti.
	 * Apeleaza constructorul superclasei {@link GeometricObject}
	 * 
	 * @param ID este ID-ul cercului
	 * @param R este raza cercului
	 * @param x abscisa centrului de cerc de raza {@link #R}
	 * @param y ordonata centrului de cerc de raza {@link #R}
	 */
	public Circle(double ID, double R, double x, double y) {
		super(ID, x - R, y - R, x + R, y + R);
		this.setCircleID(ID);
		this.setR(R);
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Constructor
	 * <br>
	 * Construieste un cerc de folosind parametrii primiti.
	 * Apeleaza constructorul superclasei {@link GeometricObject}
	 * 
	 * @param R este raza cercului
	 * @param x abscisa centrului de cerc de raza {@link #R}
	 * @param y ordonata centrului de cerc de raza {@link #R}
	 */
	public Circle(double R, double x, double y) {
		super(x - R, y - R, x + R, y + R);

		this.setR(R);
		this.setX(x);
		this.setY(y);
	}
	/**
	 * 
	 * Verifica daca punctul determinat de parametrii  primiti, x si y, se afla in interiorul cercului.
	 * <p>
	 * Calculeaza distanta de la punct la centrul cenrcului. Daca distanta calculata este mai
	 *  mica sau egala decat raza la patrat, atunci returneaza true (false in caz contrar).
	 * 
	 * @return true sau false
	 */
	@Override
	public boolean containsPoint(double x, double y) {
		double distance = 0;
		distance = Math.pow(this.getX() - x,2) + Math.pow(this.getY() - y, 2);
		if(distance <= this.getR() * this.getR()){
			return true;
		}
		return false;
	}

	/**
	 * @return ID-ul cercului
	 */
	public double getCircleID() {
		return ID;
	} 

	/**
	 * 
	 * @param ID este setat
	 * 
	 */
	
	public void setCircleID(double ID) {
		this.ID = ID;
	}

	/**
	 * 
	 * @return Raza cercului
	 */
	
	public double getR() {
		return R;
	}
	
	/**
	 * Seteaza raza cercului.
	 * @param R raza cercului
	 */

	public void setR(double R) {
		this.R = R;
	}

	/**
	 * 
	 * @return abscisa centrului cercului
	 */
	
	public double getX() {
		return x;
	}

	/**
	 * Seteaza abscisa centrului cercului.
	 * @param x abscisa centrului cercului
	 */
	
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Returneaza ordonata centrului cercului.
	 * @return ordonata centrului cercului
	 */
	
	public double getY() {
		return y;
	}

	/**
	 * Seteaza ordonata centrului cercului.
	 * @param y ordonata centrului cercului
	 */
	
	public void setY(double y) {
		this.y = y;
	}
	
	

}