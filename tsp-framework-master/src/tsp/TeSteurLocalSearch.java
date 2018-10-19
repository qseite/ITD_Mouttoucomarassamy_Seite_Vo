package tsp;

import java.io.IOException;

public class TeSteurLocalSearch {
	private Instance instance;
	public TeSteurLocalSearch(Instance instance) {
		super();
		this.instance = instance;
	}
	public void swap(int[] tab, int i,int j) {
		int a=tab[i];
		tab[i]=tab[j];
		tab[j]=a;
	}
	public double distance(int[] tab) throws Exception {
		double d=0;
		for (int i=0;i<tab.length-1;i++) {
			d=d+this.instance.getDistances(i,i+1);
		}
		

		return d;
	}
	
	public static void main(String[] args) throws IOException {
		Instance graph = new Instance("instances/eil51.tsp",0);
		
	} 
}
