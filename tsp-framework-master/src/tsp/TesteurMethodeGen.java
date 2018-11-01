package tsp;

import java.io.IOException;
import java.util.ArrayList;

public class TesteurMethodeGen {
	
	public static void main(String[] args) throws IOException {
		Instance graph = new Instance("instances/eil51.tsp",0);
		ArrayList<Integer> ordreVille=new ArrayList<Integer>();
		for (int i=0;i<graph.getNbCities();i++) {
			ordreVille.add(i);
		}
		Individu i1=new Individu(graph,ordreVille);
		System.out.println(i1.getOrdreVisite());
		i1.mutation();
		System.out.println("on effectue une mutation");
		System.out.println(i1.getOrdreVisite());
		
	}
}
