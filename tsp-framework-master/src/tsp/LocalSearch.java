package tsp;

import java.util.ArrayList;
import java.util.Collections;

public class LocalSearch {
	private Instance instance;
	private int[] solution;
	private int[] temp;
	private int[] ini; // permet uniquement de comparer si la solution optimale a évoluer au cours d'une itération
	// on stop l'algo lorsque la solution ne s'améliore pas au bout d'une itération
	private double gain;
	

	public LocalSearch(Instance instance) throws Exception {
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
		this.gain=0;
		
	}
	
	public LocalSearch(Instance instance, ArrayList<Integer> ini) throws Exception {
		this.instance = instance;
		this.temp= new int[this.instance.getNbCities()];
		this.solution= new int[this.instance.getNbCities()];
		this.ini=new int[this.instance.getNbCities()];
		for (int i=0;i<instance.getNbCities();i++) {
			this.ini[i]=ini.get(i);
			this.solution[i]=ini.get(i);
		}
		this.gain=0;
	}
	
	public void swap(int[] tab, int i,int j) {
		int a=tab[i];
		int b=tab[j];
		tab[i]=b;
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
	
	public void setSolution(int index, int val) {
		this.solution[index]=val;
	}
	
	public void swapSolution(int i1,int i2) {
		this.swap(this.solution, i1, i2);
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
	
	public double getGain() {
		return this.gain;
	}
	
	public void setGain(double g) {
		this.gain=g;
	}

	public double distance(int[] tab) throws Exception {
		double d=0;
		for (int i=0;i<tab.length-1;i++) {
			d=d+this.instance.getDistances(tab[i],tab[i+1]);
		}
		d+=this.instance.getDistances(tab[0], tab[tab.length-1]);
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
		int n = this.getInstance().getNbCities();

		for (int i=index+1;i<this.getInstance().getNbCities();i++) {
			this.setTemp(this.getIni());
			
			double g1=this.getInstance().getDistances(this.getIni()[(n+index-1)%n],this.getIni()[index])
					+this.getInstance().getDistances(this.getIni()[index], this.getIni()[(index+1)%n]);
			double d1=this.getInstance().getDistances(this.getIni()[(n+i-1)%n],this.getIni()[i])
					+this.getInstance().getDistances(this.getIni()[i], this.getIni()[(i+1)%n]);
			//distance au voisinage des points encore non swapés
			
			this.swap(this.temp, i, index);

			double g2=this.getInstance().getDistances(this.getTemp()[(n+index-1)%n],this.getTemp()[index])
					+this.getInstance().getDistances(this.getTemp()[index], this.getTemp()[(index+1)%n]);
			double d2=this.getInstance().getDistances(this.getTemp()[(n+i-1)%n],this.getTemp()[i])
					+this.getInstance().getDistances(this.getTemp()[i], this.getTemp()[(i+1)%n]);
			//distance au voisinage des points swapés		
			
			//if (this.distance(this.getTemp())<this.distance(this.getSolution())) {
			if (g1+d1-g2-d2>this.getGain()) { 
			//plus la somme est importante plus g2 et d2 son petits donc plus la distance de la nvlle sol est courte
				this.setSolution(this.getTemp());
				this.setGain(g1+d1-g2-d2);
			}
			

		}
	}
	
	public void swapIteration() throws Exception {
		for (int i=0; i<this.getInstance().getNbCities();i++) {
			this.swapCycle(i);
		}		
	}

	public void setInitial() {
		this.setIni(this.getSolution());
	}
	
	// on vérifie que pendant une itération la solution finale de l'itération à changer de la solution initiale
	public boolean iniEqualsSol() throws Exception { 
		return (this.distance(this.getIni())==this.distance(this.getSolution()));
	}
	
	public static void main(String[] args) throws Exception {
		boolean testEgalite=true; 
		long t0;
		long t1=0;
		int k=0;
		t0=System.currentTimeMillis();
		Instance graph = new Instance("instances/rat575.tsp",0);
		LocalSearch ls = new LocalSearch(graph);
		System.out.println("Solution initiale : "+ls.tostring(ls.getIni()));
		System.out.println("Distance initiale : "+ls.distance(ls.getIni()));
		
		do {
			ls.swapIteration();
			ls.setGain(0);
			System.err.println(ls.distance(ls.getSolution()));
			
			if (ls.iniEqualsSol()) {
				testEgalite=false;
			}
			//System.out.println(k);
			k++;
			ls.setInitial();
			t1=System.currentTimeMillis();
		} while (testEgalite && (t1-t0)<20000); /* condition d'arrêt de l'algo
		un tour de boucle correspond à une itération de l'algo, O(n) */
		
		System.out.println("Solution finale : "+ls.tostring(ls.getSolution()));
		System.out.println("Distance finale : "+ls.distance(ls.getSolution()));
		System.out.println("Durée totale d'exécution : "+(t1-t0)+" ms");
		System.out.println("Durée d'une itération : "+(t1-t0)/k+ "ms");
		System.out.println("Nombre d'itération : "+k);
		System.out.println("Arrêt de l'algo car ini==sol : "+!testEgalite);
	}
}
