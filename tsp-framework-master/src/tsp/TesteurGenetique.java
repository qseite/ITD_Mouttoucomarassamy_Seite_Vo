package tsp;

import java.io.IOException;

import java.util.ArrayList;

public class TesteurGenetique {
	
	public static void main(String[] args) throws Exception {
		
		int nbIndividus = 50;
		Instance g_instance = new Instance("instances/eil51.tsp",0);
		Population population = new Population(nbIndividus,g_instance);
		int nombreIterations=30;
		double seuilMutation=0;
		for(int i=0;i<nombreIterations;i++) {
			ArrayList<Individu> parents = population.selection();
			Individu enfant = population.crossover1(parents.get(0),parents.get(1));
			population.insertion(enfant);
			double alea = Math.random();
			if(alea>seuilMutation) {
				for(int j=0;j<population.getPopulation().size();j++) {
					population.getPopulation().get(j).mutation();
				}
			}
		}System.out.println(population.getPopulation().toString());
		System.out.println(population.getBest().getValeur());
		
	}
}

