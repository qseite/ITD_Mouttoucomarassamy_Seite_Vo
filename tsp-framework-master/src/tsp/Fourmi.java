package tsp;

import java.util.ArrayList;

public class Fourmi {
	private ArrayList<Integer> villesNonVisitees;
	private ArrayList<Integer> villesVisitees;
	private Instance m_instance;
	private double longueur;
	private int numeroFourmi;
	private boolean elite;
	
	public Fourmi(Instance m_instance, int num) {
		this.numeroFourmi=num;
		this.m_instance=m_instance;
		this.villesNonVisitees = new ArrayList<Integer>();
		this.villesNonVisitees = new ArrayList<Integer>(m_instance.getListeVilles());
		this.villesVisitees = new ArrayList<Integer>();
		this.elite=false;
		
		/*int availableProcessors = Runtime.getRuntime().availableProcessors();
		for(int i=0; i<availableProcessors; i++) {
			final int i2=i;
			new Thread( () -> {
				for(int j=0; j<10; j++) {
					System.out.println("Le coeur " + i2 + " affiche " + j);
				}
			}).start();
		}*/
	}
	
	public int getNumeroFourmi() {
		return this.numeroFourmi;
	}

	public ArrayList<Integer> getVillesNonVisitees() {
		return this.villesNonVisitees;
	}
	
	public ArrayList<Integer> getVillesVisitees() {
		return this.villesVisitees;
	}
	
	public double getLongueur() {
		return this.longueur;
	}
	
	public void addLongueur(double l) {
		this.longueur+=l;
	}
	
	public Instance getInstance() {
		return this.m_instance;
	}
	
	public boolean getElite() {
		return this.elite;
	}
	
	public void setElite(boolean t) {
		this.elite=t;
	}
	
	public void resetFourmi() {
		this.villesNonVisitees = new ArrayList<Integer>(this.getInstance().getListeVilles());
		this.villesVisitees = new ArrayList<Integer>();
	}
	
	public void ajouterVillesVisitee(int numeroVille) throws Exception {
		/*if (this.getDerniereVilleVisitee()!=-1) {
			this.longueur+=this.getInstance().getDistances(numeroVille, this.getDerniereVilleVisitee());
		}*/
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
		double num = Math.pow(this.getInstance().getPheromones()[i][j], TSPSolver.ALPHA)
				*Math.pow(1.0/this.m_instance.getDistances(i, j), TSPSolver.BETA);
		double den = 0;
		for (int numVille : this.getVillesNonVisitees()) {
			den+=Math.pow(this.getInstance().getPheromones()[i][numVille], TSPSolver.ALPHA)
					*Math.pow(1.0/this.m_instance.getDistances(i, numVille), TSPSolver.BETA);
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
	
	public void majPheromones() {
		for (int i=this.getVillesVisitees().size()-1;i>0;i--) {
			int index1=this.getVillesVisitees().get(i);
			int index2=this.getVillesVisitees().get(i-1);
			if (this.elite) {
				this.getInstance().getPheromones()[index1][index2]+=(TSPSolver.NOMBRE_ELITISTE*TSPSolver.Q)/this.getLongueur();
				this.getInstance().getPheromones()[index2][index1]+=(TSPSolver.NOMBRE_ELITISTE*TSPSolver.Q)/this.getLongueur();
				this.setElite(false);
			} else {
				this.getInstance().getPheromones()[index1][index2]+=TSPSolver.Q/this.getLongueur();
				this.getInstance().getPheromones()[index2][index1]+=TSPSolver.Q/this.getLongueur();
			}
		}
	}
}
