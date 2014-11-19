package kana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Kana extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JFrame mainFrame;
	JPanel masterPane;
	JPanel gamePane;
	JPanel menuPane;
	JButton startBtn;
	JButton menuBtn;

	public Kana() {
		// constructor
		super("Kana Quiz");
		mainFrame = this;
		
		setSize(430,400);
		setResizable(false);
		
		// p��valikon komponentit .. push l�pp�rilt�
		JTextArea guideText = new JTextArea("Valitse merkit joita tahdot opiskella. "
				+ "Voit muuttaa valintojasi my�hemmin\npalaamalla p��valikkoon.");
		guideText.setOpaque(false);
		guideText.setEditable(false);

		startBtn = new JButton(" Aloita >");
		startBtn.addActionListener(this);
		
		menuPane = new JPanel(new BorderLayout());
		menuPane.setBackground(Color.WHITE);
		menuPane.add(guideText, BorderLayout.NORTH);
		menuPane.add(startBtn, BorderLayout.SOUTH);
		
		// pelin�kym�n komponentit
		menuBtn = new JButton("< Takaisin p��valikkoon  ");
		menuBtn.addActionListener(this);
		
		gamePane = new JPanel();
		gamePane.setBackground(Color.WHITE);
		
		// masterPanessa on molemmat paneelit "game" ja "menu" joita vaihdellaan
		masterPane = new JPanel(new CardLayout());
		masterPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		masterPane.setBackground(Color.WHITE);	
		masterPane.add(menuPane, "MENU");
		masterPane.add(gamePane, "GAME");
		this.getContentPane().add(masterPane);
		
		// lopetus
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
	}
	
	
	public void startGame() {
		// Aloitetaan uusi peli valituilla asetuksilla
		gamePane.removeAll();
		
		gamePane.add(menuBtn);	
		
		
		// TODO: removable?
		gamePane.revalidate();
		gamePane.repaint();
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startBtn) {
			// siirryt��n pelin�kym��n
			CardLayout cardLayout = (CardLayout)(masterPane.getLayout());
			cardLayout.show(masterPane, "GAME");
			startGame();
		}
		if(e.getSource() == menuBtn) {
			// siirryt��n p��valikkoon
			CardLayout cardLayout = (CardLayout)(masterPane.getLayout());
			cardLayout.show(masterPane, "MENU");
		}
		
		//JOptionPane.showMessageDialog(mainFrame, "action performed");
	}
	
	
	public static void main(String args[]) {
		Kana ohjelma = new Kana();
    	ohjelma.setLocationRelativeTo(null);
    	ohjelma.setVisible(true);
    }
}
