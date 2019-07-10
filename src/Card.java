/*
 * Name: Dina Bernstein
 * Class: ICS4UE
 * Teacher: Mr. Benum
 * Purpose: Creates a card object to be used in a poker game
 */

//Imports necessary libraries
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Card extends Rectangle
{
	private int rank; //Holds the cards value
	private int index; //Holds the cards location in a hand
	public final static int WIDTH = 140, HEIGHT = 210; //Holds the width and height of a card when it is drawn
	private final static Image cardBack = new ImageIcon("cardFaceDown.png").getImage(); //Image of the back of the card
	private boolean isFaceUp; //Tracks if the card is face-up or face-down
	private Image image; //Will be given the image on the front of the card
	private int x,y; //The x and y locations of the card on the game screen
	private static int CARDS_SPACE = 160; //The distance between each card on the game screen
	private static int CARDS_X = 150; //Distance of first card from the left
	private static int PLAYER_CARDS_Y = 380; //The y value of the cards
	private boolean isUp = false; //Tracks if the card has been moved up on he screen
	public Card(int rank, int index)
	{
		//Sets the values of the fields to equal the inputed values
		this.rank = rank;
		this.index = index;
		isFaceUp = true; //Cards are automatically designated as faceup
		switch (rank) //Depending on the rank of the card, an image is selected
		{
		case 1: image = new ImageIcon("cloud.png").getImage(); break;
		case 2: image = new ImageIcon("mushroom.png").getImage(); break;
		case 3: image = new ImageIcon("flower.png").getImage(); break;
		case 4: image = new ImageIcon("luigi.png").getImage(); break;
		case 5: image = new ImageIcon("mario.png").getImage(); break;
		case 6: image = new ImageIcon("star.png").getImage(); break;

		}
		this.x = this.index*CARDS_SPACE+CARDS_X; //An x value is given based on the index of the card
		this.y = PLAYER_CARDS_Y; //The initial y value is the same for all cards
	}
	/**
	 * Sets the X-value of a card
	 */
	public void setX(double i)
	{
		x = (int)i;
	}
	/**
	 * Sets the Y-value of a card
	 */
	public void setY(double i)
	{
		y = (int)i;
	}
	/**
	 * Returns the X-value of a card
	 */
	public double getX()
	{
		return x;
	}
	/**
	 * Returns the Y-value of a card
	 */
	public double getY()
	{
		return y;
	}
	/**
	 * Returns the width of a card
	 */
	public double getWidth()
	{
		return WIDTH;
	}
	/**
	 * Returns the height of a card
	 */
	public double getHeight()
	{
		return HEIGHT;
	}
	/**
	 * Returns whether or not a card has been moved to the 'up' position
	 */
	public boolean isUp()
	{
		return isUp;
	}
	/**
	 * Moves the card - if it is up, it goes down; if it is down, it goes up
	 */
	public void switchLocation()
	{
		if (isUp)
		{
			isUp = false;
		}
		else
		{
			isUp = true;
		}
	}
	/**
	 * Sets the index a card, and moves its x value so it would be drawn in the appropriate spot
	 */
	public void setIndex(int i)
	{
		this.index = i;
		this.x = this.index*CARDS_SPACE+CARDS_X;
	}
	/**
	 * Returns the index of a card
	 */
	public int getIndex()
	{
		return index;
	}
	/**
	 * Switches the direction the card is facing
	 */
	public void flip()
	{
		isFaceUp = !isFaceUp;
	}
	/**
	 * Puts the card face down
	 */
	public void putFaceDown()
	{
		isFaceUp = false;
	}
	/**
	 * Draws the card using its specified values
	 */
	public void draw(Graphics g)
	{
		if (isFaceUp)
		{
			g.drawImage(image, x,y,WIDTH,HEIGHT, null);
		}
		else
		{
			g.drawImage(cardBack, x,y,WIDTH,HEIGHT, null);
		}
	}
	/**
	 * Checks if two cards are equal
	 */
	public boolean equals(Object other)
	{
		//Two cards are equal if they have the same rank
		return rank == ((Card)(other)).rank;
	}
	/**
	 * Returns the rank of a card
	 */
	public int getRank()
	{
		return rank;
	}
	/**
	 * Compares the rank of this card and that of the parameter
	 */
	public int compareTo(Card other) 
	{
		return rank-other.rank;
	}
	/**
	 * Converts a card into a String denoting its rank
	 */
	public String toString()
	{
		return ""+rank;
	}
	/**
	 * Checks if a mouse click was within the area of a card
	 */
	public boolean checkClick(Point point)
	{
		return point.y>y && point.y<y+HEIGHT && point.x>x && point.x<x+WIDTH;
	}
}
