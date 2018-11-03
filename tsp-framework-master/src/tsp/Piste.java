package tsp;

import java.util.ArrayList;

/**
 * Cette classe mémorise les principales caractéristiques de la Piste parcourue par les Fourmis
 * 
 * @author romai
 *
 */

public class Piste {
	public static double ALPHA=1; 
	public static double BETA=2; 
	public static double Q=100; 
	public static double P=0.2;
	public static boolean ELITISTE=true;
	public static int NOMBRE_ELITISTE=20;
	public static double COEF_ELITISTE=10;
	public static int MAX_TIME=60;
	public static double c_ini_pheromone=0.1;
	public static int NOMBRE_FOURMI=101;
	
	/** L'Instance contenant toutes les données du problème */
	private Instance m_instance;
	
	/** Tableau de double mémorisant la quantité de phéromone sur la Piste */
	private double[][] pheromoneSurArc;
	
	/** Mémorise la longueur du meilleur chemin trouvé par l'une des Fourmis */
	private double bestLongueur;
	
	/** Mémorise le meilleur chemin actuel trouvé par l'une des Fourmis */
	private ArrayList<Integer> solutionTemp;
	
	/** Contient la liste de toutes les Fourmis parcourant la Piste */
	private ArrayList<Fourmi> listeFourmis;
	
	/** Mémorise le nombre de Fourmis sur la Piste */
	private int nombreFourmi;


	// -----------------------------
	// ----- CONSTRUCTOR -----------
	// -----------------------------
	
	/**
	 * Génère une Piste en fonction d'une Instance et des données du problème
	 * Le nombre de Fourmis parcourant la piste ainsi que la quantité de phéromone initiale 
	 * est déterminé arbitrairement 
	 * @param instance
	 */
	public Piste(Instance instance) {
		this.m_instance=instance;
		this.pheromoneSurArc = new double[instance.getNbCities()][instance.getNbCities()];
		this.bestLongueur=-1;
		this.solutionTemp = new ArrayList<Integer>();
		this.listeFourmis = new ArrayList<Fourmi>();
		for (int i=0; i<instance.getNbCities();i++) {
			for (int j=0; j<=i;j++) {
				this.pheromoneSurArc[i][j]=c_ini_pheromone;
				this.pheromoneSurArc[j][i]=this.pheromoneSurArc[i][j];
			}
		}
		this.nombreFourmi=NOMBRE_FOURMI;
		for (int i=0; i<this.nombreFourmi;i++) {
			this.listeFourmis.add(new Fourmi(this.m_instance, this));
		}
	}
	
	// -----------------------------
	// ----- METHODS ---------------
	// -----------------------------
	
	/**
	 * @return l'Instance du problème
	 */
	public Instance getInstance() {
		return this.m_instance;
	}
	
	/**
	 * @param index
	 * @return la Fourmi d'index 'index'
	 */
	public Fourmi getFourmi(int index) {
		return this.listeFourmis.get(index);
	}
	
	/**
	 * @return l'ArrayList des Fourmi parcourant la Piste
	 */
	public ArrayList<Fourmi> getFourmis() {
		return this.listeFourmis;
	}
	
	/**
	 * @return un tableau de double donnant la quantité de phéromone sur chaque arc
	 */
	public double[][] getPheromoneSurArc() {
		return this.pheromoneSurArc;
	}
	
	/**
	 * @return un double représentant la meilleur longueur actuelle
	 */
	public double getBestLongueur() {
		return this.bestLongueur;
	}
	
	/**
	 * @return une ArrayList d'Intger représentant le meilleur chemin actuel
	 */
	public ArrayList<Integer> getSolutionTemp() {
		return this.solutionTemp;
	}
	
	/**
	 * Met à jour la quantité de phéromone en fonction de la longueur du chemin
	 * parcouru par les Fourmi ainsi qu'en fonction du coefficient d'évaporation
	 * P des phéromones
	 * Plus le chemin parcouru est faible plus la quantité de phéromone déposée est importante
	 */
	public void majPheromone() {
		for (int i=0;i<this.getInstance().getNbCities();i++) {
			for (int j=0;j<=i;j++) {
				this.getPheromoneSurArc()[i][j]=P*this.getPheromoneSurArc()[i][j];
				for (Fourmi four : this.getFourmis()) {
					if (!four.getElitiste()) {
						this.getPheromoneSurArc()[i][j]
								+=four.getPassage()[i][j]*(Q/four.getLongueur());
					} else {
						this.getPheromoneSurArc()[i][j]
								+=four.getPassage()[i][j]*COEF_ELITISTE*(Q/four.getLongueur());
					}
				}
				this.getPheromoneSurArc()[j][i]=this.getPheromoneSurArc()[i][j];				
			}
		}
	}
	
	/**
	 * Met à jour le meilleur chemin trouvée et sa longueur
	 */
	public void majBestSolution() {
		double best=this.getFourmi(0).getLongueur();
		Fourmi bestF=this.getFourmi(0);
		for (Fourmi four : this.getFourmis()) {
			if (four.getLongueur()<best) {
				best = four.getLongueur();
				bestF = four;
			}
		}
		if (this.getBestLongueur()<0) {
			this.bestLongueur=best;
			this.solutionTemp=bestF.getVillesVisitees();			
		} else if (this.getBestLongueur()>best) {
			this.bestLongueur=best;
			this.solutionTemp=bestF.getVillesVisitees();
		}

	}
	
	/**
	 * Définit un nombre arbitraire de fourmis élitistes, les Fourmis élitistes
	 * sont les Fourmis ayant parcouru la plus petite distance
	 * @param int nbr : donne le nombre de fourmi élitiste
	 */
	public void setElitiste(int nbr) {
		for (int i=0;i<nbr;i++) {
			double best=this.getFourmi(0).getLongueur();
			Fourmi bestF=this.getFourmi(0);
			for (int j=0; j<this.getFourmis().size();j++) {
				if (this.getFourmi(j).getLongueur()<best && !this.getFourmi(j).getElitiste()) {
					best = this.getFourmi(j).getLongueur();
					bestF = this.getFourmi(j);
				}
			}
			bestF.setElitiste(true);
			if (i==0) {
				if (this.getBestLongueur()<0) {
					this.bestLongueur=best;
					this.solutionTemp=bestF.getVillesVisitees();			
				} else if (this.getBestLongueur()>best) {
					this.bestLongueur=best;
					this.solutionTemp=bestF.getVillesVisitees();
				}
			}
		}
	}
	
	/**
	 * Après un tour complet la mémoire de chaque fourmi est réinitialisée
	 */
	public void resetFourmis() {
		this.listeFourmis.clear();
		this.listeFourmis = new ArrayList<Fourmi>();
		for (int i=0; i<this.getInstance().getNbCities();i++) {
			this.listeFourmis.add(new Fourmi(this.getInstance(), this));
		}
	}
}
