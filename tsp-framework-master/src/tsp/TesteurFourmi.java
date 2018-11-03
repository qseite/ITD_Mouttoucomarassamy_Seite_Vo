package tsp;

public class TesteurFourmi {
	
	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		long spentTime = 0;
		Instance graph = new Instance("instances/eil51.tsp",0);
		Piste piste = new Piste(graph);
		double index=0;
		boolean sameWay = false;
		
		int rien =0;
		
		do {
			for (int i=0;i<piste.getFourmis().size();i++) {
				piste.getFourmi(i).ajouterVillesVisitee(i%graph.getNbCities());
			}

			
			//this.getInstance().setHeuristic();
			for (Fourmi four : piste.getFourmis()) {
				for (int i=0;i<graph.getNbCities()-1;i++) {
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
		} while(spentTime < (Piste.MAX_TIME * 1000 - 100));
		

		System.err.println(index+" itérations pour obtenir ce résultat");
		System.err.println(spentTime/index+" ms par itération");
		System.err.println("Durée d'éxécution : "+spentTime+" ms");
	}
}
