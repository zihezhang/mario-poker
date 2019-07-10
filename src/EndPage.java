/*
 * Zihe Zhang
 * ICS4UE
 * Mr.Benum
 * Displays a end page after player has lost the game. This page plays a sound and shows the score of the player
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;	
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*; 

public class EndPage extends JFrame 
{
	private JLabel title; // JLabel with just text
	private JLabel background; // JLabel to display the background image on.
	private final int FRAME_WIDTH = 1000;
	private final int FRAME_HEIGHT = 700;
	private final Font BUTTON_FONT = new Font("Bernard MT Condensed", Font.PLAIN, 20);
	private final Color DGREEN = new Color(11,66,30);
	private final Font TITLE_FONT = new Font("Bernard MT Condensed", Font.PLAIN, 45);
	private static Clip clip;
	private JButton homeButton = new JButton();

	// LabelFrame constructor adds JLabels to JFrame
	public EndPage(String name, int score)
	{
		// Calls JFrame High Scores
		super("Game Over");
		// Sets size of JFrame as predetermined constants
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		// sets the location of JFrame on screen
		setLocation(480, 100);

		// Creates and adds a JLabel with a background image to the JFrame
		background = new JLabel (new ImageIcon("endbackground.png"));
		add(background);
		background.setLayout(null); 

		title = new JLabel( "Score: " + score );

		HighscoresPage scores = new HighscoresPage();
		scores.fileToArray();
		scores.arrangeScores();
		scores.addHighScore(name, score);
		scores.arrangeScores();
		scores.writeHighScoresToFile();
		scores.updateLabels();

		title.setBounds(400, 230, 900, 100); // BOUNDS WORK LIKE THIS  = x, y, width of label, height of label  
		title.setForeground(Color.WHITE);
		title.setFont(TITLE_FONT);
		background.add(title);

		//Creates a back button with the text "home" and places it on the background
		homeButton.setText("Home");
		background.add(homeButton); 
		homeButton.setFont(BUTTON_FONT); // Changing font to preassigned font
		homeButton.setForeground(DGREEN); // Changing button color
		homeButton.setBounds(440, 325, 100, 35); // Setting location of Button
		//the JFrame will be disposed of when the home button is pressed
		homeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});          

		playMusic();

	} // closes constructor 
	/**
	 * Plays end game tune
	 * pre: there is a music file to play
	 * post: music is played when player loses
	 * 
	 */
	public static void playMusic() {
		try // Tries to get audio clip and play it
		{
			AudioInputStream music = AudioSystem.getAudioInputStream(new File("15 - Down.wav").getAbsoluteFile()); // Gets audio clip 
			clip = AudioSystem.getClip(); // Gets audio clip
			clip.open(music); // Opens music clip											
		}
		catch (Exception e) // If there is an exception								
		{
		}
		clip.start(); // Starts the music  
	}

} // end class LabelFrame


