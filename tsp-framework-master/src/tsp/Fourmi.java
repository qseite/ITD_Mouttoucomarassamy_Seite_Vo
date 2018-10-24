package tsp;

import java.util.ArrayList;

public class Fourmi {
	private ArrayList<Integer> villesNonVisitees;
	private ArrayList<Integer> villesVisitees;
	private Instance m_instance;
	private double longueur;
	private int[][] passageSurArc;
	private boolean elitiste;
	private Piste piste;
	
	public Fourmi(Instance m_instance, Piste piste) {
		this.piste=piste;
		this.elitiste=false;
		this.m_instance=m_instance;
		this.villesNonVisitees = new ArrayList<Integer>();
		for (int i=0;i<this.m_instance.getNbCities();i++) {
			this.villesNonVisitees.add(i);
		}
		this.villesVisitees = new ArrayList<Integer>();
		this.passageSurArc=new int[m_instance.getNbCities()][m_instance.getNbCities()];
		for (int i=0; i<this.m_instance.getNbCities();i++) {
			for (int j=0; j<=i;j++) {
				this.passageSurArc[i][j]=0;
				this.passageSurArc[j][i]=0;
			}
		}
	}
	
	public Piste getPiste() {
		return this.piste;
	}
	
	public ArrayList<Integer> getVillesNonVisitees() {
		return this.villesNonVisitees;
	}
	
	public ArrayList<Integer> getVillesVisitees() {
		return this.villesVisitees;
	}
	
	public boolean getElitiste() {
		return this.elitiste;
	}
	
	public void setElitiste(boolean t) {
		this.elitiste=t;	
	}
	
	public int[][] getPassage() {
		return this.passageSurArc;
	}
	
	public double getLongueur() {
		return this.longueur;
	}
	
	public Instance getInstance() {
		return this.m_instance;
	}
	
	public void ajouterVillesVisitee(int numeroVille) {
		if (this.getDerniereVilleVisitee()!=-1) {
			this.getPassage()[this.getDerniereVilleVisitee()][numeroVille]=1;
		}
		this.villesVisitees.add(numeroVille);
		int index=this.villesNonVisitees.indexOf(numeroVille);
		this.villesNonVisitees.remove(index);
	}
	
	public int getDerniereVilleVisitee() {
		if (this.getVillesVisitees().size()==0) {
			return -1;
		} else {
			return this.getVillesVisitees().get(this.getVillesVisitees().size()-1);
		}
	}
	
	public void setProchaineVille() throws Exception {
		boolean test=false;
		double x = Math.random();
		double sommeDesProba=0;
		for (int k=0;k<this.getVillesNonVisitees().size() & !test;k++) {
			sommeDesProba+=this.getProbaIaJ(this.getDerniereVilleVisitee(),this.getVillesNonVisitees().get(k));
			if (x <= sommeDesProba) {
				this.ajouterVillesVisitee(this.getVillesNonVisitees().get(k));
				test=true;
			}
		}
	}
	
	public double getProbaIaJ(int i, int j) throws Exception {
		double num = Math.pow(this.getPiste().getPheromoneSurArc()[i][j], TSPSolver.ALPHA)
				*Math.pow(1.0/this.m_instance.getDistances(i, j), TSPSolver.BETA);
		/*double num=this.getInstance().getHeuristic()[i][j];*/
		double den = 0;
		for (int numVille : this.getVillesNonVisitees()) {
			den+=Math.pow(this.getPiste().getPheromoneSurArc()[i][numVille], TSPSolver.ALPHA)
					*Math.pow(1.0/this.m_instance.getDistances(i, numVille), TSPSolver.BETA);
			/*den+=this.getInstance().getHeuristic()[i][numVille];*/
		}
		return (num/den);
	}
	
	public void setLongueur() throws Exception {
		double l=0;
		int index=0;
		for (int i=0; i<this.getVillesVisitees().size()-1;i++) {
			l+=this.m_instance.getDistances(this.getVillesVisitees().get(i), this.getVillesVisitees().get(i+1));
			index=i+1;
		}
		l+=this.m_instance.getDistances(this.getVillesVisitees().get(0), 
				this.getVillesVisitees().get(index));
		this.longueur=l;
	}
}
