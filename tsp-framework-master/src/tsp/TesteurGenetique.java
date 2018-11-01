package tsp;

import java.io.IOException;

import java.util.ArrayList;

public class TesteurGenetique {
	
	public static void main(String[] args) throws Exception {
		
		int nbIndividus = 100;
		Instance g_instance = new Instance("instances/eil51.tsp",0);
		Population population = new Population(nbIndividus,g_instance);
		int nbIterationsElitistes=5;
		int nbIterationsNonElitistes=500;
		double seuilMutation = 0.2;
		Individu meilleur = population.getBest();
		
		//Iterations avec une s�lection des parents �litiste et sans mutation
		for(int i=0;i<nbIterationsElitistes;i++)  {
			System.out.println("iteration:"+i);
			ArrayList<Individu> parents = population.selectionElitiste();
			Individu enfant = population.crossover2(parents.get(0),parents.get(1));
			population.insertion(enfant);
			if (meilleur.getValeur()>population.getBest().getValeur()) {
				meilleur=population.getBest();
			}
			System.out.println("valeur: -----"+population.getBest().getValeur()+"----");
			
	    }
		//It�rations avec une s�lection des parents al�atoire et avec mutation
		for(int i=nbIterationsElitistes;i<nbIterationsNonElitistes+nbIterationsElitistes;i++) {
			System.out.println("iteration:"+i);
			ArrayList<Individu> parents = population.selectionAleatoire();
			Individu enfant = population.crossover2(parents.get(0),parents.get(1));
			double alea = Math.random();
			if(alea<seuilMutation) {
				enfant.mutation();
			}
			population.insertion(enfant);
			if (meilleur.getValeur()>population.getBest().getValeur()) {
				meilleur=population.getBest();
			}
			System.out.println("valeur: -----"+population.getBest().getValeur()+"----");	
			}
		
		
		System.out.println("Valeur du meilleur : "+meilleur.getValeur());
	    System.out.println("Nombre d'individus : "+population.getPopulation().size());
  
	}
}

