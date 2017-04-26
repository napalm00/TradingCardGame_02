package core;

import interfaces.Effect;
import interfaces.GameEntityVisitor;

/***
 * Base abstract class that represents an effect
 */
public abstract class AbstractEnchantment extends AbstractGameEntity implements Effect
{
    /***
     * Plays the enchantment
     * 
     * @return		boolean		True by default
     */
    public boolean play()
    {
        Game.instance.getEnchantmentStack().push(this);
        return true;
    }

    /***
     * Resolves the enchantment, triggering the corresponding event
     */
    public void resolve()
    {   
        Game.instance.getTriggers().trigger(Triggers.ENTER_ENCHANTMENT_FILTER);
    }
	
    /***
     * Removes the enchantment, triggering the corresponding event
     */
    public void remove()
    {
        Game.instance.getTriggers().trigger(Triggers.EXIT_ENCHANTMENT_FILTER);
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
