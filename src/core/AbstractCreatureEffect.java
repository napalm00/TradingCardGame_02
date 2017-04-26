package core;

import interfaces.Card;
import interfaces.Creature;

/***
 * Base abstract class that represents a creature's effect
 */
public abstract class AbstractCreatureEffect extends AbstractCardEffect
{
    protected AbstractCreatureEffect(Player owner, Card card)
    {
        super(owner, card);
    }

    protected abstract Creature createCreature();

    /***
     * Play the effect, adding the creature to the owner's list of creatures, and triggering the corresponding filter
     */
    public void resolve()
    {
        owner.getCreatures().add(new DecoratedCreature(owner, createCreature()));
        Game.instance.getTriggers().trigger(Triggers.ENTER_CREATURE_FILTER);
    }
}
