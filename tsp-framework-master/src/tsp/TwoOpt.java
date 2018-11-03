package tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import tsp.gui.TSPGUI;

public class TwoOpt extends LocalSearch{

	/**
	 * Créer une instance de TwoOpt, on génère aléatoirement un chemin que l'on stock dans solution et dans ini
	 * C'est sur cet objet qu'on va effectuer les différentes opérations
	 * @param instance contenant toutes les informations du problème
	 * @throws Exception
	 */
	public TwoOpt(Instance instance) throws Exception {
		super(instance);
	}
	
	/**
	 * Créer une instance de TwoOpt, cette fois-ci le chemin initial 
	 * est donné en entrée et non pas générer aléatoirement
	 * @param instance contenant toutes les données du problème
	 * @param ini : chemin initial choisi arbitrairement
	 * @throws Exception
	 */
	public TwoOpt(Instance instance, ArrayList<Integer> ini) throws Exception {
		super(instance,ini);
	}
	
	/**
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
				int[] tempon=new int[this.getInstance().getNbCities()];
				for (int j=index;j<=i;j++) {
					int a=this.getSolution()[i+index-j];
					tempon[j]=a;
				}
				for (int j=index;j<=i;j++) {
					this.setSolution(j,tempon[j]);
				}
			}
		}
	}
	
	/**
	 * On réalise une itération complète de 2-opt sur le chemin initial 'ini'
	 * @throws Exception
	 */
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
		Instance graph = new Instance("instances/brazil58.tsp",1);
		TwoOpt ls = new TwoOpt(graph);
		System.err.println("Solution initiale : "+ls.tostring(ls.getIni()));
		System.err.println("Distance initiale : "+ls.distance(ls.getIni()));
		
		int r=0;
		int n=ls.getInstance().getNbCities();
		int[] sol1=new int[n];
		do {
			
			ls.twoOptIteration();
			System.err.println(ls.distance(ls.getSolution()));
			
			if (ls.iniEqualsSol()) {
				testEgalite=false;
				System.err.println("Il y a égalité");
				System.err.println("");
				r++;
			}
			if (r==1) {
				ls.setTemp(ls.getSolution());
				System.err.println("Solution actuelle : "+ls.distance(ls.getTemp()));
				System.err.println("On balance une série de swap pour sortir de l'extremum local");
				System.err.println("");
				for (int i=0;i<15;i++) {
					ls.swapSolution((int)(Math.random()*(n-1)), (int)(Math.random()*(n-1)));
				}
				testEgalite=true;
				r++;
			}
			

			k++;
			ls.setInitial();
			
			t1=System.currentTimeMillis();
		} while (testEgalite && (t1-t0)<20000); /* condition d'arrêt de l'algo
		un tour de boucle correspond à une itération de l'algo, O(n) */

		if (ls.distance(ls.getTemp())<ls.distance(ls.getSolution())) {
			System.err.println("La 1ere solution vaut : "+ls.distance(ls.getTemp()));
			System.err.println("La 2nd solution vaut : "+ls.distance(ls.getSolution()));
			System.err.println("La première solution était meilleure");
			System.err.println("");
			ls.setSolution(ls.getTemp());		
		}
		
		System.err.println("Solution finale : "+ls.tostring(ls.getSolution()));
		System.err.println("Distance finale : "+ls.distance(ls.getSolution()));
		System.err.println("Durée totale d'exécution : "+(t1-t0)+" ms");
		System.err.println("Durée d'une itération : "+(t1-t0)/k+ "ms");
		System.err.println("Nombre d'itération : "+k);
		System.err.println("Arrêt de l'algo car ini==sol : "+!testEgalite);
		
		Solution sol = new Solution(graph);
		for (int i=0;i<ls.getSolution().length;i++) {
			sol.setCityPosition(ls.getSolution()[i], i);
		}
		sol.setCityPosition(ls.getSolution()[0], ls.getSolution().length);
		//TSPGUI gui = new TSPGUI(sol);
	}
	
}
