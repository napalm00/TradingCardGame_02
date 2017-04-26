package core;

import interfaces.Creature;
import interfaces.DamageManager;
import interfaces.DamageManagerDecorator;

/***
 * Base abstract class that represents a damage manager decorator
 */
public abstract class AbstractDamageManagerDecorator implements DamageManagerDecorator
{
    protected DamageManager decorated;

    public DamageManagerDecorator decorate(DamageManager damageManager)
    {
        decorated = damageManager;
        return this;
    }

    /***
     * Remove the damage manager decorator
     * 
     * @param damageManagerDecorator The damage manager decorator to remove
     * 
     * @return		DamageManager		The damage manager
     */
    public DamageManager removeDecorator(DamageManagerDecorator damageManagerDecorator)
    {
        if(damageManagerDecorator == this)
        {
            DamageManager result = decorated;
            decorated = null;
            
            return result;
        }
        
        if(decorated instanceof DamageManagerDecorator)
        {
            decorated = ((DamageManagerDecorator) decorated).removeDecorator(damageManagerDecorator);
        }

        return this;
    }

    /***
     * Inflict damage to the creature
     * 
     * @param damage The amount of damage to inflict
     */
    public void inflictDamage(int damage)
    {
        decorated.inflictDamage(damage);
    }

    /***
     * Heal the creature
     * 
     * @param points The amount of health points to restore
     */
    public void heal(int points)
    {
        decorated.heal(points);
    }

    /***
     * Lose the game
     * 
     * @param string Reason for the loss
     */
    public void lose(String string)
    {
        decorated.lose(string);
    }

    /***
     * Inflict damage to a different creature
     * 
     * @param creature The creature to inflict damage to
     * @param damage The amount of damage to inflict
     */
    public void inflictDamage(Creature creature, int damage)
    {
        decorated.inflictDamage(creature, damage);
    }
}
