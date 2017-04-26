package core;

import interfaces.Creature;
import interfaces.Phase;

/***
 * End phase of the game
 */
public class BaseEndPhase implements Phase
{
    /***
     * Resets the damage on the creatures of both players
     */
    public void execute()
    {
        Player currentPlayer = Game.instance.getCurrentPlayer();

        System.out.println("End phase of" + currentPlayer.getName());

        for(Creature creature : currentPlayer.getCreatures())
        {
            System.out.println(creature.getName() + " damage resetted");
            creature.resetDamage();
        }

        for(Creature creature : Game.instance.getCurrentAdversary().getCreatures())
        {
            System.out.println(creature.getName() + " of " + Game.instance.getCurrentAdversary().getName() + " damage resetted");
            creature.resetDamage();
        }

        Game.instance.getTriggers().trigger(Triggers.END_FILTER);
    }
}
