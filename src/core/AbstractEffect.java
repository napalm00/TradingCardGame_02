package core;

import interfaces.Effect;
import interfaces.GameEntityVisitor;

/***
 * Base abstract class that represents an effect
 */
public abstract class AbstractEffect extends AbstractGameEntity implements Effect
{
    /***
     * Plays the effect
     * 
     * @return		boolean		True by default
     */
    public boolean play()
    {
        Game.instance.getCardStack().push(this);
        return true;
    }

    /***
     * Removes the effect
     */
    public void remove()
    {
        isRemoved = true;
    }

    /***
     * Accepts a visitor
     * 
     * @param gameEntityVisitor The visitor to accept
     */
    public void accept(GameEntityVisitor gameEntityVisitor)
    {
        gameEntityVisitor.visit(this);
    }
}
