package kana;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class UI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JFrame mainFrame;
	// CardLayout paneelit
	private JPanel masterPane;
	private JPanel gamePane;
	// Päävalikon komponentit
	private JPanel menuPane;
	private JButton startBtn;
	private JButton checkAllBtn;
	private JButton checkNoneBtn;
	// Pelinäkymän komponentit
	private JTextPane previousResultText;
	private JTextPane currentQuestionText;
	private JButton[] optionBtn = new JButton[4];
	private JButton menuBtn;
	
	private Character currentQuestion;
	private Game peli;
	private JCheckBox[] charChoices = new JCheckBox[10];
	int randomNum = 0;
	
	public UI() {
		super("Kana Quiz");
		mainFrame = this;
		
		setSize(400,420);
		setResizable(false);

		peli = new Game();

		// Päävalikon komponentit
		JTextPane guideText = new JTextPane();
		guideText.setText("Valitse merkit joita tahdot opiskella. Voit muuttaa valintojasi\nmyöhemmin palaamalla päävalikkoon.");
		guideText.setOpaque(false);
		guideText.setEditable(false);

		startBtn = new JButton(" Aloita >");
		startBtn.addActionListener(this);
		
		menuPane = new JPanel(new BorderLayout());
		menuPane.setOpaque(false);
		menuPane.add(guideText, BorderLayout.NORTH);
		
		JPanel checkBoxPaneOuter = new JPanel();
		checkBoxPaneOuter.setOpaque(false);
		
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
		
		// Pelinäkymän komponentit
		// Edellinen kysymys
		previousResultText = new JTextPane();
		previousResultText.setEditable(false);
		previousResultText.setBackground(new Color(100,100,100));
		previousResultText.setForeground(Color.WHITE);
		previousResultText.setMargin(new Insets(10,10,10,10));
		StyledDocument previousResultTextDoc = previousResultText.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		previousResultTextDoc.setParagraphAttributes(0, previousResultTextDoc.getLength(), center, false);

		// Nykyinen kysymys
		JPanel questionPaneOuter = new JPanel(new BorderLayout());
		questionPaneOuter.setOpaque(false);
		
		JPanel questionPane = new JPanel();
		questionPane.setOpaque(false);
		
		currentQuestionText = new JTextPane();
		currentQuestionText.setContentType("text/html");
		currentQuestionText.setText("<html><span style=\"font-size: 50px;\"><b> </b></span></html>");
		currentQuestionText.setEditable(false);
		questionPane.add(currentQuestionText);
		
		JPanel answerPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
		answerPane.setOpaque(false);

		for(int i=0;i<4;i++) {
			optionBtn[i] = new JButton("x");
			answerPane.add(optionBtn[i]);
			optionBtn[i].addActionListener(this);
		}

		questionPaneOuter.add(questionPane, BorderLayout.NORTH);
		questionPaneOuter.add(answerPane, BorderLayout.CENTER);

		// Paluunappi
		menuBtn = new JButton("< Takaisin päävalikkoon  ");
		menuBtn.addActionListener(this);
		
		// Koko paneeli
		gamePane = new JPanel(new BorderLayout());
		gamePane.setOpaque(false);
		gamePane.add(previousResultText, BorderLayout.NORTH);
		gamePane.add(questionPaneOuter, BorderLayout.CENTER);
		gamePane.add(menuBtn, BorderLayout.SOUTH);	

		// masterPanessa on molemmat paneelit "game" ja "menu" joita vaihdellaan
		masterPane = new JPanel(new CardLayout());
		masterPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		masterPane.setBackground(Color.WHITE);	
		masterPane.add(menuPane, "MENU");
		masterPane.add(gamePane, "GAME");
		this.getContentPane().add(masterPane);
		
		// Lopetus
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
	}


	public void drawQuestion(ArrayList<Character> currentQuestionPool) {
		Random rand = new Random();
		randomNum = rand.nextInt((1 - 0) + 1) + 0; // Arvotaan "kummin päin" kysytään
		
		currentQuestion = currentQuestionPool.get(0);
		currentQuestionText.setText("<html><span style=\"font-size: 50px;\"><b>"+(randomNum==0?currentQuestion.getRomaji():currentQuestion.getKana())+"</b></span></html>");
		Collections.shuffle(currentQuestionPool);
		for(int i=0;i<4;i++) {
			//todo: setvisible false jos currentQuestionPool.get(i):ssä ei oo mitään
			if(randomNum==0) optionBtn[i].setFont(new Font("Sans Serif", Font.BOLD, 30));
			else optionBtn[i].setFont(new Font("Sans Serif", Font.PLAIN, 28));
			optionBtn[i].setText((randomNum==0?currentQuestionPool.get(i).getKana():currentQuestionPool.get(i).getRomaji()));
		}
		//optionBtn[3].setVisible(false);
	}
	

	public void drawResult(Character currentQuestion, boolean result) {
		currentQuestionText.requestFocus(); // Selkeyden vuoksi siirretään fokus kysymykselle josta voi siirtyä tabilla
		if(result==true) {
			previousResultText.setBackground(new Color(0,100,0));			
			previousResultText.setText("Oikein!     " + currentQuestion.getKana()+" = "+currentQuestion.getRomaji());
		}
		else {
			previousResultText.setBackground(new Color(100,0,0));
			currentQuestion.setMistakes(currentQuestion.getMistakes()+1);
			previousResultText.setText("Väärin!     " + currentQuestion.getKana()+" = "+currentQuestion.getRomaji());
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startBtn) {
			// Testataan onko ryhmiä valittuna
			boolean anySelected = false;
			ArrayList<Integer> selectedArray = new ArrayList<Integer>(); // Alustetaan array
			for(int i=0;i<charChoices.length;i++)
				if(charChoices[i].isSelected()) {
					selectedArray.add(i); // Ja lisätään siihen valittujen checkboxien indeksit
					anySelected = true;
				}
			
			if(anySelected) {
				// Siirrytään pelinäkymään
				CardLayout cardLayout = (CardLayout)(masterPane.getLayout());
				cardLayout.show(masterPane, "GAME");
				previousResultText.setBackground(new Color(100,100,100));
				previousResultText.setText("Valitse vaihtoehto joka mielestäsi tarkoittaa näytettyä merkkiä.");

				peli.initializeGame(selectedArray);
				drawQuestion(peli.newQuestion());
			}
			else
				JOptionPane.showMessageDialog(mainFrame, "Sinun täytyy valita jokin ryhmä jotta voit aloittaa pelin.");
		}
		
		if(e.getSource() == menuBtn) {
			// Siirrytään päävalikkoon
			CardLayout cardLayout = (CardLayout)(masterPane.getLayout());
			cardLayout.show(masterPane, "MENU");
		}
		if(e.getSource() == checkAllBtn) {
			for(int i=0;i<charChoices.length;i++)
				charChoices[i].setSelected(true);
		}
		if(e.getSource() == checkNoneBtn) {
			for(int i=0;i<charChoices.length;i++)
				charChoices[i].setSelected(false);
		}
		
		for(int i=0;i<4;i++) { // Vastauspainikkeiden testaus
			if(e.getSource() == optionBtn[i]) {
				if((randomNum==0 && currentQuestion.getKana()==optionBtn[i].getText()) ||
						(randomNum==1 && currentQuestion.getRomaji()==optionBtn[i].getText()))
					drawResult(currentQuestion,true);
				else
					drawResult(currentQuestion,false);
				drawQuestion(peli.newQuestion());
			}			
		}
	}
	

}
