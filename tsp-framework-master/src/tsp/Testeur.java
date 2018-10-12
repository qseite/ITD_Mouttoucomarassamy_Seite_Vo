package tsp;

import java.io.IOException;
import java.util.ArrayList;

public class Testeur {
	
	public static void main(String[] args) throws Exception {
		Instance instance_test;
		ArrayList<Integer>[][] matrix = new ArrayList[5][5];
		matrix[1][1] = new ArrayList<Integer>();
		matrix[1][1].add(20);
		System.out.println(matrix);
		/*instance_test = new Instance("instances/eil10.tsp",1);
		Piste piste = new Piste(instance_test, 0.01);*/
		
	}
}
