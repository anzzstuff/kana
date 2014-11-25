package kana;

import java.util.ArrayList;

public class Game extends Kana {
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> selectedArray;
	private CharacterPool pool;
	
	public Game(ArrayList<Integer> par_selectedArray) {
		selectedArray = par_selectedArray;
		
		initializeGame();
		initializePool();
	}

	public void initializeGame() {
		
	}
	

	public void initializePool() {
		pool = new CharacterPool(allCharsList);
		
		for(int i=0;i<allCharsList.size();i++) { // Loopataan läpi jokainen merkki
			Character thisChar = allCharsList.get(i);
			System.out.println(thisChar.getKana() + " " +thisChar.getIndex());
			
			// Tarkistetaan onko merkin indeksi valittujen checkboxien rangeissa ja lisätään pooliin jos on
			if((thisChar.getIndex()>=0 && thisChar.getIndex()<=4 && selectedArray.contains(0)) ||
				(thisChar.getIndex()>=5 && thisChar.getIndex()<=9 && selectedArray.contains(1))
				)
				pool.addToPool(thisChar);

		}

	}
}
