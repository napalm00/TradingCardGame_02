package core;

import interfaces.Card;

/***
 * Base abstract class that represents a card's effect
 */
public abstract class AbstractCardEffect extends AbstractEffect
{
    protected Player owner;
    protected Card card;

    protected AbstractCardEffect(Player owner, Card card)
    {
        this.owner = owner;
        this.card = card;
    }

    /***
     * Play the card, removing it from the player's hand
     * 
     * @return		boolean		Super class play() call return value
     */
    public boolean play()
    {
        owner.getHand().remove(card);
        card.remove();
        
        return super.play();
    }

    /***
     * Card name getter
     * 
     * @return		String		The card's name
     */
    public String getName()
    {
        return card.getName();
    }

    public String toString()
    {
        return card.toString();
    }
}
