package tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.sun.deploy.util.SystemPropertyUtil;

public class TesteurMethodeGen {
	
	public static void main(String[] args) throws Exception {
		Instance graph = new Instance("instances/eil51.tsp",0);
		ArrayList<Integer> ordreVille=new ArrayList<Integer>();
		for (int i=0;i<graph.getNbCities();i++) {
			ordreVille.add(i);
		}
		Individu i1=new Individu(graph,ordreVille);
		
		System.out.println("Ordreville de i1 "+i1.getOrdreVisite()+"\n");
		
		ArrayList<Integer> temp=new ArrayList<Integer>();
		temp.addAll(ordreVille);
		Collections.shuffle(temp);
		Individu i2=new Individu(graph,temp);

		System.out.println("Ordreville de i2 "+i2.getOrdreVisite()+"\n");
		i1.mutationSwap(1);
		System.out.println("on effectue une mutationSwap sur i1");
		System.out.println("nouvel ordreVille de i1 "+i1.getOrdreVisite()+"\n");
		
		ArrayList<Individu> lInd=new ArrayList<Individu>();
		lInd.add(i1);lInd.add(i2);
		Population pop=new Population(2,graph,lInd);
		
		Individu i3=pop.crossover2(i1, i2);
		System.out.println("Ordre ville de i3=crossover2(i1,i2) "+i3.getOrdreVisite());
		System.out.println(i3.getOrdreVisite().size()+"\n");
		
		System.out.println("valeur de i1 "+i1.getValeur());
		System.out.println("valeur de i2 "+i2.getValeur());
		System.out.println("valeur de i3 "+i3.getValeur()+"\n");
		pop.insertion(i3);
		System.out.println("valeur du 1er ind de pop "+pop.getPopulation().get(0).getValeur());
		System.out.println("valeur du 2e ind de pop "+pop.getPopulation().get(1).getValeur());
		
		
		
		
		
	}
}
