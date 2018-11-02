package tsp;

import java.io.IOException;
import java.util.ArrayList;

public class Benchmark {
	public static Instance[] buildInstances() throws IOException {
		Instance[] inst = new Instance[12];
		inst[0] = new Instance("instances/brazil58.tsp",1);
		inst[1] = new Instance("instances/d198.tsp",0);
		inst[2] = new Instance("instances/d657.tsp",0);
		inst[3] = new Instance("instances/eil10.tsp",0);
		inst[4] = new Instance("instances/eil101.tsp",0);
		inst[5] = new Instance("instances/eil51.tsp",0);
		inst[6] = new Instance("instances/kroA100.tsp",0);
		inst[7] = new Instance("instances/kroA150.tsp",0);
		inst[8] = new Instance("instances/kroA200.tsp",0);
		inst[9] = new Instance("instances/lin318.tsp",0);
		inst[10] = new Instance("instances/pcb442.tsp",0);
		inst[11] = new Instance("instances/rat575.tsp",0);
		return inst;
	}
	
	public static void main(String[] args) throws Exception {
		Instance[] instances = buildInstances();
		for(int i = 0; i<instances.length; i++) {
			System.err.println("======="+instances[i].getFileName()+"========");
			
			long startTime = System.currentTimeMillis();
			long spentTime = 0;
			
			int nbIndividus = 90;

			Population population = new Population(nbIndividus,instances[i]);

			int nbIteElitistes=4000;
			int nbIteAleatoires=2000;
			double seuilMutation = 0.15;
	        double seuilOptimisation = 0.5;
	        
	        int index=0;

			do {
				
				//Iterations avec une s�lection des parents �litiste 
				if (index < nbIteElitistes)  {
					//System.err.println("iteration:"+index);
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
					//System.err.println("valeur: -----"+population.getBest().getValeur()+"----");
				
				//It�rations avec une s�lection des parents al�atoire
			    } else if (index<nbIteAleatoires+nbIteElitistes) {
			    	
					System.err.println("iteration:"+index);
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
				
					//System.err.println("valeur: -----"+population.getBest().getValeur()+"----");	
				}
				
				spentTime = System.currentTimeMillis() - startTime;
				index++;
			} while(spentTime < (60 * 1000 - 100) && index<nbIteAleatoires+nbIteElitistes );
			
			
			System.err.println("");
			System.err.println("Nombre d'itérations/reproduction : "+index);
			System.err.println("Durée d'une itération : "+spentTime/index+" ms");
			System.err.println("Durée d'éxécution : "+spentTime+" ms"+"\n");
		    System.out.println(population.getPopulation().get(0).getOrdreVisite().size());

		}
	}
}