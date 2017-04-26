package core;

import interfaces.*;

import java.util.List;

/***
 * Represents a decorated creature
 */
public class DecoratedCreature implements Damageable, Creature
{
    private Creature decorated;
    private Player owner;

    public DecoratedCreature(Player owner, Creature creature)
    {
        this.owner = owner;
        decorated = creature;
        decorated.setTopDecorator(this);
    }

    /***
     * Adds a decorator to the creature
     * 
     * @param creatureDecorator The decorator to add
     */
    public void addDecorator(CreatureDecorator creatureDecorator)
    {
        decorated = creatureDecorator.decorate(decorated);
        if(getDamage() >= toughness())
        {
            remove();
        }
    }

    /***
     * Removes the creature
     */
    public void remove()
    {
        owner.getCreatures().remove(this);
        System.out.println("Field: Removing " + name());
        onRemove();
        Game.instance.getTriggers().trigger(Triggers.EXIT_CREATURE_FILTER, this);
    }

    /***
     * Handler called when the creature is removed
     */
    public void onRemove()
    {
        decorated.onRemove();
        decorated = null;
    }

    /***
     * Check whether the creature has been removed
     * 
     * @return		boolean		True if the creature has been removed, false otherwise
     */
    public boolean isRemoved()
    {
        return decorated == null;
    }

    /***
     * Accepts a visitor
     * 
     * @param gameEntityVisitor Visitor to accept
     */
    public void accept(GameEntityVisitor gameEntityVisitor)
    {
        gameEntityVisitor.visit(this);
    }

    /***
     * Gets the creature's name
     * 
     * @return		String		The creature's name
     */
    public String name()
    {
        return decorated.name();
    }

    /***
     * Gets the creature's power (damage capability)
     * 
     * @return		int		The creature's power
     */
    public int power()
    {
        return Math.max(0, decorated.power());
    }

    /***
     * Gets the creature's toughness (defense capability)
     * 
     * @return		int		The creature's toughness
     */
    public int toughness()
    {
        return Math.max(0, decorated.toughness());
    }

    /***
     * Taps the creature
     */
    public void tap()
    {
        decorated.tap();
    }

    /***
     * Untaps the creature
     */
    public void untap()
    {
        decorated.untap();
    }

    /***
     * Checks whether the creature is tapped
     * 
     * @return		boolean		True if the creature is tapped, false otherwise
     */
    public boolean isTapped()
    {
        return decorated.isTapped();
    }

    /***
     * Checks whether the creature can attack
     * 
     * @return		boolean		True if the creature can attack, false otherwise
     */
    public boolean canAttack()
    {
        return decorated.canAttack();
    }

    /***
     * Makes the creature attack
     */
    public void attack()
    {
        decorated.attack();
    }

    /***
     * Checks whether the creature can defend
     * 
     * @return		boolean		True if the creature can defend, false otherwise
     */
    public boolean canDefend()
    {
        return decorated.canDefend();
    }

    /***
     * Makes the creature defend
     */
    public void defend()
    {
        decorated.defend();
    }

    /***
     * Inflicts damage to the creature
     * 
     * @param damage Amount of damage to inflict
     */
    public void inflictDamage(int damage)
    {
        owner.inflictDamage(decorated, damage);
    }

    /***
     * Get the creature's damage
     * 
     * @return		int		The creature's damage amount
     */
    public int getDamage()
    {
        return decorated.getDamage();
    }

    /***
     * Resets the creature's damage
     */
    public void resetDamage()
    {
        decorated.resetDamage();
    }

    /***
     * Destroys the creature
     */
    public void destroy()
    {
        decorated.destroy();
    }

    /***
     * Lists the creature's effects
     * 
     * @return		List<Effect>		The creature's effects
     */
    public List<Effect> effects()
    {
        return decorated.effects();
    }

    /***
     * Lists the creature's available effects
     * 
     * @return		List<Effect>		The creature's available effects
     */
    public List<Effect> avaliableEffects()
    {
        return decorated.avaliableEffects();
    }

    /***
     * Remove a creature decorator
     * 
     * @param creatureDecorator The decorator to remove
     */
    public void removeDecorator(CreatureDecorator creatureDecorator)
    {
        if(decorated instanceof CreatureDecorator)
        {
            decorated = ((CreatureDecorator) decorated).removeDecorator(creatureDecorator);
            if(getDamage() >= toughness())
            {
                destroy();
            }
        }
    }

    /***
     * Get the creature's decorator (this class)
     * 
     * @return		DecoratedCreature		This.class
     */
    public DecoratedCreature getTopDecorator()
    {
        return this;
    }

    /***
     * Implemented, unused
     * @param decoratedCreature 
     */
    public void setTopDecorator(DecoratedCreature decoratedCreature)
    {
    }

    public String toString()
    {
        return decorated.toString();
    }
}
