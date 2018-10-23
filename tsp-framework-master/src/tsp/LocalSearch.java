package tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LocalSearch {
	private Instance instance;
	private int[] solution;
	private int[] temp;
	private int[] ini;
	private int compteurStop;
	

	public LocalSearch(Instance instance) {
		super();
		this.instance = instance;
		this.temp= new int[this.instance.getNbCities()];
		this.solution= new int[this.instance.getNbCities()];
		this.ini=new int[this.instance.getNbCities()];
		ArrayList<Integer> sol= new ArrayList<Integer>();	
		for(int i=0;i<instance.getNbCities();i++) {
			sol.add(i);
		}
		Collections.shuffle(sol);
		for(int j=0;j<instance.getNbCities();j++) {
			this.ini[j]=sol.get(j);
			this.solution[j]=sol.get(j);
		}
		this.compteurStop=0;
		
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
	
	public void setSolution(int[] tab) {
		for (int i=0;i<tab.length;i++) {
			this.solution[i]=tab[i];
		}
	}
	
	public int[] getTemp() {
		return this.temp;
	}
	
	public void setTemp(int[] tab) {
		for (int i=0;i<tab.length;i++) {
			this.temp[i]=tab[i];
		}
	}
	
	public int[] getIni() {
		return this.ini;
	}
	
	public void setIni(int[] tab) {
		for (int i=0;i<tab.length;i++) {
			this.ini[i]=tab[i];
		}
	}

	public double distance(int[] tab) throws Exception {
		double d=0;
		for (int i=0;i<tab.length-1;i++) {
			d=d+this.instance.getDistances(tab[i],tab[i+1]);
		}
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
		this.setTemp(this.getIni());
		for (int i=index;i<this.getInstance().getNbCities();i++) {
			this.swap(this.temp, i, index);
			//System.out.println(this.distance(this.getTemp()));
			if (this.distance(this.getTemp())<this.distance(this.getSolution())) {
				this.setSolution(this.getTemp());
			}
		}
	}

	public void setInitial() {
		this.setIni(this.getSolution());
	}
	
	public int getCompteurStop() {
		return this.compteurStop;
	}
	
	public void setCompteurPlus() {
		this.compteurStop++;
	}
	
	public boolean iniEqualsSol() throws Exception {
		return (this.distance(this.getIni())==this.distance(this.getSolution()));
	}

	
	/*public static void main(String[] args)  {
			try{
				Instance graph = new Instance("instances/eil10.tsp",0);
				int[] tab = {0,7,6,5,3,4,9,8,1,2}; //0765349812
				int[] LStab=tab;
				LocalSearch LS=new LocalSearch(graph,LStab);
				int j;
				int[] boucletab;
				int indexj; // index de j repositionné pour parcourir la liste de i+1 à i-1
				double distanceMin=LS.distance(tab);
				boolean condition=true;//condition d'arret de la recherche locale
				int i=0;//indice qui va être swappé en position indexj
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
								System.out.println(LS.tostring(LS.solution)+"\n"+LS.tostring(boucletab));
								distanceMin=LS.distance(LS.solution);
								System.out.println("Nouvelle solution:"+" : "+LS.distance(LS.solution)+"km");
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
	}*/
	
	public static void main(String[] args) throws Exception {
		long t0;
		long t1=0;
		int k=0;
		t0=System.currentTimeMillis();
		Instance graph = new Instance("instances/pcb442.tsp",0);
		LocalSearch ls = new LocalSearch(graph);
		System.out.println("Solution initiale : "+ls.tostring(ls.getIni()));
		System.out.println("Distance initiale : "+ls.distance(ls.getIni()));
		do {
			for (int j=0;j<graph.getNbCities();j++) {
				ls.swapCycle(j);
			}
			if (ls.iniEqualsSol()) {
				ls.setCompteurPlus();
				System.err.println(ls.getCompteurStop());
			}
			System.out.println(k);
			k++;
			ls.setInitial();
			t1=System.currentTimeMillis();
		} while (ls.getCompteurStop()<10 && (t1-t0)<60000);
		System.out.println("Solution finale : "+ls.tostring(ls.getSolution()));
		System.out.println("Distance finale : "+ls.distance(ls.getSolution()));
		System.out.println("Durée totale d'exécution : "+(t1-t0)+" ms");
		System.out.println("Durée d'une itération : "+(t1-t0)/k+ "ms");
	}
}
