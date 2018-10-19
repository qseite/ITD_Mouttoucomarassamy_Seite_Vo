package tsp;

import java.util.ArrayList;

public class Population {
	
	ArrayList<Individu> population;
	
	public Population() {
		
	}
	
	//retourne le meilleur individu
	public Individu getBest() {
		double valeur=this.population.get(0);
		for (int i=1;i<this.population.size();i++) {
			double inter = this.population.get(i).getValeur();
			if (inter<valeur){
				res=valeur;
			}
		}
	}
	
	// insere l'individu en argument et enlève et retourne l'individu retiré (celui qui a la plus grande distance)
	public Individu insertion(Individu inserer) {
		
	}

}
