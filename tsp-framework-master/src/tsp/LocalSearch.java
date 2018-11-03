package tsp;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Cette classe permet d'appliquer à un chemin un algorithme de recherche locale utilisant du swap
 */

public class LocalSearch {
	private Instance instance;
	private int[] solution; // permet de stocker la solution au bout d'un cycle de swap
	private int[] temp; // permet de stocker la solution temporaire
	private int[] ini; // permet uniquement de comparer si la solution optimale a évoluer au cours d'une itération
	private double gain; // permet de mesurer le gain de distance (ou la perte) entre deux chemins
	
	// on stop l'algo lorsque la solution ne s'améliore pas au bout d'une itération

	/**
	 * Créer une instance de LocalSearch, on génère aléatoirement un chemin que l'on stock dans solution et dans ini
	 * C'est sur cet objet qu'on va effectuer les différentes opérations
	 * @param instance contenant toutes les informations du problème
	 * @throws Exception
	 */
	public LocalSearch(Instance instance) throws Exception {
		this.instance = instance;
		this.temp= new int[this.instance.getNbCities()];
		this.solution= new int[this.instance.getNbCities()];
		this.ini=new int[this.instance.getNbCities()];
		ArrayList<Integer> sol= new ArrayList<Integer>();	
		for(int i=0;i<instance.getNbCities();i++) {
			sol.add(i);
		}
		Collections.shuffle(sol);
		for(int j=0;j<instance.getNbCities();j++) {
			this.ini[j]=sol.get(j);
			this.solution[j]=sol.get(j);
		}
		this.gain=0;
		
	}
	
	/**
	 * Créer une instance de LocalSearch, cette fois-ci le chemin initial 
	 * est donné en entrée et non pas générer aléatoirement
	 * @param instance contenant toutes les données du problème
	 * @param ini : chemin initial choisi arbitrairement
	 * @throws Exception
	 */
	public LocalSearch(Instance instance, ArrayList<Integer> ini) throws Exception {
		this.instance = instance;
		this.temp= new int[this.instance.getNbCities()];
		this.solution= new int[this.instance.getNbCities()];
		this.ini=new int[this.instance.getNbCities()];
		for (int i=0;i<instance.getNbCities();i++) {
			this.ini[i]=ini.get(i);
			this.solution[i]=ini.get(i);
		}
		this.gain=0;
	}
	
	/**
	 * Effectue un swap sur un tableau d'entiers entre deux élements d'index différents
	 * @param tab : tableau d'entiers
	 * @param i 
	 * @param j
	 */
	public void swap(int[] tab, int i,int j) {
		int a=tab[i];
		int b=tab[j];
		tab[i]=b;
		tab[j]=a;
	}
	
	/**
	 * @return l'instance du problème
	 */
	public Instance getInstance() {
		return this.instance;
	}
	
	/**
	 * @return this.solution : le meilleur chemin actuel
	 */
	public int[] getSolution() {
		return this.solution;
	}
	
	/**
	 * Permet de définir la variable d'instance 'solution' sur le tableau d'entiers en entrée
	 * @param tab
	 */
	public void setSolution(int[] tab) {
		for (int i=0;i<tab.length;i++) {
			this.solution[i]=tab[i];
		}
	}
	
	/**
	 * Permet de donner à l'élement d'index 'index' de 'solution' la valeur 'val'
	 * @param index
	 * @param val
	 */
	public void setSolution(int index, int val) {
		this.solution[index]=val;
	}
	
	/**
	 * Permet de réaliser un swap entre 2 index sur la variable d'instance 'solution'
	 * @param i1
	 * @param i2
	 */
	public void swapSolution(int i1,int i2) {
		this.swap(this.solution, i1, i2);
	}
	
	/**
	 * Retourne le chemin temporaire sur lequel on effectue les différentes opérations
	 * @return un tableau d'entiers
	 */
	public int[] getTemp() {
		return this.temp;
	}
	
	/**
	 * Permet de définir la variable d'instance 'temp' avec le tableau d'entiers en entrée
	 * @param tab
	 */
	public void setTemp(int[] tab) {
		for (int i=0;i<tab.length;i++) {
			this.temp[i]=tab[i];
		}
	}
	
	/**
	 * Retourne le chemin initial de l'itération actuelle
	 * @return un tableau d'entiers
	 */
	public int[] getIni() {
		return this.ini;
	}
	
	/**
	 * Permet de définir la variable d'instance 'ini' avec le tableau d'entiers en entrée
	 * @param tab
	 */
	public void setIni(int[] tab) {
		for (int i=0;i<tab.length;i++) {
			this.ini[i]=tab[i];
		}
	}
	
	/**
	 * Retourne le gain actuel de l'instance
	 * @return un double
	 */
	public double getGain() {
		return this.gain;
	}
	
	/**
	 * Définit le gain sur 'g'
	 * @param g
	 */
	public void setGain(double g) {
		this.gain=g;
	}
	
	/**
	 * Calcule la distance totale du chemin representé par le tableau d'entiers en entrée
	 * @param tab
	 * @return un double
	 * @throws Exception
	 */
	public double distance(int[] tab) throws Exception {
		double d=0;
		for (int i=0;i<tab.length-1;i++) {
			d=d+this.instance.getDistances(tab[i],tab[i+1]);
		}
		d+=this.instance.getDistances(tab[0], tab[tab.length-1]);
		return d;
	}
	
	/**
	 * Retourne un String représentant le chemin de 'tab'
	 * @param tab
	 * @return
	 */
	public String tostring(int[] tab) {
		String stringsol="";
		for(int t=0;t<tab.length;t++) {
				stringsol+=" "+tab[t];
		}
		return stringsol;
	}

	/**
	 * Réalise un cycle de swap sur l'élement d'index 'index'
	 * On effectue les opérations sur 'temp' défini au préalable avec la variable d'instance 'ini'
	 * Si le chemin s'améliore on change 'solution'
	 * @param index
	 * @throws Exception
	 */
	public void swapCycle(int index) throws Exception { 
		int n = this.getInstance().getNbCities();

		for (int i=index+1;i<this.getInstance().getNbCities();i++) {
			this.setTemp(this.getIni());
			
			double g1=this.getInstance().getDistances(this.getIni()[(n+index-1)%n],this.getIni()[index])
					+this.getInstance().getDistances(this.getIni()[index], this.getIni()[(index+1)%n]);
			double d1=this.getInstance().getDistances(this.getIni()[(n+i-1)%n],this.getIni()[i])
					+this.getInstance().getDistances(this.getIni()[i], this.getIni()[(i+1)%n]);
			//distance au voisinage des points encore non swapés
			
			this.swap(this.temp, i, index);

			double g2=this.getInstance().getDistances(this.getTemp()[(n+index-1)%n],this.getTemp()[index])
					+this.getInstance().getDistances(this.getTemp()[index], this.getTemp()[(index+1)%n]);
			double d2=this.getInstance().getDistances(this.getTemp()[(n+i-1)%n],this.getTemp()[i])
					+this.getInstance().getDistances(this.getTemp()[i], this.getTemp()[(i+1)%n]);
			//distance au voisinage des points swapés		
			
			//if (this.distance(this.getTemp())<this.distance(this.getSolution())) {
			if (g1+d1-g2-d2>this.getGain()) { 
			//plus la somme est importante plus g2 et d2 son petits donc plus la distance de la nvlle sol est courte
				this.setSolution(this.getTemp());
				this.setGain(g1+d1-g2-d2);
			}
			

		}
	}
	
	/**
	 * Réalise une itération complète de swap
	 * @throws Exception
	 */
	public void swapIteration() throws Exception {
		for (int i=0; i<this.getInstance().getNbCities();i++) {
			this.swapCycle(i);
		}		
	}

	/**
	 * Utiliser après la fin d'une itération
	 * On définit 'ini' avec le meilleur chemin de l'itération précédente 
	 */
	public void setInitial() {
		this.setIni(this.getSolution());
	}
	
	/**
	 * 	On vérifie que pendant une itération la solution finale de l'itération à changer de la solution initiale
	 * @return
	 * @throws Exception
	 */
	public boolean iniEqualsSol() throws Exception { 
		return (this.distance(this.getIni())==this.distance(this.getSolution()));
	}
	
	public static void main(String[] args) throws Exception {
		boolean testEgalite=true; 
		long t0;
		long t1=0;
		int k=0;
		t0=System.currentTimeMillis();
		Instance graph = new Instance("instances/rat575.tsp",0);
		LocalSearch ls = new LocalSearch(graph);
		System.err.println("Solution initiale : "+ls.tostring(ls.getIni()));
		System.err.println("Distance initiale : "+ls.distance(ls.getIni()));
		
		do {
			ls.swapIteration();
			ls.setGain(0);
			System.err.println(ls.distance(ls.getSolution()));
			
			if (ls.iniEqualsSol()) {
				testEgalite=false;
			}
			//System.out.println(k);
			k++;
			ls.setInitial();
			t1=System.currentTimeMillis();
		} while (testEgalite && (t1-t0)<20000); /* condition d'arrêt de l'algo
		un tour de boucle correspond à une itération de l'algo, O(n) */
		
		System.err.println("Solution finale : "+ls.tostring(ls.getSolution()));
		System.err.println("Distance finale : "+ls.distance(ls.getSolution()));
		System.err.println("Durée totale d'exécution : "+(t1-t0)+" ms");
		System.err.println("Durée d'une itération : "+(t1-t0)/k+ "ms");
		System.err.println("Nombre d'itération : "+k);
		System.err.println("Arrêt de l'algo car ini==sol : "+!testEgalite);
	}
}
