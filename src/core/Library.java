package core;

import interfaces.Card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/***
 * Card library (deck) manager
 */
public class Library
{
    final private Random random = new Random();
    final private Player owner;
    private ArrayList<Card> cards = new ArrayList<>();

    /***
     * Sets the library's owner
     *
     * @param owner The owner to set
     */
    public Library(Player owner)
    {
        this.owner = owner;
    }
    
    /***
     * Shuffles the ArrayList cards
     */
    public void shuffle()
    {
        final int end = cards.size();
        for(int i = 0; i != end - 1; ++i)
        {
            int index = i + random.nextInt(end - i);
            Card tmp = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, tmp);
        }
    }
    
    /***
     * Adds a card to the ArrayList cards
     * 
     * @param card The card to add
     */
    public void add(Card card)
    {
        cards.add(card);
    }
    
    /***
     * Adds a deck to the library
     * 
     * @param deck The card deck to add
     */
    public void add(Iterator<Card> deck)
    {
        while(deck.hasNext())
        {
            cards.add(deck.next());
        }
    }
    
    /***
     * Checks if the owner's library is empty, if it's not, remove one card
     */
    Card draw()
    {
        if(cards.isEmpty())
        {
            owner.lose("No more cards");
        }

        return cards.remove(cards.size() - 1);
    }

    /***
     * Returns the number of cards in the owner's deck
     * 
     * @return		int		The number of cards
     */
    public int cardsNumber()
    {
        return cards.size();
    }
}
