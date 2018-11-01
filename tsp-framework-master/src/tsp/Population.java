package tsp;

import java.util.ArrayList;
import java.util.Collections;

public class Population {
	
	ArrayList<Individu> population;
	int nombreIndividus;
	Instance g_instance;
	
	/**
	 * Initialise une population à partir d'une instance, l'adn de chaque individu de la population est
	 * généré aléatoirement
	 * @param nbIndividu, nombre d'individus dans la population
	 * @param g_instance, instance concernée
	 */
	public Population(int nbIndividu, Instance g_instance) {
		this.population = new ArrayList<Individu>();
		this.nombreIndividus=nbIndividu;
		this.g_instance=g_instance;
		ArrayList<Integer> temp=new ArrayList<Integer>();
		for (int i=0;i<g_instance.getNbCities();i++) {
			temp.add(i);
		}
		this.population.add(new Individu(g_instance,temp));
		for (int i=1;i<nbIndividu;i++) {
			ArrayList<Integer> copie = new ArrayList<Integer>();
			copie.addAll(temp);
			Collections.shuffle(copie);
			Individu aRajouter = new Individu(g_instance,copie);
			this.population.add(aRajouter);
		}
		
	}
	
	/**
	 * Initialise une population à partir d'une liste prédéfinie d'Invidus
	 * @param nbIndividus
	 * @param g_instance
	 * @param population
	 */
    public Population(int nbIndividus, Instance g_instance, ArrayList<Individu> population) {
    	this.population=population;
    	this.nombreIndividus=nbIndividus;
    	this.g_instance=g_instance;
    }
	
    /**
     * @return Retourne l'ArrayList des Individus de la population
     */
	public ArrayList<Individu> getPopulation(){
		return this.population;
	}
	
	public Instance getInstance() {
		return this.g_instance;
	}
	
	public int getNombreIndividus() {
		return this.nombreIndividus;
	}
	
	/**
	 * @return Retourne l'index dans this.population de l'Individu possédant la meilleure solution
	 * @throws Exception
	 */
	public int getIndexBest() throws Exception {
		double valeur=this.population.get(0).getValeur();
		int index=0;
		for (int i=1;i<this.population.size();i++) {
			double inter = this.population.get(i).getValeur();
			if (inter<valeur){
				valeur=inter;
				index=i;
			}	
		}
		return index;	
	}
	
	/**
	 * @see getIndexBest()
	 * @return Retourne l'Individu dans this.population possédant la meilleure solution
	 * @throws Exception
	 */
	public Individu getBest() throws Exception {
		return this.population.get(this.getIndexBest());
	}
	
	/**
	 * Ajoute arbitrairement un individu à la population
	 * @param ind : individu ajouté à la population
	 */
	public void add(Individu ind) {
		this.population.add(ind);
		this.nombreIndividus++;
	}
	/**
	 * Prend en entrée 2 individus de la population et réalise un crossover à 1 point 
	 * pour faire naître un nouvel Individu, l'index du point est choisi aléatoirement
	 * @param ind1 : parent n°1
	 * @param ind2 : parent n°2
	 * @return un Individu "enfant" de ind1 et ind2
	 */
	public Individu crossover1(Individu ind1, Individu ind2) {
		ArrayList<Integer> ordre = new ArrayList<Integer>();
		int taille = ind1.getOrdreVisite().size();
		int alea = (int)(Math.random()*(taille-1));
		for (int i=0;i<alea;i++) {
			ordre.add(ind1.getOrdreVisite().get(i));
		}
		for (int j = alea;j<taille;j++) {
			if(!ordre.contains(ind2.getOrdreVisite().get(j))) {
				ordre.add(ind2.getOrdreVisite().get(j));
			}else {
				int k=0;
				while(ordre.contains(ind2.getOrdreVisite().get(k)) && k<taille ) {
					k++;
				}ordre.add(ind2.getOrdreVisite().get(k));
			}
		}
		Individu res = new Individu(this.g_instance,ordre);
		return res;
	}
	
	/**
	 * Prend en entrée 2 individus de la population et réalise un crossover à 2 points
	 * pour faire naître un nouvel individu, les index des 2 points sont choisis aléatoirement
	 * @param i1 : parent n°1
	 * @param i2 : parent n°2
	 * @return un individu "enfant" de i1 et i2
	 */
	public Individu crossover2(Individu i1, Individu i2) {
		int alea1 = (int)(Math.random()*this.getInstance().getNbCities()-1);
		int alea2 = (int)(Math.random()*(this.getInstance().getNbCities()-1));
		int index1 = Math.min(alea1, alea2);
		int index2 = Math.max(alea1, alea2);
		ArrayList<Integer> child = new ArrayList<Integer>();
		for (int i=0;i<index1;i++) {
			child.add(i1.getOrdreVisite().get(i));
		}
		for (int i=index1;i<index2;i++) {
			if (!child.contains(i2.getOrdreVisite().get(i))) {
				child.add(i2.getOrdreVisite().get(i));
			} else {
				int k=0; 
				while (child.contains(i2.getOrdreVisite().get(k)) && k<i2.getOrdreVisite().size()) {
					k++;
				}
				child.add(i2.getOrdreVisite().get(k));
			}
		}
		for (int i=index2;i<this.getInstance().getNbCities();i++) {
			if (!child.contains(i1.getOrdreVisite().get(i))) {
				child.add(i1.getOrdreVisite().get(i));
			} else {
				int j=index1; 
				while (child.contains(i1.getOrdreVisite().get(j)) && j<i1.getOrdreVisite().size()) {
					j++;
				}
				child.add(i1.getOrdreVisite().get(j));
			}
		}
		return new Individu(this.getInstance(),child);
	}
	/*Effectue un croisement entre 2 individus parents pour donner 2 individus enfants
	 * @param parent1 : parent n�1
	 * @param parent2 : parent n�2
	 * @return une ArrayList<Individu> compos�e des 2 enfants
	 */
	public ArrayList<Individu> crossoverOX(Individu parent1, Individu parent2){
		
		int taille = parent1.getOrdreVisite().size();
		
		int alea1 = (int)(Math.random()*taille-1);
		int alea2 = (int)(Math.random()*taille-1);
		
		int debut = Math.min(alea1, alea2);
		int fin = Math.max(alea1, alea2);
		
		ArrayList<Integer> enfant1 = new ArrayList<Integer>();
		ArrayList<Integer> enfant2 = new ArrayList<Integer>();
		
		enfant1.addAll(parent1.getOrdreVisite().subList(debut, fin));
		enfant2.addAll(parent2.getOrdreVisite().subList(debut, fin));
		
		int villeActuelle = 0;
		int villeParent1 = 0;
		int villeParent2 = 0;
		for (int i=0;i<taille;i++) {
			
			villeActuelle = (fin+i)%taille;
			
			villeParent1 = parent1.getOrdreVisite().get(villeActuelle);
			villeParent2 = parent2.getOrdreVisite().get(villeActuelle);
			
			if(!enfant1.contains(villeParent2)) {
				enfant1.add(villeParent2);
			}
			
			if (!enfant2.contains(villeParent1)) {
				enfant2.add(villeParent1);
			}
		}
		
		Collections.rotate(enfant1, debut);
		Collections.rotate(enfant2, debut);
		
		ArrayList<Individu> res = new ArrayList<Individu>();
		Individu ind1 = new Individu(this.getInstance(),enfant1);
		Individu ind2 = new Individu(this.getInstance(),enfant2);
		res.add(ind1);
		res.add(ind2);
		return res;
		
	}
	
	/*public Individu crossover2points(Individu ind1, Individu ind2) {
		int alea1 = (int)(Math.random()*this.getInstance().getNbCities()-1);
		int alea2 = (int)(Math.random()*(this.getInstance().getNbCities()-1));
		int index1 = Math.min(alea1, alea2);
		int index2 = Math.max(alea1, alea2);
		int taille = ind1.getOrdreVisite().size();
		ArrayList<Integer> ordre = new ArrayList<Integer>();
		
		for (int i=0;i<taille;i++) {
			ordre.add(-1);
		}
		
		// On recopie individu1 entre index1 et index2
		for (int i=index1;i<index2;i++) {
			ordre.set(i,ind1.getOrdreVisite().get(i));
		}
		
		ArrayList<Integer> restant = new ArrayList<Integer>();
		for (int i=0;i<taille;i++) {
			if(!ordre.contains(ind1.getOrdreVisite().get(i))) {
				restant.add(ind1.getOrdreVisite().get(i));
			}
		}
		ArrayList<Integer> restantOrdonne = new ArrayList<Integer>();
		for (int i=0;i<taille;i++) {
		 if(restant.contains(ind2.getOrdreVisite().get(i))) {
			 restantOrdonne.add(ind2.getOrdreVisite().get(i));
		      }
		}
		
		for (int i=0;i<restantOrdonne.size();i++) {
			int index = (index2+i)%taille;
			ordre.set(index,restantOrdonne.get(i));
		}
		
		return new Individu(this.getInstance(),ordre);
	}*/
	
	/**
	 * Insere l'individu en entrée s'il est meilleur que le moins 
	 * bon de la population et enlève le moins bon
	 * @param aInserer : individu à insérer dans la population
	 * @throws Exception
	 */
	public void insertion(Individu aInserer) throws Exception {
		double valeur=this.population.get(0).getValeur();
		int index=0;
		for (int i=1;i<this.population.size();i++) {
			double inter = this.population.get(i).getValeur();
			if (inter>valeur){
				valeur=inter;
				index=i;
			}
	    }
		boolean dejaPresent =false;
		for (int i=0;i<this.getPopulation().size();i++) {
			if(aInserer.getOrdreVisite().equals(this.getPopulation().get(i).getOrdreVisite())) {
				dejaPresent=true;
			}
		}
	    if(valeur>aInserer.getValeur() && !dejaPresent) {
	    	this.population.remove(index);
			this.population.add(aInserer);
	    }
     }
	
	/**
	 * Retourne 2 parents : le meilleur individu de la population et un individu choisi aléatoirement
	 * @return une ArrayList d'Individu contenant les 2 parents
	 * @throws Exception
	 */
	public ArrayList<Individu> selectionElitiste() throws Exception {
		
		int index = this.getIndexBest();
		Individu ind1 = this.getPopulation().get(index);
		//Population copie = new Population(this.getPopulation());
		//copie.getPopulation().remove(index);
		int alea = (int)(Math.random()*this.getPopulation().size()-1);
		Individu ind2=this.getPopulation().get(alea);
		ArrayList<Individu> res = new ArrayList<Individu>();
		res.add(ind1);
		res.add(ind2);
		return res;	
	}
	
	/**
	 * Retourne 2 parents : tous deux choisis aléatoirement
	 * @return une ArrayList d'Individu contenant les 2 parents
	 * @throws Exception
	 */
	public ArrayList<Individu> selectionAleatoire() throws Exception {
		int index1 = (int)(Math.random()*this.getPopulation().size()-1);
		int index2 = (int)(Math.random()*this.getPopulation().size()-1);
		ArrayList<Individu> res = new ArrayList<Individu>();
		Individu ind1=this.getPopulation().get(index1);
		Individu ind2=this.getPopulation().get(index2);
		res.add(ind1);
		res.add(ind2);
		return res;
	}
	
	/**
	 * Retourne 2 parents à partir de la population, les parents sont choisis en 
	 * fonction de la qualité de leur solution, plus les individus sont bons plus
	 * ils ont de chance d'être parent
	 * @return une ArrayList d'Individu contenant les 2 parents
	 * @throws Exception
	 */
	public ArrayList<Individu> selectionRoulette() throws Exception {
		
		ArrayList<Double> emplacement = new ArrayList<Double>();
		double sommeDesChemins=0;
		
		for (int i=0;i<this.getPopulation().size();i++) {
			sommeDesChemins+=1.0/this.getPopulation().get(i).getValeur();
			emplacement.add(1.0/this.getPopulation().get(i).getValeur());
		} 
		
		double alea1 = Math.random();
		double alea2= Math.random();
		double sommeDesProbas=0;
		boolean test1=false;
		boolean test2=false;
		int index1=2;
		int index2=0;
		
		for (int i=0;i<this.getPopulation().size() && (!test1 || !test2);i++) {
			sommeDesProbas+=(emplacement.get(i)/sommeDesChemins);
			if (alea1 <=sommeDesProbas && !test1) {
				test1=true;
				index1=i;
				//System.err.println(sommeDesProbas);
			} else if (alea2 <=sommeDesProbas && !test2) {
				test2=true;
				index2=i;
				//System.err.println(sommeDesProbas);
			}
		}
		Individu parent1=new Individu(this.getInstance(),this.getPopulation().get(index1).getOrdreVisite());
		Individu parent2=new Individu(this.getInstance(),this.getPopulation().get(index2).getOrdreVisite());
		
		ArrayList<Individu> rep=new ArrayList<Individu>();
		rep.add(parent1);rep.add(parent2);
		
		return rep;
	}
}
