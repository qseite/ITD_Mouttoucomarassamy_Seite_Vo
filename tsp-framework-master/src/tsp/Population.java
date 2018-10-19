package tsp;

import java.util.ArrayList;

public class Population {
	
	ArrayList<Individu> population;
	
	public Population() {
		
	}
	
	//retourne le meilleur individu
	public Individu getBest() throws Exception {
		double valeur=this.population.get(0).getValeur();
		int index=0;
		for (int i=1;i<this.population.size();i++) {
			double inter = this.population.get(i).getValeur();
			if (inter<valeur){
				index=i;
			}
		}return this.population.get(index);
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
}
