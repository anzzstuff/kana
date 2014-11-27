package kana;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
	private ArrayList<Integer> selectedArray;
	private Character currentQuestionAnswer;
	private Character previousQuestionAnswer;
	private ArrayList<Character> allCharsList;
	private ArrayList<Character> fullPool;
	
	public Game(ArrayList<Character> par_allCharsList) {
		allCharsList=par_allCharsList;
	}

	public void initializeGame(ArrayList<Integer> par_selectedArray) {
		selectedArray = par_selectedArray;
	}
	

	public void initializePool() {
		fullPool = new ArrayList<Character>();
		
		for(int i=0;i<allCharsList.size();i++) { // Loopataan läpi jokainen merkki
			Character thisChar = allCharsList.get(i);
			
			// Tarkistetaan onko merkin indeksi valittujen checkboxien rangeissa ja lisätään pooliin jos on
			if((thisChar.getIndex()>=0 && thisChar.getIndex()<=4 && selectedArray.contains(0)) ||
				(thisChar.getIndex()>=5 && thisChar.getIndex()<=9 && selectedArray.contains(1)) || 
				(thisChar.getIndex()>=10 && thisChar.getIndex()<=14 && selectedArray.contains(2)) || 
				(thisChar.getIndex()>=15 && thisChar.getIndex()<=19 && selectedArray.contains(3)) || 
				(thisChar.getIndex()>=20 && thisChar.getIndex()<=24 && selectedArray.contains(4)) || 
				(thisChar.getIndex()>=25 && thisChar.getIndex()<=29 && selectedArray.contains(5)) || 
				(thisChar.getIndex()>=30 && thisChar.getIndex()<=34 && selectedArray.contains(6)) || 
				(thisChar.getIndex()>=35 && thisChar.getIndex()<=37 && selectedArray.contains(7)) || 
				(thisChar.getIndex()>=38 && thisChar.getIndex()<=42 && selectedArray.contains(8)) || 
				(thisChar.getIndex()>=43 && thisChar.getIndex()<=45 && selectedArray.contains(9)) 
				)
				fullPool.add(thisChar);

		}
	}
	
	public ArrayList<Character> newQuestion() {
		ArrayList<Character> tempPool = new ArrayList<Character>(fullPool);
		tempPool.remove(previousQuestionAnswer);
		Collections.shuffle(tempPool);
		int k = tempPool.size();
		tempPool.subList(4,k).clear();
		
		currentQuestionAnswer=tempPool.get(0);
		previousQuestionAnswer=currentQuestionAnswer;
		
		return tempPool;
	}
	
	
}
