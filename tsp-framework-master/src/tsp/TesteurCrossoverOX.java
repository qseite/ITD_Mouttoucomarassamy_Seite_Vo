package tsp;

import java.io.IOException;

import java.util.ArrayList;

public class TesteurCrossoverOX {
	
	public static void main(String[] args) throws Exception {
		long t0=0;
		long t1=0;
		t0=System.currentTimeMillis();
		int nbIndividus = 100;
		Instance g_instance = new Instance("instances/d657.tsp",0);
		Population population = new Population(nbIndividus,g_instance);
		int nbIterationsElitistes=1;
		int nbIterationsNonElitistes=1000;
		double seuilMutation = 1;
		Individu meilleur = population.getBest();
		
		//Test pour les crossovers
	  /*  for (int i=0;i<2;i++) {
	    	Individu parent1 =population.selectionElitiste().get(0);
	    	Individu parent2 = population.selectionElitiste().get(1);
	    	System.out.println(parent1.getOrdreVisite());
	    	System.out.println(parent2.getOrdreVisite());
	    	Individu enfant = population.crossover2(parent1, parent2);
	    	System.out.println(enfant.getOrdreVisite());
	    }*/
		
		//Iterations avec une s�lection des parents �litiste et sans mutation
		for(int i=0;i<nbIterationsElitistes;i++)  {
			System.out.println("iteration:"+i);
			ArrayList<Individu> parents = population.selectionElitiste();
			ArrayList<Individu> enfant = population.crossoverOX(parents.get(0),parents.get(1));
		    double alea = Math.random();
			if(alea<seuilMutation) {
				enfant.get(0).mutation();
			}
			double aleaBis = Math.random();
			if(aleaBis<seuilMutation) {
				enfant.get(1).mutation();
			}
			population.insertion(enfant.get(0));
			population.insertion(enfant.get(1));
			if (meilleur.getValeur()>population.getBest().getValeur()) {
				meilleur=population.getBest();
			}
			System.out.println("valeur: -----"+population.getBest().getValeur()+"----");
			
	    }
		//It�rations avec une s�lection des parents al�atoire et avec mutation
		for(int i=nbIterationsElitistes;i<nbIterationsNonElitistes+nbIterationsElitistes;i++) {
			System.out.println("iteration:"+i);
			ArrayList<Individu> parents = population.selectionRoulette();
			ArrayList<Individu> enfant = population.crossoverOX(parents.get(0),parents.get(1));
		    double alea = Math.random();
			if(alea<seuilMutation) {
				enfant.get(0).mutation();
			}
			double aleaBis = Math.random();
			if(aleaBis<seuilMutation) {
				enfant.get(1).mutation();
			}
			population.insertion(enfant.get(0));
			population.insertion(enfant.get(1));
			if (meilleur.getValeur()>population.getBest().getValeur()) {
				meilleur=population.getBest();
			}
			System.out.println("valeur: -----"+population.getBest().getValeur()+"----");	
			}
		
		t1=System.currentTimeMillis();
		System.out.println("Valeur du meilleur : "+meilleur.getValeur());
	    System.out.println("Nombre d'individus : "+population.getPopulation().size());
	    System.out.println("Durée d'exécution : "+(t1-t0)+" ms");
	}
}

