package kana;

import javax.swing.JFrame;

public class Kana {
	public static void main(String args[]) {
		Game peli = new Game();
		UI uiHandler = new UI(peli);
		uiHandler.setResizable(false);
		uiHandler.pack();
		uiHandler.setLocationRelativeTo(null); // Keskittää ikkunan näytölle
		uiHandler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		uiHandler.setVisible(true);
    }
}
