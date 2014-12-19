package kana;

public class Kana {

	public Kana() {
		UI uiHandler = new UI();
		uiHandler.setLocationRelativeTo(null); // Keskittää ikkunan näytölle
		uiHandler.setVisible(true);
	}
	
	public static void main(String args[]) {
		new Kana();
    }
}
