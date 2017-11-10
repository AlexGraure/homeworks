import java.util.ArrayList;

/**
 * 
 * 	@author Graure Marius-Alexandru
 *	Clasa ce descrie cadranele.
 */

public class Quadtree {

	private int Crt_Fig_Nr = 0;
	private int split = 0;
	private ArrayList<GeometricObject> figures = new ArrayList<GeometricObject>();
	
	private GeometricObject bounds;
	private Quadtree root;
	private Quadtree[] nodes;

	
	/**
	 * Constructor de cadran.
	 * 
	 * @param pBounds coordonate delimitatoare de tip {@link GeometricObject}
	 * @param n	numarul de fii
	 */
	public Quadtree(GeometricObject pBounds, int n) {
		this.setRoot(null);
		try {
			this.setBounds(new GeometricObject(pBounds.getX1(), pBounds.getY1(), pBounds.getX2(), pBounds.getY2()));
		} catch (NullPointerException ex) {
			System.out.println("Null");
		}
		this.nodes = new Quadtree[n];
	}
	
	/**
	 * 
	 * Insereaza recursiv in arbore.
	 * 
	 * @param qtree cadran primit ca parameru de tip {@link Quadtree}
	 * @param fig figura geometrica de tip {@link GeometricObject}
	 */
	
	public void Insert(Quadtree qtree, GeometricObject fig) {

		int OK = 0, collision = 0, i = 0, j = 0;

		OK = qtree.nodeCollision(qtree.getBounds(), fig);
		if (OK != 1)
			return;
		
		if (qtree.getCrt_Fig_Nr() == 0) {
			if (qtree.getSplit() == 0) {
				/**
				 * Daca nodul nu contine nicio figura
				 * si nu este impartit (split = 0), se
				 * insereaza in nodul curent.
				 */
				qtree.figures.add(fig);
				qtree.setCrt_Fig_Nr(qtree.getCrt_Fig_Nr() + 1);
				return;
			} else {
				/**
				 * Daca este impartit (split = 1), verifica recursiv fiecare nod
				 * al arborelui.
				 */
				for (i = 0; i < 4; i++) {
					Insert(qtree.nodes[i], fig);
				}
				return;
			}
		}
		/**
		 * Daca nodul contine figuri, se verifica daca exista coliziuni intre
		 * figurile prezente si cea care urmeaza a fi inserata.
		 */
		else {
				for (j = 0; j < qtree.getCrt_Fig_Nr(); j++) {
						collision = qtree.figCollision(qtree.figures.get(j), fig);
						if (collision == 1) {
							qtree.figures.add(fig);
							qtree.setCrt_Fig_Nr(qtree.getCrt_Fig_Nr() + 1);
							return;
						}
				}
				/**
				 * Daca nu se gasesc coliziuni se imparte nodul si
				 * se insereaza figurile geometrice in noile cadrane formate.
				 */
				qtree.split(qtree, qtree.getBounds());
				qtree.setSplit(1);
				qtree.changeQtree(qtree, fig);
				return;
		}
	}
	
	/**
	 * Rearanjeaza cadranul prezent.
	 * <br>
	 * Insereaza figurile deja prezente in nod in cadranele formate recent.
	 * 
	 * @param qtree cadran de tip {@link Quadtree}
	 * @param fig figura geometrica de tip {@link GeometricObject}
	 */
	
	private void changeQtree(Quadtree qtree, GeometricObject fig) {

		int i = 0, j = 0;
		
		ArrayList<GeometricObject> clone = new ArrayList<GeometricObject>(qtree.figures.size());
		for (i = 0; i < qtree.figures.size(); i++) {
	        clone.add(qtree.figures.get(i));
	    }
		qtree.figures.clear();
		
		for (i = 0; i < 4; i++) {
			qtree.getNodes()[i].setRoot(qtree);
			for (j = 0; j < qtree.getCrt_Fig_Nr(); j++) {
				Insert(qtree.getNodes()[i], clone.get(j));
			}
			Insert(qtree.getNodes()[i], fig);
		}
		qtree.setCrt_Fig_Nr(0);
	}
	
	/**
	 * 
	 * Verifica daca exista coliziune intre figurile geometrice 
	 * primite ca parametri.
	 * 
	 * @param fig1 figura deja prezenta de tip {@link GeometricObject}
	 * @param fig2 figura geometrica de tip {@link GeometricObject}
	 * @return 1 daca exista coliziune intre figuri si 0 in caz contrar
	 */
	
	private int figCollision(GeometricObject fig1, GeometricObject fig2) {

		if (fig2.getX2() < fig1.getX1())
			return 0;
		if (fig2.getY1() > fig1.getY2())
			return 0;
		if (fig2.getX1() > fig1.getX2())
			return 0;
		if (fig2.getY2() < fig1.getY1())
			return 0;

		return 1;
	}

	/**
	 * 
	 * @param node cadranul (nodul) de tip {@link Quadtree}
	 * @param fig figura geometrica de tip {@link GeometricObject}
	 * @return 1 daca exista coliziune intre cadran si figura
	 *  si 0 in caz contrar.
	 */
	
	private int nodeCollision(GeometricObject node, GeometricObject fig) {

		if (fig.getX2() < node.getX1())
			return 0;
		if (fig.getY1() > node.getY2())
			return 0;
		if (fig.getX1() > node.getX2())
			return 0;
		if (fig.getY2() < node.getY1())
			return 0;

		return 1;
	}
	
	/**
	 * 
	 * Realizeaza impartirea cadranului primit ca parametru
	 * in functie de coordonate
	 * 
	 * @param qtree cadran de tip {@link Quadtree}
	 * @param bounds coordonate delimitatoare de tip {@link GeometricObject}
	 */
	
	private void split(Quadtree qtree, GeometricObject bounds) {
		
		qtree.nodes[0] = new Quadtree(getBounds1(qtree.getBounds()), 4);
		qtree.nodes[1] = new Quadtree(getBounds2(qtree.getBounds()), 4);
		qtree.nodes[2] = new Quadtree(getBounds3(qtree.getBounds()), 4);
		qtree.nodes[3] = new Quadtree(getBounds4(qtree.getBounds()), 4);

	}

	/**
	 * 
	 * Seteaza coordonatele mini-cadranului din sud-vest
	 * 
	 * @param bounds coordonate delimitatoare ale cadranului initial
	 * @return Cadranul nou format
	 */
	
	private GeometricObject getBounds1(GeometricObject bounds) {

		GeometricObject frame = new GeometricObject();
		frame.setX1(bounds.getX1());
		frame.setY1(bounds.getY1());
		frame.setX2(bounds.getX1() + (bounds.getX2() - bounds.getX1()) / 2);
		frame.setY2(bounds.getY1() + (bounds.getY2() - bounds.getY1()) / 2);

		return frame;
	}

	/**
	 * 
	 * Seteaza coordonatele mini-cadranului din nord-vest
	 * 
	 * @param bounds coordonate delimitatoare ale cadranului initial
	 * @return Cadranul nou format
	 */
	
	private GeometricObject getBounds2(GeometricObject bounds) {

		GeometricObject frame = new GeometricObject();
		frame.setX1(bounds.getX1());
		frame.setY1(bounds.getY1() + (bounds.getY2() - bounds.getY1()) / 2);
		frame.setX2(bounds.getX1() + (bounds.getX2() - bounds.getX1()) / 2);
		frame.setY2(bounds.getY2());

		return frame;
	}
	
	/**
	 * 
	 * Seteaza coordonatele mini-cadranului din nord-est
	 * 
	 * @param bounds coordonate delimitatoare ale cadranului initial
	 * @return Cadranul nou format
	 */

	private GeometricObject getBounds3(GeometricObject bounds) {

		GeometricObject frame = new GeometricObject();
		frame.setX1(bounds.getX1() + (bounds.getX2() - bounds.getX1()) / 2);
		frame.setY1(bounds.getY1() + (bounds.getY2() - bounds.getY1()) / 2);
		frame.setX2(bounds.getX2());
		frame.setY2(bounds.getY2());

		return frame;
	}

	/**
	 * 
	 * Seteaza coordonatele mini-cadranului din sud-est
	 * 
	 * @param bounds coordonate delimitatoare ale cadranului initial
	 * @return Cadranul nou format
	 */
	
	private GeometricObject getBounds4(GeometricObject bounds) {

		GeometricObject frame = new GeometricObject();
		frame.setX1(bounds.getX1() + (bounds.getX2() - bounds.getX1()) / 2);
		frame.setY1(bounds.getY1());
		frame.setX2(bounds.getX2());
		frame.setY2(bounds.getY1() + (bounds.getY2() - bounds.getY1()) / 2);

		return frame;
	}
	
	/**
	 * 
	 * Verifica recursiv in ce figuri geometrice se gaseste
	 *  punctul (dat de x si y primiti) si adauga ID-ul in vectorul
	 *  de ID-uri
	 * 
	 * @param qtree arbore (cadran) de tip {@link Quadtree}
	 * @param x abscisa punctului
	 * @param y ordonata punctului
	 * @param IDs vector de ID-uri de figuri (double)
	 * @return 1 daca punctul este inclus intr-o figura (in mai multe)
	 *  si 0 in caz contrar
	 */
	
	public int PCol(Quadtree qtree, double x, double y, double[] IDs){

		/**
		 * 	Devine true daca vreuna din figurile din cadranul
		 *	prezent contine punctul
		 */
		boolean OK = false; 
		/**
		 * 	Devine 1 daca id-ul figurii din quadtree care contine
		 * 	punctul dat de coordonatele (x,y) este deja in vectorul de id-uri
		 */
		int found = 0;
		/**
		 *	Devine 1 daca s-au gasit id-urile figurilor care
		 *	contin punctul de coordonate (x,y)
		 */
		int contains = 0;
		/**
		 * Ia valoarea indexului primului element nul din vectorul
		 *de id-uri plus 1
		 */ 
		int index = 0; 
		
		/**
		 * Verifica daca punctul se afla in cadran
		 */
		OK = qtree.bounds.containsPoint(x, y);
		if(OK == false){
			return 0;
		}
		OK = false;
		/**
		 * Daca se afla in cadran, dar acesta nu are figuri, 
		 * inseamna ca se afla in fii sai(mini-cadrane)
		 */
		if(qtree.getCrt_Fig_Nr() == 0){
			if(qtree.getSplit() == 0){
				return 0;
			}
			/**
			 * Caut in mini-cadrane
			 */
			for(int i = 0; i < 4; i++){
				contains = PCol(qtree.getNodes()[i], x, y, IDs);
				if(contains == 1){
					return 1;
				}
			}
		}
		
		/**
		 * Daca cadranul are figuri, verific tipul figurii si daca contine punctul.
		 */
		
		else {
			for(int i = 0; i < qtree.getCrt_Fig_Nr(); i++){
				OK = false;
				if(qtree.figures.get(i) instanceof Rectangle)
					OK = ((Rectangle)qtree.figures.get(i)).containsPoint(x, y);
				else 	if(qtree.figures.get(i) instanceof Triangle)
					OK = ((Triangle)qtree.figures.get(i)).containsPoint(x, y);
				else	if(qtree.figures.get(i) instanceof Circle)
					OK = ((Circle)qtree.figures.get(i)).containsPoint(x, y);
				else	if(qtree.figures.get(i) instanceof Rhombus)
					OK = ((Rhombus)qtree.figures.get(i)).containsPoint(x, y);
				
				/**
				 * Daca punctul este continut de figura, adaug ID-ul in vectorul de ID-uri
				 */
				if(OK == true){
					found = 0;
					index = 0;
					for(int j = 0; j < IDs.length; j++){
						if(IDs[j] == qtree.figures.get(i).getID()){
							found = 1;
							break;
						}
						if(IDs[j] == 0){
							index = j;
							break;
						}
					}	
					if(found != 1){
						IDs[index] = qtree.figures.get(i).getID();
						contains = 1;
					}
				}
			}
			if(contains == 1){
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * Verifica daca exista coliziuni intre figurile
	 * deja prezente in arbore si figura geometrica T_FIG.
	 * <br>
	 * Adaug ID-urile figurilor din arbore care prezinta coliziune 
	 * in vectorul de ID-uri. 
	 * 
	 * @param qtree arbore (cadran) de tip {@link Quadtree}
	 * @param T_FIG figura geometrica de tip {@link GeometricObject}
	 * @param IDs vector de ID-uri (double)
	 */

	public void FCol(Quadtree qtree, GeometricObject T_FIG, double[] IDs){

		/**
		 * devine 1 daca id-ul figurii din quadtree care se afla
		 * in coliziune cu T_FIG se afla deja in vectorul de id-uri
		 */
		int found = 0; 
		/**
		 * devine 1 daca figura T_FIG se afla in cadran
		 */
		int col = 0; 
		/**ia valoarea indexului primului element nul din vectorul
		 *de id-uri plus 1
		 */
		int index = 0; 
		/**
		 * Verific daca figura ar apartine cadranului prezent
		 */
		col = qtree.nodeCollision(qtree.getBounds(), T_FIG);
		if(col != 1)
			return;

		if(qtree.getCrt_Fig_Nr() == 0){
			if(qtree.getSplit() == 0){
				return;
			}
			/**
			 * Daca se afla in cadran, dar acesta nu are figuri, 
			 * inseamna ca se afla in fii sai(mini-cadrane)
			 */
			for(int i = 0; i < 4; i++){
				FCol(qtree.getNodes()[i], T_FIG, IDs);
			}
			return;
		}
		/**
		 * Daca cadranul are figuri, verific posibilele coliziuni
		 */
		else {
			for(int i = 0; i < qtree.getCrt_Fig_Nr(); i++){
				/**
				 * Daca s-a gasit o coliziune, adaug ID-ul figurii
				 * "curente" in vectorul de ID-uri
				 */
				if (qtree.figCollision(qtree.figures.get(i), T_FIG) == 1) {
					found = 0;
					index = 0;
					for(int j = 0; j < IDs.length; j++){
						if(IDs[j] == qtree.figures.get(i).getID()){
							found = 1;
							break;
						}
						if(IDs[j] == 0){
							index = j;
							break;
						}
					}
					if(found != 1){
						IDs[index] = qtree.figures.get(i).getID();
					}
				}
			}
			return;
		}
	}
	
	/**
	 * 
	 * Sterge figura geometrica din arbore.
	 * <br>
	 * Cauta dupa coordonate recursiv in noduri si atunci cand 
	 * gaseste figura din arbore corespunzatoare ID-ului lui fig,
	 * o sterge. 
	 * 
	 * @param qtree arbore (cadran) de tip {@link Quadtree}
	 * @param fig figura geometrica de tip {@link GeometricObject}
	 */
	
	public void deleteID(Quadtree qtree, GeometricObject fig){
		int contains = 0;
		
		contains = qtree.nodeCollision(qtree.getBounds(), fig);
		if(contains != 1){
			return;
		}
		
		if(qtree.getCrt_Fig_Nr() == 0){
			if(qtree.getSplit() == 0){
				return;
			}
			for (int i = 0; i < 4; i++) {
				deleteID(qtree.getNodes()[i], fig);
			}
		}
		else {
			qtree.figures.remove(fig);
			qtree.setCrt_Fig_Nr(qtree.getCrt_Fig_Nr() - 1);
		}
		
	}

	/**
	 * 
	 * @return Radacina arborelui
	 */
	public Quadtree getRoot() {
		return root;
	}

	/**
	 * Seteaza radacina arborelui
	 * @param root radacina arborelui
	 */
	public void setRoot(Quadtree root) {
		this.root = root;
	}

	/**
	 * 
	 * @return un {@link GeometricObject} ce contine
	 * coordonatele cadranului
	 */
	public GeometricObject getBounds() {
		return bounds;
	}

	
	/**
	 * 
	 * Seteaza coordonatele cadranului
	 * 
	 * @param bounds un {@link GeometricObject} ce contine
	 * coordonatele cadranului
	 */
	public void setBounds(GeometricObject bounds) {
		this.bounds = bounds;
	}

	/**
	 * 
	 * @return valoare lui split (daca cadranul este impartit sau nu)
	 */
	public int getSplit() {
		return split;
	}

	/**
	 * Seteaza valaorea lui split (cadranul este impartit sau nu).
	 * @param split 
	 */
	public void setSplit(int split) {
		this.split = split;
	}

	/**
	 * 
	 * @return vectorul de mini-cadrane (noduri)
	 */
	public Quadtree[] getNodes() {
		return nodes;
	}

	/**
	 * Seteaza nodurile.
	 * @param nodes vector de mini-cadrane (noduri)
	 */
	public void setNodes(Quadtree[] nodes) {
		nodes = new Quadtree[4];
	}
	
	/**
	 * 
	 * @return numarul de figuri prezente in cadran
	 */
	public int getCrt_Fig_Nr() {
		return Crt_Fig_Nr;
	}

	/**
	 * Seteaza numarul de figuri din cadran
	 * @param crt_Fig_Nr numarul de figuri
	 */
	public void setCrt_Fig_Nr(int crt_Fig_Nr) {
		Crt_Fig_Nr = crt_Fig_Nr;
	}

	/**
	 * 
	 * @return un ArrayList ce va contine figurile din cadran
	 */
	public ArrayList<GeometricObject> getFigures() {
		return figures;
	}

	/**
	 * Seteaza ArrayList-ul de figuri geometrice
	 * @param figures ArrayList de figuri geometrice 
	 */
	public void setFigures(ArrayList<GeometricObject> figures) {
		this.figures = figures;
	}
	
}
