/*
 * Name: Dina Bernstein & Zihe Zhang
 * Class: ICS4UE
 * Teacher: Mr. Benum
 * Purpose: Extends the poker hand to create the hand of the opposing computer player
 */

import java.util.ArrayList;

public class DealerHand extends PokerHand{
	//Holds the y-value of the dealer's cards
	public final static int DEALER_CARDS_Y = 30;

	//Constructor
	public DealerHand()
	{
		super();
		deal();
		//Places each card face down and moves their y-value to the dealer hadn position
		for (int i = 0; i < hand.length; i++)
		{
			hand[i].putFaceDown();
			hand[i].setY(DEALER_CARDS_Y);
		}
	}

	/**
	 *Overrides deal method so cards dealt are face down and moved to the dealer y-value
	 *post: Deck has 5 cards and they are added to the GUI with dealer specifications
	 */
	public void deal()
	{
		for (int i = counter; i < 5; i++)
		{
			//Creates a new card with a randomly generated value
			Card cardToAdd = new Card((int)(Math.random()*6+1),i);
			//Changes the card so it appears as a dealer's card
			cardToAdd.setY(DEALER_CARDS_Y);
			cardToAdd.putFaceDown();
			//Adds the card to the dealer's hand
			addCard(cardToAdd);
		}
	}

	/**
	 *Returns card to discard in the dealer's hand which are unlikely to bring a high score
	 *pre: The hand is full, with 5 cards
	 */
	public ArrayList<Integer> discard()
	{
		ArrayList<Integer> removeCard = new ArrayList<Integer>(); //This will store indexes of the cards to remove
		//Converts the hand to a list of values and their frequencies to go through
		for (int i = 0; i<convert().length; i++)
		{
			//If there is only one instance of a value in the hand, the card is discarded since it is not part of a pair/group of cards
			if(convert()[i]==1)
			{
				removeCard.add(indexOf(hand,(i+1))); 
			}
		}
		//This value is returned
		return removeCard;
	}

	/**
	 *Finds the index of a card in a array of cards
	 *pre: The card array is not empty and the the value is acceptable value for a card (within the range of 1-6)
	 *post: Returns the index of that card, if card was not found in the deck it returns a value of -1
	 */
	public static int indexOf(Card[] array, int value)
	{
		for (int i = 0; i < array.length; i++) //Searches each value in the array
		{
			if (array[i].getRank()==value)
			{
				return i; //Returns only the first instance of 'value', but this method is only used for cards which are one of a kind in the hand, so this is fine
			}
		}
		//If no card has been found and the method is still running, -1 is returned
		return -1;
	}
}

