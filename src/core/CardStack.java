package core;

import interfaces.Effect;

/***
 * Represents a stack of cards
 */
public class CardStack extends BaseStack
{
    /***
     * Resolves the card stack, playing the effects and removing the cards
     */
    public void resolve()
    {
        while(!isEmpty())
        {
            Effect effect = pop();

            System.out.println("Stack: Resolving " + effect);

            effect.remove();
            effect.resolve();
        }
    }
}
