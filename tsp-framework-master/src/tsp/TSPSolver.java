package tsp;

/**
 * 
 * This class is the place where you should enter your code and from which you can create your own objects.
 * 
 * The method you must implement is solve(). This method is called by the programmer after loading the data.
 * 
 * The TSPSolver object is created by the Main class.
 * The other objects that are created in Main can be accessed through the following TSPSolver attributes: 
 * 	- #m_instance :  the Instance object which contains the problem data
 * 	- #m_solution : the Solution object to modify. This object will store the result of the program.
 * 	- #m_timeLimit : the maximum time limit (in seconds) given to the program.
 *  
 * @author Damien Prot, Fabien Lehuede, Axel Grimault
 * @version 2017
 * 
 */
public class TSPSolver {

	// -----------------------------
	// ----- ATTRIBUTS -------------
	// -----------------------------

	/**
	 * The Solution that will be returned by the program.
	 */
	private Solution m_solution;

	/** The Instance of the problem. */
	private Instance m_instance;

	/** Time given to solve the problem. */
	private long m_timeLimit;
	
	public static int NOMBRE_FOURMI=51;
	public static double ALPHA=1; 
	public static double BETA=2; 
	public static double Q=100; 
	public static double P=0.5;
	public static boolean ELITISTE=false;
	public static int NOMBRE_ELITISTE=10;
	public static int MAX_TIME=60;
	public static double CONSTANTE_PHEROMONE=0.1;
	// -----------------------------
	// ----- CONSTRUCTOR -----------
	// -----------------------------

	/**
	 * Creates an object of the class Solution for the problem data loaded in Instance
	 * @param instance the instance of the problem
	 * @param timeLimit the time limit in seconds
	 */
	public TSPSolver(Instance instance, long timeLimit) {
		m_instance = instance;
		m_solution = new Solution(m_instance);
		m_timeLimit = timeLimit;
	}

	// -----------------------------
	// ----- METHODS ---------------
	// -----------------------------

	/**
	 * **TODO** Modify this method to solve the problem.
	 * 
	 * Do not print text on the standard output (eg. using `System.out.print()` or `System.out.println()`).
	 * This output is dedicated to the result analyzer that will be used to evaluate your code on multiple instances.
	 * 
	 * You can print using the error output (`System.err.print()` or `System.err.println()`).
	 * 
	 * When your algorithm terminates, make sure the attribute #m_solution in this class points to the solution you want to return.
	 * 
	 * You have to make sure that your algorithm does not take more time than the time limit #m_timeLimit.
	 * 
	 * @throws Exception may return some error, in particular if some vertices index are wrong.
	 */
	public void solve() throws Exception
	{
		m_solution.print(System.err);
		// Example of a time loop
		long startTime = System.currentTimeMillis();
		long spentTime = 0;
		long t0=0;
		long t1=0;
		double index=0;
		boolean sameWay = false;
		
		do {
			t0=System.currentTimeMillis();
			for (int i=0;i<this.getInstance().getFourmis().size();i++) {
				this.getInstance().getFourmi(i).ajouterVillesVisitee(i%m_instance.getNbCities());
			}
			//System.err.println("Liste des villes : "+this.getInstance().getListeVilles());
			//System.err.println("Villes visitées par f8 : "+this.getInstance().getFourmi(8).getVillesVisitees());
			//System.err.println("Villes non visitées par f8 : "+this.getInstance().getFourmi(8).getVillesNonVisitees());
			//System.err.println("Quantité de phéromone entre v8 et v1 : "+this.getInstance().getPheromones()[8][1]);
			//System.err.println("Distance entre v8 et v1 : "+m_instance.getDistances(8,1));
			//System.err.println("Proba pour f8 d'aller de v8 à v1 : "+this.getInstance().getFourmi(8).getProbaIaJ(8, 1));
			//System.err.println("Derniere ville visitée par f8 : "+this.getInstance().getFourmi(8).getDerniereVilleVisitee());
			
			for (Fourmi four : this.getInstance().getFourmis()) {
				for (int i=0;i<m_instance.getNbCities()-1;i++) {
					four.setProchaineVille();	
				}
				four.setLongueur();
	
			}

			this.getInstance().majBestSolution();
			if (TSPSolver.ELITISTE) {
				this.getInstance().setElitiste(NOMBRE_ELITISTE);
			}
			this.getInstance().evaporation();
			for (int i=0;i<this.getInstance().getFourmis().size();i++) {
				this.getInstance().getFourmi(i).majPheromones();
				this.getInstance().getFourmi(i).resetFourmi();
			}
			
			spentTime = System.currentTimeMillis() - startTime;
			index++;	
			t1=System.currentTimeMillis();
			//System.err.println(this.getInstance().getBestLongueur());
			System.err.println((t1-t0)+" ms");
		} while(spentTime < (TSPSolver.MAX_TIME * 1000 - 100) && index<100 && !sameWay);
		
		for (int i=0;i<m_instance.getNbCities();i++) {
			this.m_solution.setCityPosition(this.getInstance().getSolutionTemp().get(i), i);
		}
		this.m_solution.setCityPosition(this.getInstance().getSolutionTemp().get(0), m_instance.getNbCities());
		System.err.println(index+" itérations pour obtenir ce résultat");
		System.err.println(spentTime/index+" ms par itération");

	}

	// -----------------------------
	// ----- GETTERS / SETTERS -----
	// -----------------------------

	/** @return the problem Solution */
	public Solution getSolution() {
		return m_solution;
	}

	/** @return problem data */
	public Instance getInstance() {
		return m_instance;
	}

	/** @return Time given to solve the problem */
	public long getTimeLimit() {
		return m_timeLimit;
	}

	/**
	 * Initializes the problem solution with a new Solution object (the old one will be deleted).
	 * @param solution : new solution
	 */
	public void setSolution(Solution solution) {
		this.m_solution = solution;
	}

	/**
	 * Sets the problem data
	 * @param instance the Instance object which contains the data.
	 */
	public void setInstance(Instance instance) {
		this.m_instance = instance;
	}

	/**
	 * Sets the time limit (in seconds).
	 * @param time time given to solve the problem
	 */
	public void setTimeLimit(long time) {
		this.m_timeLimit = time;
	}

}