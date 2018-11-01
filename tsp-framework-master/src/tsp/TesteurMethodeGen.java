package tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TesteurMethodeGen {
	
	public static void main(String[] args) throws IOException {
		Instance graph = new Instance("instances/eil51.tsp",0);
		ArrayList<Integer> ordreVille=new ArrayList<Integer>();
		for (int i=0;i<graph.getNbCities();i++) {
			ordreVille.add(i);
		}
		Individu i1=new Individu(graph,ordreVille);
		
		System.out.println("Ordreville de i1 "+i1.getOrdreVisite());
		System.out.println("");
		
		ArrayList<Integer> temp=new ArrayList<Integer>();
		temp.addAll(ordreVille);
		Collections.shuffle(temp);
		Individu i2=new Individu(graph,temp);


		System.out.println("Ordreville de i2 "+i2.getOrdreVisite());
		System.out.println("");
		i1.mutationSwap(1);
		System.out.println("on effectue une mutationSwap");
		System.out.println(i1.getOrdreVisite());
		
	}
}
