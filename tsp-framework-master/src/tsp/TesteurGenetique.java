package tsp;

import java.io.IOException;

import java.util.ArrayList;

public class TesteurGenetique {
	
	public static void main(String[] args) throws Exception {
		
		int nbIndividus = 49;
		Instance g_instance = new Instance("instances/eil51.tsp",0);
		Population population = new Population(nbIndividus,g_instance);
		int nombreIterations=20;
		for(int i=0;i<nombreIterations;i++) {
			ArrayList<Individu> parents = population.selection();
			Individu enfant = population.crossover1(parents.get(0),parents.get(1));
			population.insertion(enfant);
		}System.out.println(population.getPopulation().toString());
		System.out.println(population.getBest().getValeur());
		
	}
}

