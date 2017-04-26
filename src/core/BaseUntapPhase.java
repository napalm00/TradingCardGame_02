package core;

import interfaces.Creature;
import interfaces.Phase;

/***
 * Base untap phase of the game
 */
public class BaseUntapPhase implements Phase
{
    /***
     * Untasp all creatures of the current player
     */
    public void execute()
    {
        Player currentPlayer = Game.instance.getCurrentPlayer();

        System.out.println("Untap phase of " + currentPlayer.getName());

        Game.instance.getTriggers().trigger(Triggers.UNTAP_FILTER);

        if(currentPlayer.getCreatures().isEmpty())
        {
            System.out.println("No creatures to untap");
        }

        for(Creature creature : currentPlayer.getCreatures())
        {
            System.out.println("Untapping " + creature.getName());
            creature.untap();
        }
    }
}
