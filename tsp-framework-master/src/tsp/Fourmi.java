package tsp;

import java.util.ArrayList;

/**
 * Cette classe modélise une fourmi améliorée, 
 * 
 * @author romai
 *
 */
public class Fourmi {
	/** Une ArrayList des villes non visitées */
	private ArrayList<Integer> villesNonVisitees;
	
	/** Une ArrayList des villes visitées */
	private ArrayList<Integer> villesVisitees;
	
	/** L'Instance du problème contenant toutes les données */
	private Instance m_instance;
	
	/** La longueur du chemin parcouru par la fourmi */
	private double longueur;
	
	/** Tableau d'entiers, un élément d'index [i][j] vaut 1 si la fourmi a parcouru l'arc i-j, 0 sinon */
	private int[][] passageSurArc;
	
	/** Booléen valant 'true' si la fourmi est élitiste */
	private boolean elitiste;
	
	/** Objet dont la tâche principale est de mémoriser la quantité de phéromone sur le chemin */
	private Piste piste;
	
	/**
	 * Initialise une fourmi en fonction de l'instance et d'une Piste commune à toutes les fourmis
	 * La fourmi a son état initial n'a encore parcouru aucune ville
	 * @param Instance m_instance
	 * @param Piste piste
	 */
	public Fourmi(Instance m_instance, Piste piste) {
		this.piste=piste;
		this.elitiste=false;
		this.m_instance=m_instance;
		this.villesNonVisitees = new ArrayList<Integer>();
		for (int i=0;i<this.m_instance.getNbCities();i++) {
			this.villesNonVisitees.add(i);
		}
		this.villesVisitees = new ArrayList<Integer>();
		this.passageSurArc=new int[m_instance.getNbCities()][m_instance.getNbCities()];
		for (int i=0; i<this.m_instance.getNbCities();i++) {
			for (int j=0; j<=i;j++) {
				this.passageSurArc[i][j]=0;
				this.passageSurArc[j][i]=0;
			}
		}
	}
	
	/**
	 * Retourne la Piste sur laquelle la fourmi travaille
	 * @return une Piste
	 */
	public Piste getPiste() {
		return this.piste;
	}
	
	/**
	 * Retourne la liste des villes non visitées
	 * @return une ArrayList d'Integer
	 */
	public ArrayList<Integer> getVillesNonVisitees() {
		return this.villesNonVisitees;
	}
	
	/**
	 * Retourne la liste des villes visitées
	 * @return une ArrayList d'Integer
	 */
	public ArrayList<Integer> getVillesVisitees() {
		return this.villesVisitees;
	}
	
	/**
	 * Permet de savoir si la fourmi est élitiste ou non
	 * @return un booléen
	 */
	public boolean getElitiste() {
		return this.elitiste;
	}
	
	/**
	 * Permet de définir si une fourmi est élitiste ou non 
	 * @param boolean t
	 */
	public void setElitiste(boolean t) {
		this.elitiste=t;	
	}
	
	/**
	 * Retourne la variable d'instance 'passageSurArc'
	 * @return une matrice d'entiers
	 */
	public int[][] getPassage() {
		return this.passageSurArc;
	}
	
	/**
	 * Retourne la variable d'instance 'longueur'
	 * @return un double
	 */
	public double getLongueur() {
		return this.longueur;
	}
	
	/**
	 * Retourne l'Instance du problème
	 * @return une Instance
	 */
	public Instance getInstance() {
		return this.m_instance;
	}
	
	/**
	 * Permet de mettre à jour la liste des villes visitées
	 * @param int numeroVille
	 */
	public void ajouterVillesVisitee(int numeroVille) {
		if (this.getDerniereVilleVisitee()!=-1) {
			this.getPassage()[this.getDerniereVilleVisitee()][numeroVille]=1;
		}
		this.villesVisitees.add(numeroVille);
		int index=this.villesNonVisitees.indexOf(numeroVille);
		this.villesNonVisitees.remove(index);
	}
	
	/**
	 * Renvoie l'index de la dernière ville visitée
	 * @return un entier 
	 */
	public int getDerniereVilleVisitee() {
		if (this.getVillesVisitees().size()==0) {
			return -1;
		} else {
			return this.getVillesVisitees().get(this.getVillesVisitees().size()-1);
		}
	}
	
	/**
	 * Fait visiter à la fourmi la prochaine ville en fonction de la distance 
	 * et de la quantité de phéromone sur la Piste
	 * @throws Exception
	 */
	public void setProchaineVille() throws Exception {
		boolean test=false;
		double x = Math.random();
		double sommeDesProba=0;
		for (int k=0;k<this.getVillesNonVisitees().size() & !test;k++) {
			sommeDesProba+=this.getProbaIaJ(this.getDerniereVilleVisitee(),this.getVillesNonVisitees().get(k));
			if (x <= sommeDesProba) {
				this.ajouterVillesVisitee(this.getVillesNonVisitees().get(k));
				test=true;
			}
		}
	}
	
	/**
	 * Donne la probabilité que la fourmi aille de la ville 'i' à la ville 'j' 
	 * en fonction de la quantité de phéromone sur la Piste
	 * L'influence des phéromones et de la distance est déterminé arbitrairement par 
	 * des paramètres ALPHA et BETA
	 * @param i
	 * @param j
	 * @return un double correspondant à la probabilité
	 * @throws Exception
	 */
	public double getProbaIaJ(int i, int j) throws Exception {
		double num = Math.pow(this.getPiste().getPheromoneSurArc()[i][j], Piste.ALPHA)
				*Math.pow(1.0/this.m_instance.getDistances(i, j), Piste.BETA);
		double den = 0;
		for (int numVille : this.getVillesNonVisitees()) {
			den+=Math.pow(this.getPiste().getPheromoneSurArc()[i][numVille], Piste.ALPHA)
					*Math.pow(1.0/this.m_instance.getDistances(i, numVille), Piste.BETA);
		}
		return (num/den);
	}
	
	/**
	 * Calcule et enregistre la longueur du chemin parcouru par la Fourmi
	 * @throws Exception
	 */
	public void setLongueur() throws Exception {
		double l=0;
		int index=0;
		for (int i=0; i<this.getVillesVisitees().size()-1;i++) {
			l+=this.m_instance.getDistances(this.getVillesVisitees().get(i), this.getVillesVisitees().get(i+1));
			index=i+1;
		}
		l+=this.m_instance.getDistances(this.getVillesVisitees().get(0), 
				this.getVillesVisitees().get(index));
		this.longueur=l;
	}
}
