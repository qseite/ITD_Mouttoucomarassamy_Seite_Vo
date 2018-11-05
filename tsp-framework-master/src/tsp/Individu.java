package tsp;

import java.util.ArrayList;

/**
 * Cette classe modélise un individu et son ADN
 * L'ADN correspond à la liste des villes visitées
 * Un gène correspond à la position d'une ville dans cette liste
 */

public class Individu {
	
	/** Contient les données du problème */
	private Instance g_instance;
	
	/** Mémorise l'ordre des villes visitées = l'adn de l'individu */
	private ArrayList<Integer> ordreVisite;  
	
	/**
	 * Constructeur d'un individu à partir d'une instance et d'une ArrayList<integer> représentant l'ordre des villes visitées
	 * @param g_instance
	 * @param ordreVisite
	 */
	public Individu(Instance g_instance, ArrayList<Integer> ordreVisite) {
		this.g_instance=g_instance;
		this.ordreVisite=ordreVisite;
	}
	
	/**
	 *  Getter pour ordreVisite
	 * @return l'ArrayList<Individu> représentant l'ordre des villes visitées
	 */
	public ArrayList<Integer> getOrdreVisite(){
		return this.ordreVisite;
	}
	
	/*Getter pour l'instance
	 * @return l'instance 
	 */
	public Instance getInstance() {
		return this.g_instance;
	}
	
	/**
	 * @return un double donnant la valeur du chemin de l'individu 
	 * @throws Exception
	 */
	public double getValeur() throws Exception {
		double res=0;
		for (int i=0;i<this.getOrdreVisite().size()-1;i++) {
			res+=this.g_instance.getDistances(this.getOrdreVisite().get(i),this.getOrdreVisite().get(i+1));
			
		}res+=this.g_instance.getDistances(this.getOrdreVisite().get(this.getOrdreVisite().size()-1),this.getOrdreVisite().get(0));
		return res;
	}

	/**
	 * @param aucun paramètre en entrée
	 * @return ne retourne rien, modifie directement l'individu :4-change non séquentiel ou double-bridge
	 * le parcours : 0 ... i i+1 ... j j+1 ... k k+1 ... l l+1 ... 
	 * est modifié en : 0...i k+1 ... l j+1 ... k i+1 ... j l+1 ...
	 * Les indices i,j,k et l sont choisis aléatoirement
	 */
	public void mutation() {
		int nombreVille=this.getOrdreVisite().size();
		int index1=(int)(Math.random()*nombreVille*0.25-1);//i
		int index2=index1+(int)(Math.random()*((nombreVille-index1)*0.33-1));//j
		int index3=index2+(int)(Math.random()*((nombreVille-index2)*0.5-1));//k
		int index4=index3+(int)(Math.random()*(nombreVille-index3-1));//l
		ArrayList<Integer> mutation = new ArrayList<Integer>();
		for (int i=0;i<index1;i++) {
			mutation.add(this.getOrdreVisite().get(i));
		}
		for (int i=index3;i<index4;i++) {
			mutation.add(this.getOrdreVisite().get(i));
		}
		for (int i=index2;i<index3;i++) {
			mutation.add(this.getOrdreVisite().get(i));
		}
		for (int i=index1;i<index2;i++) {
			mutation.add(this.getOrdreVisite().get(i));
		}
		for (int i=index4;i<nombreVille;i++) {
		    mutation.add(this.getOrdreVisite().get(i));
		}
		this.ordreVisite=mutation;
	}
	
	/**
	 * @param seuil, donne la probabilité de swaper les deux villes aléatoirement
	 * @return ne retourne rien, modifie directement l'individu en swapant deux de ses gènes aléatoirement
	 */
	public void mutationSwap(double seuil) {
		int index1 = (int)(Math.random()*this.getOrdreVisite().size()-1);
		int index2 = (int)(Math.random()*this.getOrdreVisite().size()-1);
		double alea = Math.random();
		if(alea<seuil) {
			int temp = this.getOrdreVisite().get(index1);
			this.getOrdreVisite().set(index1, this.getOrdreVisite().get(index2));
			this.getOrdreVisite().set(index2,temp);
		
		}
	}
	
	/**
	 * Applique l'algo 2-opt au chemin de l'individu
	 * @throws Exception
	 */
	public void optimisation() throws Exception {
		int n=this.getInstance().getNbCities();
		ArrayList<Integer> tempon=new ArrayList<Integer>();
		TwoOpt temp;
		temp=new TwoOpt(this.g_instance,this.getOrdreVisite()); 
	
		boolean testEgalite=true; 
		long t0;
		long t1=0;
		int k=0;
		t0=System.currentTimeMillis();
		do {
			temp.twoOptIteration();
			
			if (temp.iniEqualsSol()) {
				testEgalite=false;
			}
			k++;
			temp.setInitial();
			t1=System.currentTimeMillis();	
			
		} while (testEgalite && (t1-t0)<500);
		
		for (int j=0;j<n;j++) {
			tempon.add(temp.getSolution()[j]);
		}
		this.ordreVisite.clear();
		this.ordreVisite.addAll(tempon);
	}
}
