package tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TwoOpt extends LocalSearch{
	
	private boolean test=true;

	public TwoOpt(Instance instance) throws Exception {
		super(instance);
	}
	
	public int[] cavacouper(int[] tab,int gauche,int droite) {
		ArrayList<Integer> coupe= new ArrayList<Integer>();
		ArrayList<Integer> tablist= new ArrayList<Integer>();
		for(int i=0;i<tab.length;i++) {
			tablist.add(tab[i]);
		}
		coupe=(ArrayList<Integer>) tablist.subList(gauche, droite+1);
		tablist.removeAll(coupe);
		Collections.reverse(coupe);
		tablist.addAll(gauche,coupe);
		for(int i=0;i<tab.length;i++) {
			tab[i]=tablist.get(i);
		} 
		return tab;
	}
	
	public void setTest(boolean b) {
		this.test=b;
	}
	
	/*
	 * @param int index : on réalise une série de 2-opt entre index et les villes [index+1;n[
	 * Réalise une série de renversement 2-opt entre 'index' et 'nombreDeVilles'
	 */
	public void twoOpt(int index) throws Exception {
		int n=this.getInstance().getNbCities();
		for (int i=index+1;i<this.getInstance().getNbCities();i++) {
			
			double g1=this.getInstance().getDistances(this.getSolution()[(n+index-1)%n], this.getSolution()[index]);
			double d1=this.getInstance().getDistances(this.getSolution()[i], this.getSolution()[(i+1)%n]);
			double g2=this.getInstance().getDistances(this.getSolution()[(n+index-1)%n],this.getSolution()[i]);
			double d2=this.getInstance().getDistances(this.getSolution()[index],this.getSolution()[(i+1)%n]);
			
			if (g1+d1-g2-d2>0) {
				//this.setTemp(this.getIni());
				
				System.err.println(index);
				System.out.println("g1 "+g1);
				System.out.println("d1 "+d1);
				System.err.println(i);
				System.out.println("g2 "+g2);
				System.out.println("d2 "+d2);
				System.out.println(this.tostring(this.getSolution()));
				System.out.println(this.distance(this.getSolution()));
				
				int[] tempon=new int[this.getInstance().getNbCities()];
				for (int j=index;j<=i;j++) {
					int a=this.getSolution()[i+index-j];
					tempon[j]=a;
				}
				for (int j=index;j<=i;j++) {
					this.setSolution(j,tempon[j]);
				}
				
				System.err.println(this.tostring(this.getSolution()));
				System.err.println(this.distance(this.getSolution()));
				System.out.println("");

				
				//this.setGain(g1+d1-g2-d2);
				this.setTest(true);
			} else {
				this.setTest(false);
			}
		}
	}
	
	public void twoOptIteration() throws Exception {
		for (int i=0;i<this.getInstance().getNbCities();i++) {
			this.twoOpt(i);
		}
	}
	
	public static void main(String[] args) throws Exception {
		boolean testEgalite=true; 
		long t0;
		long t1=0;
		int k=0;
		t0=System.currentTimeMillis();
		Instance graph = new Instance("instances/d657.tsp",0);
		TwoOpt ls = new TwoOpt(graph);
		System.out.println("Solution initiale : "+ls.tostring(ls.getIni()));
		System.out.println("Distance initiale : "+ls.distance(ls.getIni()));
		
		do {
			ls.twoOptIteration();
			//ls.setGain(0);
			//System.err.println(ls.distance(ls.getSolution()));
			/*
			if (ls.iniEqualsSol()) {
				testEgalite=false;
			}*/
			//System.out.println(k);
			k++;
			//ls.setInitial();
			t1=System.currentTimeMillis();
		} while (testEgalite && (t1-t0)<20000 && k<5); /* condition d'arrêt de l'algo
		un tour de boucle correspond à une itération de l'algo, O(n) */
		
		System.out.println("Solution finale : "+ls.tostring(ls.getSolution()));
		System.out.println("Distance finale : "+ls.distance(ls.getSolution()));
		System.out.println("Durée totale d'exécution : "+(t1-t0)+" ms");
		System.out.println("Durée d'une itération : "+(t1-t0)/k+ "ms");
		System.out.println("Nombre d'itération : "+k);
		System.out.println("Arrêt de l'algo car ini==sol : "+!testEgalite);
		//graph.print(System.err);
	}
	
}
