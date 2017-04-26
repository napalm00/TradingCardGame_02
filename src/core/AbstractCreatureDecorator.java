package core;

import interfaces.Creature;
import interfaces.CreatureDecorator;
import interfaces.Effect;

import java.util.List;

/***
 * Base abstract class that represents a creature decorator
 */
public abstract class AbstractCreatureDecorator implements CreatureDecorator
{
    protected Creature decorated;

    public CreatureDecorator decorate(Creature c)
    {
        decorated = c;
        return this;
    }

    /***
     * Remove the creature's decorator
     * 
     * @param decorator The decorator to remove
     * 
     * @return		Creature		The decorated creature
     */
    public Creature removeDecorator(CreatureDecorator decorator)
    {
        Creature result = this;
        if(decorator == this)
        {
            result = decorated;
            decorated = null;
            onRemove();
        }
        else if(decorated instanceof CreatureDecorator)
        {
            decorated = ((CreatureDecorator) decorated).removeDecorator(decorator);
        }
        return result;
    }

    /***
     * Card name getter
     * 
     * @return		String		The card's name
     */
    public String name()
    {
        return decorated.name();
    }

    /***
     * Get the decorated creature's power
     * 
     * @return		int		The decorated creature's power
     */
    public int power()
    {
        return decorated.power();
    }

    /***
     * Get the decorated creature's toughness
     * 
     * @return		int		The decorated creature's toughness
     */
    public int toughness()
    {
        return decorated.toughness();
    }
	
    /***
     * Tap the decorated creature
     */
    public void tap()
    {
        decorated.tap();
    }

    /***
     * Untap the decorated creature
     */
    public void untap()
    {
        decorated.untap();
    }

    /***
     * Check whether the decorated creature is tapped
     * 
     * @return		boolean		True if the creature is decorated, false otherwise
     */
    public boolean isTapped()
    {
        return decorated.isTapped();
    }

    /***
     * Check whether the decorated creature can attack
     * 
     * @return		boolean		True if the creature can attack, false otherwise
     */
    public boolean canAttack()
    {
        return decorated.canAttack();
    }

    /***
     * Make the decorated creature attack
     */
    public void attack()
    {
        decorated.attack();
    }

    /***
     * Check whether the decorated creature can defend
     * 
     * @return		boolean		True if the creature can defend, false otherwise
     */
    public boolean canDefend()
    {
        return decorated.canDefend();
    }

    /***
     * Make the decorated creature defend
     */
    public void defend()
    {
        decorated.defend();
    }

    /***
     * Inflict damage to the decorated creature
     * 
     * @param damage The amount of damage to inflict
     */
    public void inflictDamage(int damage)
    {
        decorated.inflictDamage(damage);
    }

    /***
     * Get the decorated creature's damage
     * 
     * @return		int		The amount of damage
     */
    public int getDamage()
    {
        return decorated.getDamage();
    }

    /***
     * Reset the decorated creature's damage amount
     */
    public void resetDamage()
    {
        decorated.resetDamage();
    }

    /***
     * Destroy the decorated creature
     */
    public void destroy()
    {
        decorated.destroy();
    }

    /***
     * Handler for the remove event on the decorated creature
     */
    public void onRemove()
    {
        if(decorated != null)
        {
            decorated.onRemove();
        }
    }

    /***
     * Get the list of effects
     * 
     * @return		List<Effect>		The list of effects
     */
    public List<Effect> effects()
    {
        return decorated.effects();
    }

    /***
     * Get the list of available effects
     * 
     * @return		List<Effect>		The list of available effects
     */
    public List<Effect> avaliableEffects()
    {
        return decorated.avaliableEffects();
    }

    /***
     * Get the decorated creature's decorator
     * 
     * @return		DecoratedCreature		The creature's decorator
     */
    public DecoratedCreature getTopDecorator()
    {
        return decorated.getTopDecorator();
    }

    /***
     * Set the creature's decorator
     * 
     * @param decorator The decorator to set
     */
    public void setTopDecorator(DecoratedCreature decorator)
    {
        decorated.setTopDecorator(decorator);
    }

    public String toString()
    {
        return decorated.toString();
    }
}
