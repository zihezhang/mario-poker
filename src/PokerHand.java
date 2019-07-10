/*
 * Name: Dina Bernstein
 * Class: ICS4UE
 * Teacher: Mr. Benum
 * Purpose: Holds a hand of cards with methods which allow it to be used in a poker game 
 */

//Imports libraries necessary in the code
import java.util.ArrayList;

public class PokerHand 
{
	Card[] hand; //Will hold the cards in the array
	int counter = 0; //Holds the index of the last card dealt into the hand
	public PokerHand()
	{
		hand = new Card[5]; //Hand is given a length of 5, this constructor leaves it empty
	}
	public PokerHand(int one, int two, int three, int four, int five)
	{
		hand = new Card[5]; //Hand is given a length of 5
		//Each value in the constructor is added to the hand in its index
		hand[0] = new Card(one, 0);
		hand[1] = new Card(two, 1);
		hand[2] = new Card(three, 2);
		hand[3] = new Card(four, 3);
		hand[4] = new Card(five, 4);
	}
	/**
	 * Adds a new card to the hand
	 */
	public void addCard(Card cardToAdd)
	{
		if (counter<=4) //Only adds cards if the counter is not full - that is, if its less than 5
		{
			hand[counter] = cardToAdd;
			counter++; //Counter is incremented
		}
	}
	/**
	 * Removes all cards from the hand and resets the counter
	 */
	public void clear()
	{
		for (int i = 0;i<hand.length; i++)
		{
			hand[i] = null;
		}
		counter = 0;
	}
	/**
	 * Returns the sum of each card in the hand's value
	 */
	public int getValue()
	{
		int sum = 0;
		for (int i = 0; i<=counter;i++) //Goes through each card in the hand
		{
			sum+=hand[i].getRank();
		}
		return sum;
	}
	/**
	 * Returns a specific card in the hand based on its index
	 */
	public Card getCard(int index)
	{
		return hand[index];
	}
	/**
	 * Adds cards to the hand until it is full (with 5 cards)
	 */
	public void deal()
	{
		for (int i = counter; i < 5; i++)
		{
			addCard(new Card((int)(Math.random()*6+1),i));
		}
	}
	/**
	 * Removes cards of specified indexes from the hand
	 */
	public void remove(ArrayList<Integer> cardsToRemove )
	{
		int cardsGone = cardsToRemove.size(); //Holds the amount of cards which are to be removed
		//Sets each card with an index in the cardsToRemove list as null
		for (int i = 0; i <cardsToRemove.size();i++)
		{
			hand[cardsToRemove.get(i)] = null;
		}
		counter-=cardsGone; //Decreases the counter value
		boolean nullsLeft = true;
		while (nullsLeft) //Goes through the hand and moves cards left until there are no nulls
		{
			for (int i = 0; i < hand.length-1; i++)
			{
				if (hand[i]==null && hand[i+1]!= null) //If a null is followed by a card, they switch spots
				{
					hand[i] = hand[i+1];
					hand[i+1] = null;
				}
			}
			nullsLeft = false; //NullsLeft value is temporarily set to false
			for (int i = 0; i <counter; i++) //Searches the hand (in the range of the amount of cards) for nulls
			{
				if (hand[i] ==null) //If there any nulls, the boolean is set to true, and the while loop runs again
				{
					nullsLeft = true;
				}
			}
		}

	}
	/**
	 * Sorts the hand by insertion sort
	 * Pre: Hand must have some cards in it
	 */
	public void sort()
	{
		for (int n = 1; n < counter; n++)
		{
			// Save the next element to be inserted:
			Card aTemp = hand[n];

			// Going backward from a[n-1], shift elements to the
			//   right until you find an element a[i] <= aTemp:
			int i = n;
			while (i > 0 && aTemp.getRank() < hand[i-1].getRank())
			{
				hand[i] = hand[i-1];
				i--;
			}

			// Insert the saved element after a[i]:
			hand[i] = aTemp;

			// Increment n (accomplished by n++ in the for loop).
		}
		for (int i = 0; i < hand.length;i++) //Sets each cards' index field to match their index in the array
		{
			hand[i].setIndex(i);
		}
	}
	/**
	 * Finds the score of a hand based on poker hands and ranking of each card value
	 * Pre: hand[] must have 5 cards in it
	 */
	public double findScore() 
	{
		double score = 0.0; //Will hold the final score value
		int[] values = convert(); //Creates an array with the frequency of each card of the value
		for (int i = values.length-1; i >= 0; i--) //Searches through the frequency of each card value
		{
			if (values[i]==5) //If a card is found 5 times, the player has a 5 of a kind, the best hand
			{
				score+=6.0; //A five of a kind has a score of 6
				score+=(i+1.0)/10.0; //The value of the card which is found 5 times is added to the first decimal spot, as a tiebraker
				return score;
			}
			if (values[i]==4) //Means that the player has a 4 of a kind
			{
				score+=5.0; //Has a score of 5
				score+=(i+1.0)/10.0; //The value of the card in the 4 of a kind is added to the first decimal spot
				for (int j = 0; j < values.length;j++) //Searches for the remaining card, its value is added to the second decimal place
				{
					if (values[j]==1)
					{
						score+=(j+1.0)/100.0;
					}
				}
				return score;
			}
			if (values[i]==3)//Means that the player has a full house or a 3 of a kind
			{
				score+=3.0 + (i+1.0)/10.0;//Adds 3 for now (score of a 3 of a kind), would add 1 more if it is a full house, adds the card with a frequency of 3 to the first decimal spot
				boolean cardAdded = false; //If it is a three of a kind, checks if one of the single cards has been added to the score yet
				for (int j = values.length-1; j >= 0;j--)
				{
					if (values[j]==2) //If another card has a frequency of 2, it is a full house
					{
						score+=1.0 +(j+1.0)/100.0; //Adds the second card to the second decimal spot
						return score;
					}

					if (values[j]==1) //Player has three of a kind
					{
						if (!cardAdded) //If no single cards have been added, this card is added to the second decimal spot
						{
							score+= (j+1.0)/100.0;
							cardAdded = true;
						}
						else //If a single card has been added, the card is added to the third decimal spot
						{
							score+= (j+1.0)/1000.0;
						}
					}
				}
			}
			if (values[i]==2) //Means that the player has 1 pair, 2 pairs, or a full house
			{
				score+=1.0+(i+1.0)/10.0; //Adds one for now, would add 1 more if there is another pair, and 3 more if there is a full house
				for (int j = values.length-1; j >=0; j--) //Searches through the remaining cards
				{
					if (j!=i && values[j]==2)//The player has two pairs
					{
						score+=1.0 +(j+1.0)/100.0; //The value of the smaller pair is added to the second decimal point
						for (int k = values.length-1;k>=0;k--) //Searches for the single card and adds it to the third decimal point
						{
							if (values[k]==1)
							{
								score+=(k+1.0)/1000.0;
							}
						}
						return score;
					}
					if (j!=i && values[j]==3)//The player has a full house
					{
						score-=(i+1.0)/10.0; //The value that was added to the first decimal point is removed
						score+=3.0+(j+1.0)/10.0+(i+1.0)/100.0; //Adds the triple to the first decimal spot, and the double to the second
						return score;
					}
				}
				//Means that there is only 1 pair, the values of the three single cards in descending order are added to each decimal spot
				double cardB = 100.0; //Holds the number that the card value must be divided by when added to the score (basically the decimal spot)
				for (int j = values.length-1; j >=0; j--) //Goes through the cards in descending order
				{
					if (values[j]==1)
					{
						score+=(j+1.0)/cardB; //Adds the card to the next decimal spot
						cardB*=10.0; //Multiplying this by 10 means that the next card will be added to the next decimal spot
					}			
				}
				return score;
			}
		}
		//When there are no sets
		double cardB = 10.0; //Holds the number that the card value must be divided by when added to the score (basically the decimal spot)
		for (int i = values.length-1; i>=0;i--) //Goes through the cards in descending order
		{
			if (values[i]==1) //If a card is in the hand, its value is added to the first open decimal spot
			{
				score+=(i+1.0)/cardB;
				cardB*=10.0; //Multiplying this by 10 means that the next card will be added to the next decimal spot
			}
		}
		return score;
	}
	/**
	 * Converts a hand into an array which stores how much cards there are for each rank
	 * Pre: Hand has 5 cards
	 */
	public int[] convert()
	{
		int[] handValues = new int[6]; //This array will store the frequency of cards of each values
		for (int i = 0; i < handValues.length; i++) //Initially sets each value to 0
		{
			handValues[i] = 0;
		}
		for (int i = 0; i <hand.length; i++) //Goes through each card in hand
		{
			switch (hand[i].getRank()) //Switch statement adds 1 to the index of the array for the current card's value
			{
			case 1: handValues[0]++; break;
			case 2: handValues[1]++; break;
			case 3: handValues[2]++; break;
			case 4: handValues[3]++; break;
			case 5: handValues[4]++; break;
			case 6: handValues[5]++; break;
			}
		}
		return handValues; //Array is returned
	}
	/**
	 * Checks if the PokerHand beats an opponents hand
	 * Pre: Hand and opponent have 5 cards
	 */
	public boolean findWinner(PokerHand opponent)
	{
		return findScore() > opponent.findScore(); //Returns true if the hand's score is greater than the opponent, a tie is still a loss since you need to beat the dealer
	}
}



