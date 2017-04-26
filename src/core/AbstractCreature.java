package core;

import interfaces.Creature;
import interfaces.Effect;

import java.util.ArrayList;
import java.util.List;

/***
 * Base abstract class that represents a creature
 */
public abstract class AbstractCreature implements Creature
{
    protected Player owner;
    protected boolean isTapped = false;
    protected int damage = 0;
    protected DecoratedCreature topDecorator;

    protected AbstractCreature(Player owner)
    {
        this.owner = owner;
    }

    /***
     * Tap a creature, disabling it
     */
    public void tap()
    {
        if(isTapped)
        {
            System.out.println(topDecorator.name() + " is already tapped");
        }
        else
        {
            System.out.println("Tapping " + topDecorator.name());
            isTapped = true;
        }
    }

    /***
     * Untap a tapped creature, re-enabling it
     */
    public void untap()
    {
        if(!isTapped)
        {
            System.out.println(topDecorator.name() + " is not tapped");
        }
        else
        {
            System.out.println("Untapping " + topDecorator.name());
            isTapped = false;
        }
    }

    /***
     * Check whether a creature is tapped or not
     * 
     * @return		boolean		True if the creature is tapped, false otherwise
     */
    public boolean isTapped()
    {
        return isTapped;
    }

    /***
     * Check whether a creature can attack
     * 
     * @return		boolean		True if the creature can attack, false otherwise
     */
    public boolean canAttack()
    {
        return !isTapped;
    }

    /***
     * Check whether a creature can defend
     * 
     * @return		boolean		True if the creature can defend, false otherwise
     */
    public boolean canDefend()
    {
        return !isTapped;
    }
	
    /***
     * Make the creature attack, tapping it
     */
    public void attack()
    {
        topDecorator.tap();
    }

    public void defend()
    {
    }

    /***
     * Inflicts damage to this creature
     * 
     * @param damage Damage amount
     */
    public void inflictDamage(int damage)
    {
        this.damage += damage;
        if(this.damage >= topDecorator.toughness())
        {
            topDecorator.destroy();
        }
    }

    /***
     * Get creature's damage
     * 
     * @return		int		The creature's damage
     */
    public int getDamage()
    {
        return damage;
    }

    /***
     * Destroy the creature
     */
    public void destroy()
    {
        topDecorator.remove();
    }

    /***
     * Handler for the creature remove event
     */
    public void onRemove()
    {
        System.out.println("Removing " + name());
    }

    /***
     * Reset the creature's damage
     */
    public void resetDamage()
    {
        damage = 0;
    }

    /***
     * Get the creature's decorator
     * 
     * @return		DecoratedCreature		The decorator
     */
    public DecoratedCreature getTopDecorator()
    {
        return topDecorator;
    }

    /***
     * Set the creature's decorator
     * 
     * @param decoratedCreature The creature decorator
     */
    public void setTopDecorator(DecoratedCreature decoratedCreature)
    {
        topDecorator = decoratedCreature;
        if(damage >= topDecorator.toughness())
        {
            topDecorator.destroy();
        }
    }

    public String toString()
    {
        return topDecorator.name() + "(" + topDecorator.power() + "/" + topDecorator.toughness() + ")";
    }

    /***
     * Check whether the decorated creature has been removed
     * 
     * @return		boolean		True if the decorated creature has been removed, false otherwise
     */
    public boolean isRemoved()
    {
        return topDecorator.isRemoved();
    }

    /***
     * List all of the creature's effects
     * 
     * @return		List<Effect>		A List containing the effects
     */
    public List<Effect> effects()
    {
        return new ArrayList<>();
    }

    /***
     * List all of the creature's available effects
     * 
     * @return		List<Effect>		A List containing the available effects
     */
    public List<Effect> avaliableEffects()
    {
        return new ArrayList<>();
    }
}
