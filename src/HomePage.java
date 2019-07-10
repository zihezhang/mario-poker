
/*
 * Katia Izvoztchikova
 * ICS4UE
 * Mr.Benum
 * Goal of program is to display a home screen, with buttons, a background, sounds, and
 * an image of luigi. This is to allow the player to navigate between pages of our game. 
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class HomePage extends JFrame {
	private static JLabel marioTitle; // JLabel with just text
	private static JLabel luigi; // JLabel with image
	private static JLabel background; // JLabel to display the background image on.
	static final int FRAME_WIDTH = 1000; // creating constants to hold size of home page
	static final int FRAME_HEIGHT = 700;
	static final Font BUTTONFONT = new Font("Bernard MT Condensed", Font.PLAIN, 20);
	static final Color LGREEN = new Color(22, 138, 63); // creating colors to be used more than once
	static final Color DGREEN = new Color(11, 66, 30);  // constants because I don't want them to change midway
	static boolean sound = true;
	static Clip clip;

	/**
	 * Constructor for HomePage class, creates a HomePage JFrame 
	 * with images, buttons, text, JLabels and audio.
	 */
	public HomePage()
	{
		super("Mario Poker");
		// Calls JFrame Mario Poker
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		// Makes window based on constant size
		setLocation(480, 100);
		background = new JLabel(new ImageIcon("goldbackground.jpg"));
		add(background);
		// Making the background a JLabel and adding it to the JFrame
		background.setLayout(null);

		makeMarioTitle();
		makeLuigiImage();
		makeGameButton();
		makeInstructionsButton();
		makeHighScoresButton();

		try // Tries to execute main code
		{
			AudioInputStream music = AudioSystem
					.getAudioInputStream(new File("01 - Super Mario Bros.wav").getAbsoluteFile()); // Gets audio clip
			clip = AudioSystem.getClip(); // Sets audio clip
			clip.open(music); // Opens music clip
		} catch (Exception e) // If there is an exception
		{
		}
		clip.start(); // Starts the music
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		// Music plays on loop throughout running of program

		// Making sound button with images and music attached
		final ImageIcon soundON = new ImageIcon("SoundGoodOn.PNG");
		final ImageIcon soundOff = new ImageIcon("SoundGoodOFF.PNG");
		final JButton btnSound = new JButton(soundON);
		// Setting the button to have an image
		background.add(btnSound);
		// Adding the button to the background JLabel
		btnSound.setBounds(870, 550, 60, 53);
		// Location of Button
		btnSound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (sound) {
					clip.stop(); // Stops the music
					btnSound.setIcon(soundOff);
					sound = false;
				} else {
					btnSound.setIcon(soundON);
					clip.start(); // Starts the music
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					sound = true;
				}
				// If button is clicked will alternate between
				// sound being turned on and off based on
				// boolean variable, sound.
			}
		});
	}

	public static void main(String[] args) {
		HomePage labelFrame = new HomePage();
		// Creates an instance of the HomePage
		labelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// makes sure that it closes page fully when you click x
		labelFrame.pack();
		labelFrame.setVisible(true); // display frame

	}

	/**
	 * Makes a Game Button on Home Page Frame 
	 * pre: none 
	 * post: Made a play game button, has specific font, location 
	 * and text, when clicked creates instance
	 * of Poker class.
	 */
	public static void makeGameButton() {
		JButton btnGame = new JButton();
		// Creating a play game button
		btnGame.setText("PLAY");
		background.add(btnGame);
		btnGame.setFont(BUTTONFONT);
		// Changing font to preassigned font
		btnGame.setForeground(DGREEN);
		btnGame.setBounds(520, 280, 200, 50);
		// Setting location of Button
		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// when clicked the button with create an instance of the poker class.
				Poker pgame = new Poker();
				pgame.pack(); // JFrame is fit to preferred size
				pgame.setVisible(true); // display frame

			}
		});
	}

	/**
	 * Makes Title text of HomePage 
	 * pre: none 
	 * post: Displays text with specific font, location, and color.
	 */
	public static void makeMarioTitle() {
		marioTitle = new JLabel("MARIO POKER");
		// creating a title JLabel, with text
		marioTitle.setBounds(190, 130, 900, 110);
		// setting bounds of title JLabel
		marioTitle.setForeground(LGREEN);
		// Changes color of JLabel text
		Font titleFont = new Font("Bernard MT Condensed", Font.PLAIN, 110);
		marioTitle.setFont(titleFont);
		// Changed font and size of text
		background.add(marioTitle);
		// Adding the title JLabel on top of the JLabel background
	}

	/**
	 * Creates an image of Luigi 
	 * pre: none 
	 * post: Displays the image of Luigi.
	 */
	public static void makeLuigiImage() {
		Icon luigipic = new ImageIcon(
				new ImageIcon("luigiimage.png").getImage().getScaledInstance(175, 210, Image.SCALE_DEFAULT));
		// creating an imageicon holding the Luigi image and setting its size, also
		// making
		// sure that it stays scaled to the original image.
		luigi = new JLabel(luigipic);
		// Adding this image to a JLabel
		luigi.setBounds(40, 265, 600, 220);
		background.add(luigi);
		// Adding the image JLabel on top of the JLabel background
	}

	/**
	 * Makes an Instructions Button on Home Page Frame 
	 * pre: none 
	 * post: Made an instructions button, has specific font, location 
	 * and text, when clicked creates instance of InstructionsPage class.
	 */
	public static void makeInstructionsButton() {
		JButton btnInst = new JButton();
		// Creating an instructions page button
		btnInst.setText("Instructions");
		background.add(btnInst);
		btnInst.setFont(BUTTONFONT);
		// Changing font to preassigned font
		btnInst.setForeground(DGREEN);
		btnInst.setBounds(520, 350, 200, 50);
		// Setting location of Button
		btnInst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// when clicked the button with create an instance of the instructions page
				// class.
				InstructionsPage instFrame = new InstructionsPage();
				instFrame.pack();
				instFrame.setVisible(true);

			}
		});
	}

	/**
	 * Makes a High Scores Button on Home Page Frame 
	 * pre: none 
	 * post: Made a high scores button, has specific font, location and text, 
	 * when clicked creates instance of HighscoresPage class.
	 */
	public static void makeHighScoresButton() {
		JButton btnHiScr = new JButton();
		// creating a high scores button
		btnHiScr.setText("High Scores");
		background.add(btnHiScr);
		btnHiScr.setFont(BUTTONFONT);
		// Changing font to preassigned font
		btnHiScr.setForeground(DGREEN);
		btnHiScr.setBounds(520, 420, 200, 50);
		// Setting location of Button
		btnHiScr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// When button is clicked will create an instance of the High scores page class.
				HighscoresPage scoreFrame = new HighscoresPage();
				scoreFrame.pack();
				scoreFrame.setVisible(true);
			}
		});
	}
}



