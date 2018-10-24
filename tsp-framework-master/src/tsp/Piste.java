package tsp;

import java.util.ArrayList;

public class Piste {
	private Instance m_instance;
	private double[][] pheromoneSurArc;
	private double bestLongueur;
	private ArrayList<Integer> solutionTemp;
	private ArrayList<Fourmi> listeFourmis;
	private int nombreFourmi;


	// -----------------------------
	// ----- CONSTRUCTOR -----------
	// -----------------------------
	
	public Piste(Instance instance) {
		this.m_instance=instance;
		this.pheromoneSurArc = new double[instance.getNbCities()][instance.getNbCities()];
		this.bestLongueur=-1;
		this.solutionTemp = new ArrayList<Integer>();
		this.listeFourmis = new ArrayList<Fourmi>();
		for (int i=0; i<instance.getNbCities();i++) {
			for (int j=0; j<=i;j++) {
				this.pheromoneSurArc[i][j]=TSPSolver.c_ini_pheromone;
				this.pheromoneSurArc[j][i]=this.pheromoneSurArc[i][j];
			}
		}
		this.nombreFourmi=TSPSolver.NOMBRE_FOURMI;
		for (int i=0; i<this.nombreFourmi;i++) {
			this.listeFourmis.add(new Fourmi(this.m_instance, this));
		}
	}
	
	// -----------------------------
	// ----- METHODS ---------------
	// -----------------------------
	
	public Instance getInstance() {
		return this.m_instance;
	}
	
	public Fourmi getFourmi(int index) {
		return this.listeFourmis.get(index);
	}
	
	public ArrayList<Fourmi> getFourmis() {
		return this.listeFourmis;
	}
	
	public double[][] getPheromoneSurArc() {
		return this.pheromoneSurArc;
	}
	
	public double getBestLongueur() {
		return this.bestLongueur;
	}
	
	public ArrayList<Integer> getSolutionTemp() {
		return this.solutionTemp;
	}
	
	public void majPheromone() {
		for (int i=0;i<this.getInstance().getNbCities();i++) {
			for (int j=0;j<=i;j++) {
				this.getPheromoneSurArc()[i][j]=TSPSolver.P*this.getPheromoneSurArc()[i][j];
				for (Fourmi four : this.getFourmis()) {
					if (!four.getElitiste()) {
						this.getPheromoneSurArc()[i][j]
								+=four.getPassage()[i][j]*(TSPSolver.Q/four.getLongueur());
					} else {
						this.getPheromoneSurArc()[i][j]
								+=four.getPassage()[i][j]*TSPSolver.COEF_ELITISTE*(TSPSolver.Q/four.getLongueur());
					}
				}
				this.getPheromoneSurArc()[j][i]=this.getPheromoneSurArc()[i][j];				
			}
		}
	}
	
	public void majBestSolution() {
		double best=this.getFourmi(0).getLongueur();
		Fourmi bestF=this.getFourmi(0);
		for (Fourmi four : this.getFourmis()) {
			if (four.getLongueur()<best) {
				best = four.getLongueur();
				bestF = four;
			}
		}
		if (this.getBestLongueur()<0) {
			this.bestLongueur=best;
			this.solutionTemp=bestF.getVillesVisitees();			
		} else if (this.getBestLongueur()>best) {
			this.bestLongueur=best;
			this.solutionTemp=bestF.getVillesVisitees();
		}

	}
	
	public void setElitiste(int nbr) {
		for (int i=0;i<nbr;i++) {
			double best=this.getFourmi(0).getLongueur();
			Fourmi bestF=this.getFourmi(0);
			for (int j=0; j<this.getFourmis().size();j++) {
				if (this.getFourmi(j).getLongueur()<best && !this.getFourmi(j).getElitiste()) {
					best = this.getFourmi(j).getLongueur();
					bestF = this.getFourmi(j);
				}
			}
			bestF.setElitiste(true);
			if (i==0) {
				if (this.getBestLongueur()<0) {
					this.bestLongueur=best;
					this.solutionTemp=bestF.getVillesVisitees();			
				} else if (this.getBestLongueur()>best) {
					this.bestLongueur=best;
					this.solutionTemp=bestF.getVillesVisitees();
				}
			}
		}
	}
	
	public void resetFourmis() {
		this.listeFourmis.clear();
		this.listeFourmis = new ArrayList<Fourmi>();
		for (int i=0; i<this.getInstance().getNbCities();i++) {
			this.listeFourmis.add(new Fourmi(this.getInstance(), this));
		}
	}
}
