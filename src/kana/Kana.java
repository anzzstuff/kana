package kana;

public class Kana {

	public Kana() {
		Game peli = new Game();
		UI uiHandler = new UI(peli);
		uiHandler.setLocationRelativeTo(null); // Keskittää ikkunan näytölle
		uiHandler.setVisible(true);
	}
	
	public static void main(String args[]) {
		new Kana();
    }
}
