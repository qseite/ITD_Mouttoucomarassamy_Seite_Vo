package tsp;

import java.io.IOException;

import java.util.ArrayList;

public class TesteurGenetique {
	
	public static void main(String[] args) throws Exception {
		
		int nbIndividus = 300;
		Instance g_instance = new Instance("instances/eil51.tsp",0);
		Population population = new Population(nbIndividus,g_instance);
		int nbIterationsElitistes=100;
		int nbIterationsNonElitistes=100;
		double seuilMutation = 0.2;
		Individu meilleur = population.getBest();
		
		//Test pour les crossovers
	   /* for (int i=0;i<2;i++) {
	    	Individu parent1 =population.selection1().get(0);
	    	Individu parent2 = population.selection1().get(1);
	    	System.out.println(parent1.getOrdreVisite());
	    	System.out.println(parent2.getOrdreVisite());
	    	Individu enfant = population.crossover2(parent1, parent2);
	    	System.out.println(enfant.getOrdreVisite());
	    }*/
		
		//Iterations avec une sélection des parents élitiste et sans mutation
		for(int i=0;i<nbIterationsElitistes;i++) {
			System.out.println("iteration:"+i);
			ArrayList<Individu> parents = population.selectionElitiste();
			Individu enfant = population.crossover2(parents.get(0),parents.get(1));
			population.insertion(enfant);
			//Optimis
			if (meilleur.getValeur()>population.getBest().getValeur()) {
				meilleur=population.getBest();
			}
			System.out.println("valeur: -----"+population.getBest().getValeur()+"----");
			
	    }
		//Itérations avec une sélection des parents aléatoire et avec mutation
		for(int i=nbIterationsElitistes;i<nbIterationsNonElitistes+nbIterationsElitistes;i++) {
			System.out.println("iteration:"+i);
			ArrayList<Individu> parents = population.selectionElitiste();
			Individu enfant = population.crossover2(parents.get(0),parents.get(1));
			population.insertion(enfant);
			double alea = Math.random();
			int indice = (int)(Math.random()*(nbIndividus-1));
			//int indice = 0;
			if (alea>seuilMutation) {
			for(int j=0;j<nbIndividus-indice;j++) {
					population.getPopulation().get(j).mutation();
			}
			}if (meilleur.getValeur()>population.getBest().getValeur()) {
				meilleur=population.getBest();
			}
			System.out.println("valeur: -----"+population.getBest().getValeur()+"----");
			
	} System.out.println("Valeur du meilleur : "+meilleur.getValeur());
}
}

