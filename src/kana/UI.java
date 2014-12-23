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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import net.miginfocom.swing.MigLayout;

public class UI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	// CardLayout paneelit
	private JPanel masterPane;
	private JPanel gamePane;
	private JPanel menuPane;
	// Päävalikon komponentit -- määritellään koska actionhandler
	private JButton startBtn;
	private JButton checkAllBtnHira;
	private JButton checkNoneBtnHira;
	private JButton checkAllBtnKata;
	private JButton checkNoneBtnKata;
	// Pelinäkymän komponentit -- määritellään jotta muokattavissa
	private JTextPane previousResultText;
	private JTextPane currentQuestionText;
	private JButton[] optionBtn = new JButton[4];
	private JButton menuBtn;
	// Muita jutskia
	private Character currentQuestion;
	private Game peli;
	private JCheckBox[] charChoices = new JCheckBox[22];
	private Random rand;
	private int randomNum = 0;
	
	public UI(Game par_peli) {
		super("Kana Quiz");
		peli = par_peli;
		rand = new Random(); // Globaali randomoija
		
		initializeMenuUI();
		initializeGameUI();

		// masterPanessa on gamePane ja menuPane joita vaihdellaan
		masterPane = new JPanel(new CardLayout());
		masterPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		masterPane.setBackground(Color.WHITE);	
		masterPane.add(menuPane, "MENU");
		masterPane.add(gamePane, "GAME");
		this.getContentPane().add(masterPane);
	}

	public void initializeMenuUI() {
		// Päävalikon komponentit
		menuPane = new JPanel(new MigLayout());
		menuPane.setBackground(new Color(230,230,230));
		menuPane.setOpaque(true);
		
		JTextPane guideText = new JTextPane();
		guideText.setText("Please choose which groups of characters you want to be studying. \nYou can change these later by returning to menu.");
		guideText.setEditable(false);
		guideText.setBackground(new Color(0,106,102));
		guideText.setForeground(Color.WHITE);
		guideText.setMargin(new Insets(8,8,8,8));
				
		JPanel checkBoxPane = new JPanel(new MigLayout("gap rel 0"));
		JPanel checkBoxPane2 = new JPanel(new MigLayout("gap rel 0"));
		checkBoxPane.setBorder(BorderFactory.createMatteBorder(6,0,6,3, Color.WHITE));
		checkBoxPane2.setBorder(BorderFactory.createMatteBorder(6,3,6,0, Color.WHITE));
		checkBoxPane.setOpaque(false);
		checkBoxPane2.setOpaque(false);
		checkBoxPane.add(new JLabel("Hiragana"), "center, span");
		checkBoxPane2.add(new JLabel("Katakana"), "center, span");
		String[] romajiArray = {
			"a, i, u, e, o",
			"ka, ki, ku, ke, ko",
			"sa, shi, su, se, so",
			"ta, chi, tsu, te, to",
			"na, ni, nu, ne, no",
			"ha, hi, fu, he, ho",
			"ma, mi, mu, me, mo",
			"ya, yu, ro",
			"ra, ri, ru, re, ro",
			"wa, wo, n",
			"ga, za, ba, kya, sha... (58 chars)"
		};
		
		for(int i=0;i<romajiArray.length;i++) {
			charChoices[i] = new JCheckBox(romajiArray[i]);
			charChoices[i].setOpaque(false);
			charChoices[i].setFont(new Font("Dialog", Font.PLAIN, 12));
			checkBoxPane.add(charChoices[i], "span 2, wrap");
			charChoices[i+11] = new JCheckBox(romajiArray[i]);
			charChoices[i+11].setOpaque(false);
			charChoices[i+11].setFont(new Font("Dialog", Font.PLAIN, 12));
			checkBoxPane2.add(charChoices[i+11], "span 2, wrap");
		}

		checkAllBtnHira = new JButton("Check all");
		checkAllBtnHira.addActionListener(this);
		checkAllBtnHira.setMargin(new Insets(2,4,2,4));
		checkAllBtnHira.setFont(checkAllBtnHira.getFont().deriveFont(Font.PLAIN));

		checkNoneBtnHira = new JButton("Uncheck all");
		checkNoneBtnHira.addActionListener(this);
		checkNoneBtnHira.setMargin(new Insets(2,4,2,4));
		checkNoneBtnHira.setFont(checkAllBtnHira.getFont().deriveFont(Font.PLAIN));

		checkBoxPane.add(checkAllBtnHira, "gaptop 10");
		checkBoxPane.add(checkNoneBtnHira, "gapleft 6, gaptop 10");

		checkAllBtnKata = new JButton("Check all");
		checkAllBtnKata.addActionListener(this);
		checkAllBtnKata.setMargin(new Insets(2,4,2,4));
		checkAllBtnKata.setFont(checkAllBtnKata.getFont().deriveFont(Font.PLAIN));

		checkNoneBtnKata = new JButton("Uncheck all");
		checkNoneBtnKata.addActionListener(this);
		checkNoneBtnKata.setMargin(new Insets(2,4,2,4));
		checkNoneBtnKata.setFont(checkNoneBtnKata.getFont().deriveFont(Font.PLAIN));
	
		checkBoxPane2.add(checkAllBtnKata, "gaptop 10");
		checkBoxPane2.add(checkNoneBtnKata, "gapleft 6, gaptop 10");

		startBtn = new JButton(" Start »");
		startBtn.addActionListener(this);

		menuPane.add(guideText, "dock north");
		menuPane.add(startBtn, "dock south");
		menuPane.add(checkBoxPane, "dock west");
		menuPane.add(checkBoxPane2, "dock east");

		
	}
	
	public void initializeGameUI() {
		// Pelinäkymän komponentit
		// Edellinen kysymys
		previousResultText = new JTextPane();
		previousResultText.setEditable(false);
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
		menuBtn = new JButton("« Takaisin päävalikkoon  ");
		menuBtn.addActionListener(this);
		
		// Koko paneeli
		gamePane = new JPanel(new BorderLayout());
		gamePane.setOpaque(false);
		gamePane.add(previousResultText, BorderLayout.NORTH);
		gamePane.add(questionPaneOuter, BorderLayout.CENTER);
		gamePane.add(menuBtn, BorderLayout.SOUTH);	
	}
	
	public void drawQuestion(ArrayList<Character> currentQuestionPool) {
		randomNum = rand.nextInt(2); // Arvotaan "kummin päin" kysytään. ((max - min) + 1) + min;
		
		currentQuestion = currentQuestionPool.get(0);
		currentQuestionText.setText("<html><span style=\"font-size: 50px;\"><b>"+(randomNum==0?currentQuestion.getRomaji():currentQuestion.getKana())+"</b></span></html>");
		Collections.shuffle(currentQuestionPool);
		for(int i=0;i<4;i++) {
			if(currentQuestionPool.size()>i) {
				if(randomNum==0) optionBtn[i].setFont(new Font("Serif", Font.BOLD, 30));
				else optionBtn[i].setFont(new Font("Dialog", Font.PLAIN, 26));
				optionBtn[i].setText((randomNum==0?currentQuestionPool.get(i).getKana():currentQuestionPool.get(i).getRomaji()));
				optionBtn[i].setVisible(true);
				optionBtn[i].setPreferredSize(new Dimension(72, 57));
			}
			else
				optionBtn[i].setVisible(false);
		}
	}
	
	public void drawResult(Character currentQuestion, boolean result) {
		currentQuestionText.requestFocus(); // Selkeyden vuoksi siirretään fokus kysymykselle josta voi siirtyä tabilla
		if(result==true) {
			previousResultText.setBackground(new Color(0,100,0));			
			previousResultText.setText("Good!     " + currentQuestion.getKana()+" = "+currentQuestion.getRomaji());
		}
		else {
			previousResultText.setBackground(new Color(100,0,0));
			currentQuestion.setMistakes(currentQuestion.getMistakes()+1);
			previousResultText.setText("Wrong!     " + currentQuestion.getKana()+" = "+currentQuestion.getRomaji());
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
				previousResultText.setBackground(new Color(0,106,102)); // 150,138,29
				previousResultText.setText("Select the option that you think corresponds the shown character.");

				peli.initializeGame(selectedArray);
				drawQuestion(peli.newQuestion());
			}
			else
				JOptionPane.showMessageDialog(this, "You must choose a group before you can start the game.");
		}
		
		if(e.getSource() == menuBtn) {
			// Siirrytään päävalikkoon
			CardLayout cardLayout = (CardLayout)(masterPane.getLayout());
			cardLayout.show(masterPane, "MENU");
		}
		if(e.getSource() == checkAllBtnHira) {
			for(int i=0;i<11;i++)
				charChoices[i].setSelected(true);
		}
		if(e.getSource() == checkNoneBtnHira) {
			for(int i=0;i<11;i++)
				charChoices[i].setSelected(false);
		}
		if(e.getSource() == checkAllBtnKata) {
			for(int i=11;i<22;i++)
				charChoices[i].setSelected(true);
		}
		if(e.getSource() == checkNoneBtnKata) {
			for(int i=11;i<22;i++)
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
