package kana;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.Random;

public class Game {
	private ArrayList<Integer> selectedArray;
	private Character currentQuestionAnswer;
	private Character previousQuestionAnswer;
	private ArrayList<Character> allCharsList;
	private ArrayList<Character> fullPool;

	public Game() {	
		initializeCharacters();
	}

	public void initializeGame(ArrayList<Integer> par_selectedArray) {
		selectedArray = par_selectedArray;
		initializePool();
	}

	public void initializeCharacters() {
		// Luodaan character oliot
		String[] kanaArray = {
				"あ","い","う","え","お",
				"か","き","く","け","こ",
				"さ","し","す","せ","そ",
				"た","ち","つ","て","と",
				"な","に","ぬ","ね","の",
				"は","ひ","ふ","へ","ほ",
				"ま","み","む","め","も",
				"や","ゆ","よ",
				"ら","り","る","れ","ろ",
				"わ","を","ん"
			};
		String[] romajiArray = {
				"a","i","u","e","o",
				"ka","ki","ku","ke","ko",
				"sa","shi","su","se","so",
				"ta","chi","tsu","te","to",
				"na","ni","nu","ne","no",
				"ha","hi","fu","he","ho",
				"ma","mi","mu","me","mo",
				"ya","yu","yo",
				"ra","ri","ru","re","ro",
				"wa","wo","n"
			};
		
		allCharsList = new ArrayList<Character>();
		
		for(int i=0;i<kanaArray.length;i++) {
			allCharsList.add(new Character(kanaArray[i], romajiArray[i], i, 0));
		}
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
		
		/*while(fullPool.size()<5) { // Vaihtoehtoja vähemmän kuin 4, lisätään randomilla
			Random rand = new Random();
			int randomNum = rand.nextInt((45 - 0) + 1) + 0;
			fullPool.add(allCharsList.get(randomNum));
		} */
	}
	
	public ArrayList<Character> newQuestion() {
		ArrayList<Character> tempPool = new ArrayList<Character>(fullPool);
		tempPool.remove(previousQuestionAnswer);
		Collections.shuffle(tempPool);
		int k = tempPool.size();
		
		if(k>4) tempPool.subList(4,k).clear(); // Leikataan poolista muut pois paitsi 4 ensimmäistä
		
		currentQuestionAnswer=tempPool.get(0);
		previousQuestionAnswer=currentQuestionAnswer;
		
		return tempPool;
	}
}
