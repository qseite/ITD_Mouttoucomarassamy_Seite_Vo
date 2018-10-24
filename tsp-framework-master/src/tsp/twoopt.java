package tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class twoopt extends LocalSearch{

	public twoopt(Instance instance) throws Exception {
		super(instance);
	}
	public int[] cavacouper(int[] tab,int gauche,int droite) {
		ArrayList<Integer> coupe= new ArrayList<Integer>();
		ArrayList<Integer> tablist= new ArrayList<Integer>();
		for(int i=0;i<tab.length;i++) {
			tablist.add(tab[i]);
		}
		coupe=(ArrayList<Integer>) tablist.subList(gauche, droite+1);
		tablist.removeAll(coupe);
		Collections.reverse(coupe);
		tablist.addAll(gauche,coupe);
		for(int i=0;i<tab.length;i++) {
			tab[i]=tablist.get(i);
		} 
		return tab;
	}
	
}
