package tsp;

import java.util.ArrayList;

public class Population {
	
	ArrayList<Individu> population;
	
	public Population() {
		
	}
	
    public Population(ArrayList<Individu> population) {
    	this.population=population;
    }
	
	
	public ArrayList<Individu> getPopulation(){
		return this.population;
	}
	
	public int getIndexBest() throws Exception {
		double valeur=this.population.get(0).getValeur();
		int index=0;
		for (int i=1;i<this.population.size();i++) {
			double inter = this.population.get(i).getValeur();
			if (inter<valeur){
				valeur=inter;
				index=i;
			}	
	 }return index;	
	}
	//retourne le meilleur individu
	public Individu getBest() throws Exception {
		return this.population.get(this.getIndexBest());
	}
	
	
	// insere l'individu en argument et enlève et retourne l'individu retiré (celui qui a la plus grande distance)
	public Individu insertion(Individu aInserer) throws Exception {
		double valeur=this.population.get(0).getValeur();
		int index=0;
		for (int i=1;i<this.population.size();i++) {
			double inter = this.population.get(i).getValeur();
			if (inter>valeur){
				index=i;
			}
	    }Individu res = this.population.get(index);
		this.population.remove(index);
		this.population.add(aInserer);
		return res;
     }
	
	public ArrayList<Individu> selection() throws Exception {
		
		int index = this.getIndexBest();
		Individu ind1 = this.population.get(index);
		Population copie = new Population(this.getPopulation());
		copie.getPopulation().remove(index);
		Individu ind2=copie.getBest();
		
		ArrayList<Individu> res = new ArrayList<Individu>();
		res.add(ind1);
		res.add(ind2);
		
		return res;
		
	}
}
