package kana;

import java.util.ArrayList;

public class CharacterPool {
	private ArrayList<Character> allCharsList;
	private ArrayList<Character> fullPool;
	
	public CharacterPool(ArrayList<Character> par_allCharsList) {
		allCharsList = par_allCharsList;
		fullPool = new ArrayList<Character>();
	}
	
	public void addToPool(Character par_thisChar) {
		//System.out.println("Adding "+par_thisChar.getKana() + " " +par_thisChar.getIndex() +"...");

		fullPool.add(par_thisChar);
	}
	
	public ArrayList<Character> getFullPool() {
		return fullPool;
	}
}
