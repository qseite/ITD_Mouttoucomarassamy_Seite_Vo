package tsp;

import java.util.ArrayList;

public class Population {
	
	ArrayList<Individu> population;
	int nombreIndividus;
	Instance g_instance;
	
	public Population(int nbIndividu, Instance g_instance) {
		this.population = new ArrayList<Individu>();
		this.nombreIndividus=nbIndividu;
		this.g_instance=g_instance;
		ArrayList<Integer> temp=new ArrayList<Integer>();
		for (int i=0;i<g_instance.getNbCities();i++) {
			temp.add(i);
		}
		Individu temp2=new Individu(g_instance,temp);
		this.population.add(temp2);
		for (int i=1;i<nbIndividu;i++) {
			temp2.mutation();
			this.population.add(new Individu(g_instance,temp2.getOrdreVisite()));
		}
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
	
	
	// insere l'individu en argument et enl�ve et retourne l'individu retir� (celui qui a la plus grande distance)
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
