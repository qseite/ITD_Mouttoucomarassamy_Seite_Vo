package tsp;

import java.io.IOException;

import java.util.ArrayList;

public class TesteurCrossoverOX {
	
	public static void main(String[] args) throws Exception {
		long t0=0;
		long t1=0;
		t0=System.currentTimeMillis();

		int nbIndividus = 90;
		Instance g_instance = new Instance("instances/d657.tsp",0);
		Population population = new Population(nbIndividus,g_instance);
		int nbIteElitistes=4000;
		int nbIteAleatoires=2000;
		double seuilMutation = 0.15;
        double seuilOptimisation = 0.5;
	

		//Iterations avec une s�lection des parents �litiste 
		for(int i=0;i<nbIteElitistes;i++)  {
			System.out.println("iteration:"+i);
			ArrayList<Individu> parents = population.selectionElitiste();
			ArrayList<Individu> enfant = population.crossoverOX(parents.get(0),parents.get(1));
		    double alea = Math.random();
			if(alea<seuilMutation) {
				enfant.get(0).mutation();
				enfant.get(1).mutation();
			}
			double aleaBis = Math.random();
			if(aleaBis<seuilOptimisation) {
				enfant.get(0).optimisation();
				enfant.get(1).optimisation();
			}
			population.insertion(enfant.get(0));
			population.insertion(enfant.get(1));
			System.out.println("valeur: -----"+population.getBest().getValeur()+"----");
			
	    }
		//It�rations avec une s�lection des parents al�atoire
		for(int i=nbIteElitistes;i<nbIteAleatoires+nbIteElitistes;i++) {
			System.out.println("iteration:"+i);
			ArrayList<Individu> parents = population.selectionAleatoire();
			ArrayList<Individu> enfant = population.crossoverOX(parents.get(0),parents.get(1));
		    double alea = Math.random();
			if(alea<seuilMutation) {
				enfant.get(0).mutation();
				enfant.get(1).mutation();
			}
			double aleaBis = Math.random();
			if(aleaBis<seuilOptimisation) {
				enfant.get(0).optimisation();
				enfant.get(1).optimisation();
			}
			population.insertion(enfant.get(0));
			population.insertion(enfant.get(1));
		
			System.out.println("valeur: -----"+population.getBest().getValeur()+"----");	
			}
	   
	    t1=System.currentTimeMillis();
	    System.out.println("Durée d'exécution : "+(t1-t0)+" ms");
	    System.out.println(population.getPopulation().get(0).getOrdreVisite().size());
	}
}

