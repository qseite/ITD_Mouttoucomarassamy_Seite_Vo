package tsp;

public class TesteurFourmi {
	
	public static void main(String[] args) {
		/*long startTime = System.currentTimeMillis();
		long spentTime = 0;
		Piste piste = new Piste(this.getInstance());
		double index=0;
		boolean sameWay = false;
		
		int rien =0;
		
		do {
			for (int i=0;i<piste.getFourmis().size();i++) {
				piste.getFourmi(i).ajouterVillesVisitee(i%m_instance.getNbCities());
			}

			
			//this.getInstance().setHeuristic();
			for (Fourmi four : piste.getFourmis()) {
				for (int i=0;i<m_instance.getNbCities()-1;i++) {
					four.setProchaineVille();	
				}
				four.setLongueur();
			}

			piste.majBestSolution();
			if (Piste.ELITISTE) {
				piste.setElitiste(Piste.NOMBRE_ELITISTE);
			}
			piste.majPheromone();
			piste.resetFourmis();
			
			spentTime = System.currentTimeMillis() - startTime;
			index++;	
			System.err.println(piste.getBestLongueur());
		} while(spentTime < (Piste.MAX_TIME * 1000 - 100) && index<50 && !sameWay);
		
		for (int i=0;i<m_instance.getNbCities();i++) {
			this.m_solution.setCityPosition(piste.getSolutionTemp().get(i), i);
		}
		this.m_solution.setCityPosition(piste.getSolutionTemp().get(0), m_instance.getNbCities());
		System.err.println(index+" itérations pour obtenir ce résultat");
		System.err.println(spentTime/index+" ms par itération");*/
	}
}
