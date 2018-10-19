package tsp;

import java.io.IOException;

public class LocalSearch {
	private Instance instance;
	private int[] solution;
	public LocalSearch(Instance instance, int[] sol) {
		super();
		this.instance = instance;
		this.solution=sol;
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
	
	public static void main(String[] args)  {
			try{
				Instance graph = new Instance("instances/eil10.tsp",0);
				int[] tab = {1,2,3,4,5,6,7,8,9,10};
				LocalSearch LS=new LocalSearch(graph,tab);
				int j;
				int[] boucletab;
				int indexj; /* index de j repositionné pour parcourir la liste de i+1 à i-1*/
				double distanceMin=LS.distance(tab);
				boolean condition=true;/*condition d'arret de la recherche locale*/
				int i=0;/*indice qui va être swappé en position indexj*/
				double verifcondition=distanceMin;
				while(condition) {
					
					while(i<tab.length){
						j=i+1;
						indexj=j%tab.length;
						while(indexj!=i) {
							boucletab=tab;
							LS.swap(boucletab,i,indexj);
							if(LS.distance(boucletab)<distanceMin) {
								LS.solution=boucletab;
								distanceMin=LS.distance(LS.solution);
								System.out.println(LS.solution.toString()+" : "+LS.distance(LS.solution)+"km"+"\n");
							}
							indexj=(indexj+1)%tab.length;
						}
						i=i+1;
					}
					if(verifcondition==distanceMin) {
						condition=false;
					}else {
						verifcondition=distanceMin;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
