package kana;

public class Kana {
	public static void main(String args[]) {
		Game peli = new Game();
		UI uiHandler = new UI(peli);
		uiHandler.setLocationRelativeTo(null); // Keskittää ikkunan näytölle
		uiHandler.setVisible(true);
    }
}
