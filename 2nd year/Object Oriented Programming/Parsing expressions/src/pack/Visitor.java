package pack;
/**
 * 
 * @author Graure Marius-Alexandru
 *	
 */
public interface Visitor {
	/**
	 * Metoda ce apeleaza o alta metoda visit
	 * in functie de tipurile de informatie ale 
	 * nodurilor
	 * @param a nod
	 * @param b nod
	 * @return un nod cu informatia obtinuta
	 */
	public Node visit(Node a, Node b);
	
	/**
	 * 
	 * @param a int
	 * @param b int
	 * @return nod nou cu informatia obtinuta
	 */
	public Node visit(int a, int b);
	
	/**
	 * 
	 * @param a int
	 * @param b double
	 * @return nod nou cu informatia obtinuta
	 */
	public Node visit(int a, double b);
	
	/**
	 * 
	 * @param a int
	 * @param b string
	 * @return nod nou cu informatia obtinuta
	 */
	public Node visit(int a, String b);
	
	/**
	 * 
	 * @param a double
	 * @param b int
	 * @return nod nou cu informatia obtinuta
	 */
	public Node visit(double a, int b);
	
	/**
	 * 
	 * @param a double
	 * @param b double
	 * @return nod nou cu informatia obtinuta
	 */
	public Node visit(double a, double b);
	
	/**
	 * 
	 * @param a double
	 * @param b string
	 * @return nod nou cu informatia obtinuta
	 */
	public Node visit(double a, String b);
	
	/**
	 * 
	 * @param a string
	 * @param b int
	 * @return nod nou cu informatia obtinuta
	 */
	public Node visit(String a, int b);
	
	/**
	 * 
	 * @param a string 
	 * @param b double
	 * @return nod nou cu informatia obtinuta
	 */
	public Node visit(String a, double b);
	
	/**
	 * 
	 * @param a string
	 * @param b string
	 * @return nod nou cu informatia obtinuta
	 */
	public Node visit(String a, String b);
}
