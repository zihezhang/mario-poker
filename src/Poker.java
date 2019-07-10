/*
 * Name: Dina Bernstein & Zihe Zhang
 * : ICS4UE
 * Teacher: Mr. Benum
 * Purpose: Creates the main game screen of mario poker, with interactive and visual elements
 */

//Imports necessary libraries
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class Poker extends JFrame 
{
	private static final int FRAME_WIDTH = 1000, FRAME_HEIGHT = 700; //Holds the height and width of the frame
	private static int coins; //Stores the amount of coins the player has
	private static int betValue = 1; //Stores the players bet
	private static int rounds; //Stores the amount of rounds of poker played
	private DrawingPanel cardArea; //Creates a DrawingPanel to be displayed on the screen
	//Creates a new hand for the player and dealer opponent
	PokerHand playerHand = new PokerHand(); 
	DealerHand dealerHand = new DealerHand();
	private ArrayList<Integer> cardsToRemove = new ArrayList<Integer>(); //This will store cards to be removed from the player's hand
	boolean playing = true; //True if the player has control of their hand (before pressing hold)
	//Creates a hold and bet button, sets an image for each
	private JButton hold = new JButton(new ImageIcon(new ImageIcon("holdButton.png").getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT)));
	private JButton bet = new JButton(new ImageIcon(new ImageIcon("betButton.png").getImage().getScaledInstance(100, 80, Image.SCALE_DEFAULT)));
	//Creates buttons for other screen actions
	private JButton back = new JButton();
	private JButton playAgain = new JButton();
	private JLabel winOrLose = new JLabel();
	private Font gameFont; //Sets a font for screen items 
	String playerName = ""; //This will store the name of the player, for high score purposes

	public Poker()
	{
		super("Mario Poker");
		//Creates the window
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setLocation(480, 100);
		setLayout( new BorderLayout() ); 
		coins = 11; //Player begins with 11 coins
		rounds = 0; 
		newGame(); //A new game is called
		cardArea = new DrawingPanel(); //A drawing panel is created for the card area
		add(cardArea, BorderLayout.CENTER); //Card area is added to the screen
		//Imports the SuperMario font, catches an exception if this does not happen
		try 
		{
			gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("SuperMario256.ttf")).deriveFont(30f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Exo2-SuperMario256.ttf")));
		} 
		catch(IOException | FontFormatException e) 
		{
			System.out.print(e.getStackTrace());
		}
	}
	/**
	 * Begins a new game and resets all values
	 * Pre: User has just pressed play on the homepage
	 * Post: User can play the game
	 */
	private void newGame()
	{
		playing = true;
		//Sets necessary buttons to be visible
		hold.setVisible(true);
		playAgain.setVisible(false);

		//Sets text and pictures for buttons and labels
		playAgain.setText(null);
		hold.setText(null);
		winOrLose.setText(null);
		playAgain.setIcon(new ImageIcon(new ImageIcon("playAgainButton.png").getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT)));
		hold.setIcon(new ImageIcon(new ImageIcon("holdButton.png").getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT)));
		back.setText("Back");

		//Resets player and opponent hands for new round
		playerHand.clear();
		dealerHand.clear();	
		cardsToRemove.clear();
		//Deals and sorts the player and dealer hands
		playerHand.deal();
		dealerHand.deal();
		dealerHand.sort();
		playerHand.sort();
		//The player must bet 1, causng their coins to decrease by 1
		betValue = 1;
		coins-=1;
		repaint();
	}
	//Creates a drawing panel class within the game
	private class DrawingPanel extends JPanel
	{
		//Sets the width and height of the panel
		final int WIDTH = 650;
		final int HEIGHT = 600;
		public DrawingPanel()
		{
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setFont(new Font("Arial", Font.PLAIN, 18));
			this.addMouseListener(new PokerMouseHandler());
			//Adds an action listener to the hold button
			hold.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					//Removes all cards that the player has selected to remove, redeals cards and sorts them
					playerHand.remove(cardsToRemove);
					playerHand.deal();
					playerHand.sort();
					//Removes all the cards that the dealer's discard method has selected, redeals cards and sorts them
					dealerHand.remove(dealerHand.discard());
					dealerHand.deal();
					dealerHand.sort();
					repaint();
					delay(200);
					//Flip each of the dealer's cards over, so they are visible to the user
					for (int i = 0; i < 5; i++)
					{
						dealerHand.getCard(i).flip();
					}
					boolean won = playerHand.findWinner(dealerHand); //This boolean checks if the player has won or not
					//The hold button is made invisible, replaced with the playAgain button
					hold.setVisible(false); 
					playAgain.setVisible(true);
					rounds++; //The amount of rounds is incremented
					winOrLose.setFont(gameFont);
					//The winOrLose label is updated and the bet value is added or removed from the amount of coins
					if (won)
					{
						winOrLose.setText("You Won!");
						coins+=betValue*2; //The player is returned the cards they bet, and is given that amount again
					}
					else
					{
						winOrLose.setText("You Lost!");
						coins-=betValue;
					}
					betValue = 0; //The bet value is set to 0
					playing = false; //At this point, the player is not 'playing'
				}
			});
			// The play again button checks if the player is able to play, then resets the hand
			playAgain.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					//check if the player lost all their money (this is when the game ends)
					if (coins <= 0) 
					{
						//Asks the user to enter their name and returns it as a string ("cancel" button in JOptionPane removed to prevent any errors)
						String[] options = {"OK"};
						JPanel panel = new JPanel();
						JLabel lbl = new JLabel("Enter Your Name: ");
						JTextField txt = new JTextField(10);
						panel.add(lbl);	
						panel.add(txt);
						int selectedOption = JOptionPane.showOptionDialog(null, panel, "Enter Name", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
						if(selectedOption == 0)
						{
							playerName = txt.getText();
						}
						dispose(); //closes Poker JFrame

						//Opens EndPage
						EndPage endpage = new EndPage(playerName, rounds*240); 
						endpage.pack(); 
						endpage.setVisible( true );
					}
					//if player still has coins to bet, game continues on with new round
					playing = true;
					newGame();
					repaint();
					playAgain.setVisible(false);
					hold.setVisible(true);
				}
			});
			//The bet button can increase the player's bet and reduce their coind
			bet.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					if (coins > 0 && playing==true) //makes sure player does not bet more than they have
					{
						betValue++;
						coins--;
						repaint();
					}
				}
			});
			// Closes the Poker JFrame and resets the game when button clicked
			back.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					coins = 11;
					rounds = 0;
					dispose();
				}
			});
		}

		/**
		 * Paints the drawing area
		 * 
		 * @param g the graphics context to paint
		 */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			// Draws on images
			g.drawImage(new ImageIcon("green_felt.jpg").getImage(), 0,0,FRAME_WIDTH,FRAME_HEIGHT, null);
			g.drawImage(new ImageIcon("ranking.png").getImage(), 30,400,40,200, null);
			g.drawImage(new ImageIcon("flush.png").getImage(), 12,175,130,200, null);
			g.drawImage(new ImageIcon("coinbtn.png").getImage(), 110,6,35,35, null);
			g.setFont(new Font("Serif", Font.BOLD, 24));
			g.setColor(Color.white);
			// Draws text saying how many coins player has and how many coins player bet
			g.drawString("Coins: "+coins, 10, 30);
			g.drawString("Bet: "+betValue,10,60);

			//Draws all the cards
			for (int i = 0; i < 5; i++)
			{
				dealerHand.getCard(i).draw(g);
				playerHand.getCard(i).draw(g);
			}
			//adds the bet button to the JPanel 
			add(bet); 
			bet.setBorderPainted(false); //makes button with no border
			bet.setFocusPainted(false); //makes button not do anything when you hover your mouse on it
			bet.setContentAreaFilled(false); //makes a empty button
			bet.setBounds(20, 68, 100, 100);// Location of Button	

			//adds the hold button to the JPanel 
			add(hold); 
			hold.setBorderPainted(false);
			hold.setFocusPainted(false);
			hold.setContentAreaFilled(false);
			hold.setBounds(400, 270, 210, 50);

			//adds the playAgain button to the JPanel 
			add(playAgain);
			playAgain.setBorderPainted(false);
			playAgain.setFocusPainted(false);
			playAgain.setContentAreaFilled(false);
			playAgain.setBounds(400,270,210,50);

			//adds Label that indicated weather you won or lost the round to the JPanel 
			add(winOrLose);
			winOrLose.setBounds(415,335,210,30);

			//adds the back button to the JPanel 
			add(back); 
			back.setFocusPainted(false);
			back.setBounds(830, 605, 100, 30); 		
		}
	}
	//This class handles mouse clicks
	private class PokerMouseHandler extends MouseAdapter
	{
		/**
		 * Handles a mouse being released when selecting a spot to place a card
		 * Pre: Cards have been drawn on the screen
		 * Post: The mouse click will be recognized and acted upon
		 */
		public void mouseReleased(MouseEvent event)
		{
			Point clickPoint = event.getPoint();
			if (playing) //Mouse clicks only count when the player is playing
			{
				for (int i = 0; i < 5; i++) //Goes through each card and checks if they have been clicked
				{
					if (playerHand.getCard(i).checkClick(clickPoint))
					{
						int yDistance = -40; //This will be the distance the card will move, the negative value means it moves up
						if (playerHand.getCard(i).isUp()) //If the card is already up, the yDistance becomes positive, moving the card down
						{
							yDistance*=-1;
						}
						//A method is called moving the card up
						moveCardUp(playerHand.getCard(i),yDistance, 2);
						cardArea.repaint();
					}
				}
			}

		}
	}
	/**
	 * A card is moved up or down with animation
	 * Pre: A card has been clicked
	 * Post: The card is at a new position
	 */
	private void moveCardUp(Card card, int distanceDown, int delay)
	{
		double y = card.getY();
		cardArea.repaint();
		for (int i = 0; i < Math.abs(distanceDown); i++) //Goes through each pixel the card is expected to move
		{  
			//Either increases or decreases the card distance based on which direction it is moving in
			if (distanceDown > 0)	
			{
				y++;
			}
			else
			{
				y--;
			}
			card.setY(y); //Resets the y-value of the card
			delay(delay); //Waits the delayed amount of time
			rePaintDrawingAreaImmediately(); //Repaints the screen

		}
		card.switchLocation(); //Notifies the card object that its location has been switched
		if (card.isUp()) //Adds a card to the cards to remove arraylist if it is put up
		{
			cardsToRemove.add(card.getIndex());
		}
		else 
		{
			for (int i = 0; i < cardsToRemove.size();i++) //Removes the card from the arraylist if it is put down
			{
				if (cardsToRemove.get(i)==card.getIndex())
				{
					cardsToRemove.remove(i);
				}
			}
		}
	}
	/**
	 * Delays the thread by a certain amount of time, and catches the exception which may cause a problem
	 */
	private void delay(int msec)
	{
		try
		{
			Thread.sleep(msec);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Repaints the drawing panel as a rectangle
	 */
	private void rePaintDrawingAreaImmediately()
	{
		cardArea.paintImmediately(new Rectangle(0, 0, cardArea.getWidth(), cardArea.getHeight()));
	}

	//Main method for testing the poker page
	public static void main(String[] args)
	{
		//Creates and sets a poker instance
		Poker game = new Poker();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.pack();
		game.setVisible(true);

	}
}



