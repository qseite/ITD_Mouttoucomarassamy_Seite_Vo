package tsp;
 

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LocalSearch {
	private Instance instance;
	private int[] solution;
	private int[] temp;
	

	public LocalSearch(Instance instance) {

		super();
		this.instance = instance;
		this.temp= new int[this.instance.getNbCities()];

		ArrayList<Integer> sol= new ArrayList<Integer>();	
		for(int i=0;i<instance.getNbCities();i++) {
			sol.add(i);
		}
		Collections.shuffle(sol);
		int[] solution= new int[instance.getNbCities()];
		for(int j=0;j<instance.getNbCities();j++) {
			solution[j]=sol.get(j);
		}
		this.solution=solution;

	}
	public void swap(int[] tab, int i,int j) {
		int a=tab[i];
		tab[i]=tab[j];
		tab[j]=a;
	}
	
	public Instance getInstance() {
		return this.instance;
	}
	
	public int[] getSolution() {
		return this.solution;
	}
	
	public int[] getTemp() {
		return this.temp;
	}
	
	public double distance(int[] tab) throws Exception {
		double d=0;
		for (int i=0;i<tab.length-1;i++) {
			d=d+this.instance.getDistances(tab[i],tab[i+1]);
		}
		d=d+this.instance.getDistances(tab[tab.length-1],tab[0]);		
		return d;
	}
	
	public String tostring(int[] tab) {
		String stringsol="";
		for(int t=0;t<tab.length;t++) {
				stringsol+=" "+tab[t];
		}
		return stringsol;
	}

	
	public void swapCycle(int index) throws Exception { //réalise un cycle de swap sur l'élement d'index 'index'
		for (int i=0;i<index;i++) {
			this.swap(this.temp, i, index);
			if (this.distance(this.getTemp())<this.distance(this.getSolution())) {
				this.solution=this.temp;
			}
		}
	}
	
	public static void main(String[] args)  {
			try{
				Instance graph = new Instance("instances/eil10.tsp",0);
				LocalSearch LS=new LocalSearch(graph);
				int[] tab = LS.solution; //0765349812
				int j;
				int[] boucletab;
				int indexj; /* index de j repositionné pour parcourir la liste de i+1 à i-1*/
				double distanceMin=LS.distance(tab);
				boolean condition=true;/*condition d'arret de la recherche locale*/
				int i=0;/*indice qui va être swappé en position indexj*/
				double verifcondition=distanceMin;
				while(condition) {
					while(i<tab.length){ //on prend tous les i du tableau pour les swaps a des emplacements indexj
						j=i+1;
						indexj=j%tab.length;
						while(indexj!=i) {//tant qu'on revient pas à l'emplacement de départ, on swap
							boucletab=tab;//copie du tableau d'origine
							LS.swap(boucletab,i,indexj);
							//System.out.println("distance actuelle "+LS.distance(boucletab));
							//System.out.println("distanceMin trouvée "+distanceMin);
							if(LS.distance(boucletab)<distanceMin) {//on compare les distances pour voir si on trouve mieux
								LS.solution=boucletab;
								System.out.println(LS.tostring(LS.solution));
								distanceMin=LS.distance(LS.solution);
								System.out.println("Nouvelle solution:"+LS.tostring(LS.solution)+" : "+LS.distance(LS.solution)+"km");
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
				tab=LS.solution;//on mémorise le changement si il y en a eu
			}
			
				System.out.println(LS.tostring(LS.solution));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
