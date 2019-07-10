
/*
 * Katia Izvoztchikova
 * ICS4UE
 * Mr.Benum
 * Goal of program is to display an instructions screen. To explain to user how to play. 
 * Also has to have a back button to exit screen.  
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class InstructionsPage extends JFrame {
	private static JLabel howToTitle; // JLabel with just text
	private static JLabel background; // JLabel to display the background image on.
	static final int FRAME_WIDTH = 1000; // creating constants to hold size of instructions page
	static final int FRAME_HEIGHT = 700;
	static final Font BUTTONFONT = new Font("Bernard MT Condensed", Font.PLAIN, 20);
	static final Color LGREEN = new Color(22, 138, 63); // creating reusable colors
	static String htmlText;

	/**
	 * Constructor for InstructionsPage class, creates an Instructions Page
	 * JFrame with HTML text, JLabels, and buttons. 
	 */
	public InstructionsPage() {
		super("Instructions");
		// Calls JFrame instructions
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		// Sets size of JFrame as predetermined constants
		setLocation(480, 100);
		// sets the location of JFrame on screen
		background = new JLabel(new ImageIcon("goldbackground.jpg"));
		add(background);
		// Creates and adds a JLabel with a background image to the JFrame
		background.setLayout(null);

		makeHowToTitle();

		htmlText = "<html><div style ='margin:25px;'><p style ='font-size:14px; color:white;font-family:Andalus;'> Mario poker is very similar to regular poker except"
				+ " the cards have Mario images instead of suits and ranks. You will be playing against a computer, starting with 11 coins. Each time you play 1 coin will be deducted "
				+ "as a fee for playing. The goal of the game is to have a better deck than the computer and whoever wins gets their coin back and the other player’s coin as well. If"
				+ " you feel that you have a really great deck and that you are likely to win you can bet as many coins as you have and if you win you will gain those back and another "
				+ "amount equal to the amount you bet. However, if you lose you will lose those coins and another amount equal to the amount you bet. The player that wins is based on "
				+ "the hand they have (order of ranking); 5 of the same kind, four of a kind, three of a kind AND two of a kind (a full house), three of a kind, two pairs, and then "
				+ "one pair. If you have the exact same hand as the computer you will lose and if you both have junk the player with the higher ranking cards wins. The ranking is as "
				+ "follows: star being highest, then Mario, luigi, flower, mushroom, and finally cloud.   "
				+ "<br><br>Have fun!!! </p></div></html>";
		// Text can be easily replaced in String, some formatting made using HTML
		// Made margins and formatting of text like color and size using CSS

		makeHTMLtext();

		// Makes a back button
		JButton back = new JButton();
		back.setText("Back");
		// Setting button text
		background.add(back);
		// Adding it on top of the background JLabel
		back.setForeground(new Color(11, 66, 30));
		// Changing the text color
		back.setFont(BUTTONFONT);
		// Changing button text font
		back.setBounds(830, 580, 100, 30);
		// Location and size of Button
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				// Closes the Instructions JFrame when button is clicked
			}
		});

	}

	/**
	 * Makes Title text on InstructionsPage 
	 * pre: none 
	 * post: Displays text with specific font, location, and color.
	 */
	public static void makeHowToTitle() {
		howToTitle = new JLabel("How to Play Mario Poker");
		// Creates a new JLabel with a title
		howToTitle.setBounds(260, 45, FRAME_WIDTH, 70);
		// Sets the size and the position of the JLabel
		howToTitle.setForeground(LGREEN);
		// Changes color of text
		Font titleFont = new Font("Bernard MT Condensed", Font.PLAIN, 50);
		howToTitle.setFont(titleFont);
		// Changed font and size of text
		background.add(howToTitle);
		// Adds the JLabel to the background JLabel
	}

	/**
	 * Makes JLabel that holds html text
	 * pre: none 
	 * post: Displays colored rectangle with html formatted text.
	 */
	public static void makeHTMLtext() {
		JLabel text = new JLabel(htmlText);
		// Making a JLabel for instructions text
		text.setBounds(50, 130, 880, 430);
		// Setting size and position of JLabel on JFrame screen
		text.setOpaque(true);
		// Making opaque so that JLabel visible, looks like a rectangle
		text.setBackground(LGREEN);
		// Making the JLabel background green
		background.add(text);
		// Adding the JLabel to the JFrame
	}

}



