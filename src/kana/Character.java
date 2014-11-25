package kana;

public class Character {
	private String kana;
	private String romaji;
	private int index;
	private int mistakes;
	
	public Character(String par_kana, String par_romaji, int par_index, int par_mistakes) {
		setKana(par_kana);
		setRomaji(par_romaji);
		setIndex(par_index);
		setMistakes(par_mistakes);
	}

	public String getKana() {
		return kana;
	}

	public void setKana(String par_kana) {
		this.kana = par_kana;
	}

	public String getRomaji() {
		return romaji;
	}

	public void setRomaji(String par_romaji) {
		this.romaji = par_romaji;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getMistakes() {
		return mistakes;
	}

	public void setMistakes(int par_mistakes) {
		this.mistakes = par_mistakes;
	}
}
