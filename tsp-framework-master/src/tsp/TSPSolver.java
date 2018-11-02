package tsp;

import java.util.ArrayList;

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
		
		int nbIndividus = 90;

		Population population = new Population(nbIndividus,this.m_instance);

		int nbIteElitistes=4000;
		int nbIteAleatoires=4000;
		double seuilMutation = 0.15;
        double seuilOptimisation = 0.5;
        
        int index=0;

		do {
			
			//Iterations avec une s�lection des parents �litiste 
			if (index < nbIteElitistes)  {
				System.err.println("iteration:"+index);
				ArrayList<Individu> parents = population.selectionElitiste();
				ArrayList<Individu> enfant = population.crossoverOX(parents.get(0),parents.get(1));
			    double alea = Math.random();
				if(alea<seuilMutation) {
					enfant.get(0).mutation();
					enfant.get(1).mutation();
				}
				double aleaBis = Math.random();
				if(aleaBis<seuilOptimisation) {
					enfant.get(0).optimisation();
					enfant.get(1).optimisation();
				}
				population.insertion(enfant.get(0));
				population.insertion(enfant.get(1));
				System.err.println("valeur: -----"+population.getBest().getValeur()+"----");
			
			//It�rations avec une s�lection des parents al�atoire
		    } else if (index<nbIteAleatoires+nbIteElitistes) {
		    	
				System.err.println("iteration:"+index);
				ArrayList<Individu> parents = population.selectionAleatoire();
				ArrayList<Individu> enfant = population.crossoverOX(parents.get(0),parents.get(1));
			    double alea = Math.random();
				if(alea<seuilMutation) {
					enfant.get(0).mutation();
					enfant.get(1).mutation();
				}
				double aleaBis = Math.random();
				if(aleaBis<seuilOptimisation) {
					enfant.get(0).optimisation();
					enfant.get(1).optimisation();
				}
				population.insertion(enfant.get(0));
				population.insertion(enfant.get(1));
			
				System.err.println("valeur: -----"+population.getBest().getValeur()+"----");	
			}
			
			spentTime = System.currentTimeMillis() - startTime;
			index++;
		} while(spentTime < (this.m_timeLimit * 1000 - 100) && index<nbIteAleatoires+nbIteElitistes );
		
		for (int i=0;i<m_instance.getNbCities();i++) {
			this.m_solution.setCityPosition(population.getBest().getOrdreVisite().get(i), i);
		}
		this.m_solution.setCityPosition(population.getBest().getOrdreVisite().get(0), this.m_instance.getNbCities());
		System.err.println("");
		System.err.println("Nombre d'itérations/reproduction : "+index);
		System.err.println("Durée d'une itération : "+spentTime/index+" ms");
		System.err.println("Durée d'éxécution : "+spentTime+" ms"+"\n");
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