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
			Collections.shuffle(temp);
			ArrayList<Integer> copie = new ArrayList<Integer>();
			for (int j=0;j<temp.size();j++) {
				copie.add(temp.get(j));
			}
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
	
	public Individu crossover1(Individu ind1, Individu ind2) {
		ArrayList<Integer> ordre = new ArrayList<Integer>();
		int taille = ind1.getOrdreVisite().size();
		int alea = (int)(Math.random()*taille-1);
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
	
	// Insere l'individu en argument s'il est meilleur que le moins bon de la population et enl�ve le moins bon
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
	    if(valeur>aInserer.getValeur()) {
	    	this.population.remove(index);
			this.population.add(aInserer);
	    }
     }
	
	// Retourne 2 parents : le meilleur individu de la population et un individu choisi al�atoirement
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
	
	//Retourne 2 parents : tous deux choisis al�atoirement
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
	
	public ArrayList<Individu> selectionRoulette() throws Exception {
		Population copie = new Population(this.nombreIndividus,this.g_instance,this.getPopulation());
		System.out.println(copie.nombreIndividus);
		ArrayList<Double> emplacement1 = new ArrayList<Double>();
		double probaCumulee1=0;
		for (int i=0;i<copie.getPopulation().size();i++) {
			probaCumulee1+=copie.getPopulation().get(i).getValeur();
			emplacement1.add(probaCumulee1);
		}double alea1 = Math.random()*probaCumulee1;
		double temp1=emplacement1.get(0);
		int index1=0;
		while(alea1>temp1 && index1<copie.nombreIndividus) {
			index1++;
			temp1=emplacement1.get(index1);
		}Individu parent1=copie.getPopulation().get(index1);
		copie.getPopulation().remove(index1);
		
		ArrayList<Double> emplacement2 = new ArrayList<Double>();
		double probaCumulee2=0;
		for (int i=0;i<copie.getPopulation().size();i++) {
			probaCumulee2+=copie.getPopulation().get(i).getValeur();
			emplacement2.add(probaCumulee2);
		}double alea2 = Math.random()*probaCumulee2;
		double temp2=emplacement2.get(0);
		int index2=0;
		while(alea2>temp2 && index2<copie.nombreIndividus) {
			index2++;
			temp2=emplacement2.get(index2);
		}Individu parent2=copie.getPopulation().get(index2);
		
		ArrayList<Individu> res = new ArrayList<Individu>();
		res.add(parent1);
		res.add(parent2);
		return res;
		
		
	}
	
	
}
