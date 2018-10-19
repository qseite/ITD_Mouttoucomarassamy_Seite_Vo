package tsp;

import java.util.ArrayList;

public class Individu {
	
	private Instance g_instance;
	private ArrayList<Integer> ordreVisite;
	

	
	public Individu(Instance g_instance, ArrayList<Integer> ordreVisite) {
		this.g_instance=g_instance;
		this.ordreVisite=ordreVisite;
		
	}
	
	public ArrayList<Integer> getOrdreVisite(){
		return this.ordreVisite;
	}

	public double getValeur() throws Exception {
		double res=0;
		int index=0;
		while(index<this.ordreVisite.size()-1) {
			res+=this.g_instance.getDistances(ordreVisite.get(index),ordreVisite.get(index+1));
			index++;
		}res+=this.g_instance.getDistances(this.ordreVisite.get(this.ordreVisite.size()-1),0);
		return res;
	}
	
	public Individu crossover(Individu ind1, Individu ind2) {
		ArrayList<Integer> ordre = new ArrayList<Integer>();
		int taille = ind1.getOrdreVisite().size();
		int alea = ((int)Math.random()*taille)-1;
		int index=0;
		while (index<alea) {
			ordre.add(ind1.getOrdreVisite().get(index));
		}
		
		int i=index+1;
		int tailleIntermediaire=ordre.size();
		while(index<taille) {
			int compt=0;
			boolean present = false;
			while (compt<tailleIntermediaire) {
				if(ind2.getOrdreVisite().get(i)==ordre.get(compt)) {
					present=true;
				}
			}if (present==false) {
				ordre.add(ind2.getOrdreVisite().get(i));
			}
			if (i<taille) {
				i=i+1;
			}else {
				i=0;
			}index++;
		}
		Individu res = new Individu(this.g_instance,ordre);
		return res;
	}
}
