package kana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Kana extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JFrame mainFrame;
	JPanel masterPane;
	JPanel gamePane;
	JPanel menuPane;
	JButton startBtn;
	JButton menuBtn;
	JButton checkAllBtn;
	JButton checkNoneBtn;
	public ArrayList<Character> allCharsList;
	private JCheckBox[] charChoices = new JCheckBox[10];
	
	public Kana() {
		super("Kana Quiz");
		mainFrame = this;
		
		setSize(400,420);
		setResizable(false);

		allCharsList = new ArrayList<Character>();
		initializeCharacters(); // luodaan merkki-oliot
		
		// päävalikon komponentit
		JTextArea guideText = new JTextArea("Valitse merkit joita tahdot opiskella. "
				+ "Voit muuttaa valintojasi\nmyöhemmin palaamalla päävalikkoon.");

		guideText.setOpaque(false);
		guideText.setEditable(false);

		startBtn = new JButton(" Aloita >");
		startBtn.addActionListener(this);
		
		menuPane = new JPanel(new BorderLayout());
		menuPane.setOpaque(false);
		menuPane.add(guideText, BorderLayout.NORTH);
		
		JPanel checkBoxPaneOuter = new JPanel();
		checkBoxPaneOuter.setOpaque(false);
		//checkBoxPaneOuter.setLayout(new BoxLayout(checkBoxPaneOuter, BoxLayout.PAGE_AXIS));
		
		JPanel checkBoxPane = new JPanel();
		checkBoxPane.setLayout(new BoxLayout(checkBoxPane, BoxLayout.PAGE_AXIS));
		checkBoxPane.setPreferredSize(new Dimension(375,260));
		checkBoxPane.setBackground(new Color(230,230,230));
		checkBoxPane.add(Box.createRigidArea(new Dimension(7, 9)));

		charChoices[0] = new JCheckBox("a, i, u, e, o");
		charChoices[1] = new JCheckBox("ka, ki, ku, ke, ko");
		charChoices[2] = new JCheckBox("sa, shi, su, se, so");
		charChoices[3] = new JCheckBox("ta, chi, tsu, te, to");
		charChoices[4] = new JCheckBox("na, ni, nu, ne, no");
		charChoices[5] = new JCheckBox("ha, hi, fu, he, ho");
		charChoices[6] = new JCheckBox("ma, mi, mu, me, mo");
		charChoices[7] = new JCheckBox("ya, yu, ro");
		charChoices[8] = new JCheckBox("ra, ri, ru, re, ro");
		charChoices[9] = new JCheckBox("wa, wo, n");
		for(int i=0;i<charChoices.length;i++) {
			charChoices[i].setOpaque(false);
			checkBoxPane.add(charChoices[i]);
		}
		checkBoxPane.setAlignmentX(LEFT_ALIGNMENT);
		checkBoxPaneOuter.add(checkBoxPane);
		
		JPanel checkAllPane = new JPanel(new FlowLayout());
		checkAllBtn = new JButton("Valitse kaikki");
		checkAllBtn.addActionListener(this);

		checkNoneBtn = new JButton("Poista valinnat");
		checkNoneBtn.addActionListener(this);

		checkAllPane.add(checkAllBtn);
		checkAllPane.add(checkNoneBtn);
		checkAllPane.setOpaque(false);
		checkBoxPaneOuter.add(checkAllPane);
	
		menuPane.add(checkBoxPaneOuter, BorderLayout.CENTER);	
		menuPane.add(startBtn, BorderLayout.SOUTH);
		
		// pelinäkymän komponentit
		menuBtn = new JButton("< Takaisin päävalikkoon  ");
		menuBtn.addActionListener(this);
		
		gamePane = new JPanel();
		gamePane.setOpaque(false);
		
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
	
	public void initializeCharacters() {
		// luodaan merkki-oliot
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
		
	
		for(int i=0;i<kanaArray.length;i++) {
			allCharsList.add(new Character(kanaArray[i], romajiArray[i], i, 0));
		}
	
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startBtn) {
			// testataan onko ryhmiä valittuna
			boolean anySelected = false;
			ArrayList<Integer> selectedArray = new ArrayList<Integer>();
			for(int i=0;i<charChoices.length;i++)
				if(charChoices[i].isSelected()) {
					selectedArray.add(i);
					anySelected = true;
				}
			
			// siirrytään pelinäkymään
			if(anySelected) {
				CardLayout cardLayout = (CardLayout)(masterPane.getLayout());
				cardLayout.show(masterPane, "GAME");

				Game peli = new Game(selectedArray);
			}
			else
				JOptionPane.showMessageDialog(mainFrame, "Sinun täytyy valita jokin ryhmä jotta voit aloittaa pelin.");
		}
		if(e.getSource() == menuBtn) {
			// siirrytään päävalikkoon
			CardLayout cardLayout = (CardLayout)(masterPane.getLayout());
			cardLayout.show(masterPane, "MENU");
		}
		if(e.getSource() == checkAllBtn) {
			for(int i=0;i<charChoices.length;i++) {
				charChoices[i].setSelected(true);
			}
		}
		if(e.getSource() == checkNoneBtn) {
			for(int i=0;i<charChoices.length;i++) {
				charChoices[i].setSelected(false);
			}
		}
		
		//JOptionPane.showMessageDialog(mainFrame, "action performed");
	}
	

	/*
	public void startGame() {
		int from=0;
		int to=4;
		Random rnd = new Random();
		ArrayList<Character> list = new ArrayList<Character>();
		
		//Character[] characters = new Character[5];
		
		String[] kanaArray = {
			"あ","い","う","え","お"	
		};
		String[] romajiArray = {
			"a","i","u","e","o"
		};
		
		for(int i=0;i<kanaArray.length;i++) {
			list.add(new Character(kanaArray[i], romajiArray[i], 1));
			//characters[i] = new Character(kanaArray[i], romajiArray[i], 1);
			//System.out.println(romajiArray[i]);
		}
		// Aloitetaan uusi peli valituilla asetuksilla
		for(int i=0;i<characters.length;i++) {
			System.out.println(characters[i].getKana());
		}
		System.out.println(list.size());
		Collections.shuffle(list);
		
		gamePane.removeAll();
		gamePane.add(menuBtn);	
		
		// TODO: removable?
		gamePane.revalidate();
		gamePane.repaint();
	}
	*/	
	
	
	public static void main(String args[]) {
		Kana launcher = new Kana();
		launcher.setLocationRelativeTo(null);
		launcher.setVisible(true);
    }
}
