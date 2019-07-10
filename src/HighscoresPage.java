/*
 * Zihe Zhang
 * ICS4UE
 * Mr.Benum
 * Collects scores, sorts scores in text file and created a high score JFrame displaying all the players and their scores 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HighscoresPage extends JFrame {
	//size of the frame of the whole game
	final private int FRAME_WIDTH = 1000;
	final private int FRAME_HEIGHT = 700;

	//Holds the length of the high score list
	final private int HIGH_SCORE_LIST_LENGTH = 6;

	//These arrays will hold a list of the names with high scores, and the actual high scores, respectively
	private String[] namesList = new String[HIGH_SCORE_LIST_LENGTH];
	private int[] scoresList = new int[HIGH_SCORE_LIST_LENGTH];

	//These arrays will hold the JLabels that display the text of the names with high scores and the actual high scores
	private JLabel[] nameLabels = new JLabel[HIGH_SCORE_LIST_LENGTH];
	private JLabel[] scoreLabels = new JLabel[HIGH_SCORE_LIST_LENGTH];

	final private JLabel leaderboardTitle; // JLabel with just text
	final private JLabel background; // JLabel to display the background image on.

	//Initializing fonts used for different parts of the frame
	private final Font TITLE_FONT = new Font("Bernard MT Condensed", Font.PLAIN, 110);
	private final Font TEXT_FONT = new Font("Bernard MT Condensed", Font.PLAIN, 45);
	private final Font BUTTON_FONT = new Font("Bernard MT Condensed", Font.PLAIN, 20);

	//Text file storing the top 6 names and scores
	private String highscores = "highscores.txt";

	public HighscoresPage()
	{
		// Calls JFrame High Scores
		super("High Score");
		// Sets size of JFrame as predetermined constants
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		// sets the location of JFrame on screen
		setLocation(480, 100); 

		// Creates and adds a JLabel with a background image to the JFrame
		background = new JLabel (new ImageIcon("goldbackground.jpg"));
		add(background);
		background.setLayout(null); 

		//Convert the text file of high scores into a names and score array with corresponding values
		fileToArray();
		//Arranges the scores and name arrays in order, and writes these new scores to the textfile
		arrangeScores();
		writeHighScoresToFile();	
		//Updates the labels with the text of the name and score
		updateLabels();

		//Creates a button that can send the user back to the home screen
		final JButton backButton = new JButton("BACK");
		backButton.setForeground(new Color(11,66,30));
		backButton.setFont(BUTTON_FONT);
		//adds function to the back button. It will take you back to the home screen when you press on it
		backButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});

		//Creates a JLabel which will say "Leaderboard"		
		leaderboardTitle = new JLabel("Leaderboard");
		leaderboardTitle.setForeground(new Color(22,138,63));
		leaderboardTitle.setFont(TITLE_FONT);
		leaderboardTitle.setBounds(230, 65, 800, 100);
		background.add(leaderboardTitle);

		//Goes through every label in the score and name array
		int y = 165;
		for (int i = 0; i < HIGH_SCORE_LIST_LENGTH; i++)
		{

			//Adds name labels to the panel
			nameLabels[i].setForeground(new Color(22,138,63));
			nameLabels[i].setFont(TEXT_FONT);
			nameLabels[i].setBounds(305, y, 800, 100);
			background.add(nameLabels[i]);
			//Adds the score labels to the panel
			scoreLabels[i].setForeground(new Color(22,138,63));
			scoreLabels[i].setFont(TEXT_FONT);
			scoreLabels[i].setBounds(600, y, 800, 100);
			background.add(scoreLabels[i]);
			y+=60;
		}
		backButton.setBounds(460, 580, 100, 30);
		background.add(backButton);

	}
	/**
	 *Sorts contents of the file into 2 different arrays
	 *pre: there is a file, there are less than 12 lines in the file
	 *post: one array is full of name, and the other is full of scores
	 */
	public void fileToArray()
	{
		String line = null;
		try 
		{
			//Creates specific objects for file reading
			FileReader fileReader = new FileReader(highscores);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int counter = 0;
			//Uses the modulus function to place half of the line values into the name array, and half into the scores array
			while((line = bufferedReader.readLine()) != null && counter < 12) 
			{
				if (counter%2==0)
				{
					namesList[counter/2] = line;
				}
				else
				{
					//Int.parseInt converts the string in the text file into a double, or floating point number
					scoresList[counter/2] = Integer.parseInt(line);
				}
				counter++;
			}   
			bufferedReader.close();         
		}
		//Catches any exceptions or possible mistakes with reading the file
		catch(FileNotFoundException ex) 
		{
			ex.printStackTrace();              
		}
		catch(IOException ex)
		{
			ex.printStackTrace();                  
		}
	}
	/**
	 *Writes the score and the name to the high score file
	 *pre: there is a file to write on
	 *post: name and score recorded on file
	 */
	public void writeHighScoresToFile()
	{
		try 
		{
			//Creates file writing objects
			FileWriter fileWriter = new FileWriter(highscores);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			//Goes through each point in the array and adds each one as a new line into the text file
			for (int i = 0; i < scoresList.length && i < namesList.length; i++)
			{
				bufferedWriter.write(namesList[i]);
				bufferedWriter.newLine();
				bufferedWriter.write(String.valueOf(scoresList[i]));
				bufferedWriter.newLine();
			}

			bufferedWriter.close();
		}
		//Catches any problems that occur during file writing
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	/**
	 *Arranges scores so that highest is at the top, and lowest is at the bottom
	 *pre: there are scores
	 *post: names are all sorted
	 */
	public void arrangeScores()
	{		
		//Goes through each position in the array
		for (int i = 0; i <HIGH_SCORE_LIST_LENGTH; i++)
		{
			//Starts searching from the value right after the position number, and replaces it with the position number if it is larger. 
			for (int j = i+1; j < HIGH_SCORE_LIST_LENGTH; j++)
			{
				if (scoresList[j]>scoresList[i])
				{
					//A temp stores the value of the earlier position, then the second position replaces the early position, and the temp (or value of early position) replaces the second position
					int tempScore = scoresList[i];
					String tempName = namesList[i];
					scoresList[i] = scoresList[j];
					namesList[i] = namesList[j];
					scoresList[j] = tempScore;
					namesList[j] = tempName;
				}
			}
		}
	}
	/**
	 *Add new high scores to arrays
	 *pre: the list must be in order, with the lowest value at the end
	 *post: name and score stored to arrays if the list is not full
	 */
	public void addHighScore(String name, int score)
	{
		//Temporarily sets the listFull variable to true
		boolean listFull = true;
		for (int i = 0; i < HIGH_SCORE_LIST_LENGTH; i++)
		{
			//If a score isn’t added (scores are originally 0), it means the list is not full, so the variable is set to false. The score and name is also added in the section
			if (scoresList[i]==0)
			{
				scoresList[i] = score;
				namesList[i] = name;
				listFull = false;
				break;
			}
		}
		//If the list is full, the new score is only added if it is higher than at least the lowest (or last) value
		if (listFull)
		{
			if (score > scoresList[HIGH_SCORE_LIST_LENGTH-1])
			{
				scoresList[scoresList.length-1] = score;
				namesList[scoresList.length-1] = name;
			}
		}
	}
	/**
	 *Updates the leaderboard labels to display updated organised scores
	 *pre: has to be a name with a new high score
	 *post: labels are updated
	 */
	public void updateLabels()
	{
		//Goes through each label in both label arrays and sets their values to be the same as the values in the main arrays. (So the name label 1, would have the first name in the name array)
		for (int i = 0; i < HIGH_SCORE_LIST_LENGTH; i++)
		{
			//generates labels
			nameLabels[i] = new JLabel(namesList[i]);
			scoreLabels[i] = new JLabel(String.valueOf(scoresList[i]));
		}
	}
}



