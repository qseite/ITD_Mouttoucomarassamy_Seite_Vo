package tsp;

import java.util.ArrayList;
import java.util.Collections;

public class Population {
	
	ArrayList<Individu> population;
	int nombreIndividus;
	Instance g_instance;
	
	//Constructeur qui initialise une population dont les individus sont déterminés totalement aléatoirement
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
	
    public Population(ArrayList<Individu> population) {
    	this.population=population;
    }
	
	
	public ArrayList<Individu> getPopulation(){
		return this.population;
	}
	
	public Instance getInstance() {
		return this.g_instance;
	}
	
	// Retourne l'index du meilleur individu
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
	
	//retourne le meilleur individu
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
	
	// Insere l'individu en argument et enlève et retourne l'individu le moins bon individu
	public Individu insertion(Individu aInserer) throws Exception {
		double valeur=this.population.get(0).getValeur();
		int index=0;
		for (int i=1;i<this.population.size();i++) {
			double inter = this.population.get(i).getValeur();
			if (inter>valeur){
				index=i;
				valeur=inter;
			}
	    }Individu res = this.population.get(index);
	    if(valeur>aInserer.getValeur()) {
	    	this.population.remove(index);
			this.population.add(aInserer);
			return res;
	    }
	    else {
	    	return aInserer;
	    }
     }
	
	// Retourne 2 parents : le meilleur individu de la population et un individu choisi aléatoirement
	public ArrayList<Individu> selectionElitiste() throws Exception {
		
		int index = this.getIndexBest();
		Individu ind1 = this.population.get(index);
		//Population copie = new Population(this.getPopulation());
		//copie.getPopulation().remove(index);
		int alea = (int)(Math.random()*this.getPopulation().size()-1);
		Individu ind2=this.getPopulation().get(alea);
		ArrayList<Individu> res = new ArrayList<Individu>();
		res.add(ind1);
		res.add(ind2);
		return res;	
	}
	
	//Retourne 2 parents : tous deux choisis aléatoirement
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
		double probaCumulee=0;
		for (int i=0;i<nombreIndividus;i++) {
			probaCumulee+=this.getPopulation().get(i).getValeur();
		}
		double alea = Math.random()*probaCumulee;
	}
}
