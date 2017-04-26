package core;

import interfaces.Phase;

/***
 * Main phase of the game
 */
public class BaseMainPhase implements Phase
{
    /***
     * Asks players to choose which cards to play, and resolves the enchantment and card stacks
     */
    public void execute()
    {
        Player currentPlayer = Game.instance.getCurrentPlayer();
        int responsePlayerID = (Game.instance.getPlayer(0) == currentPlayer) ? 1 : 0;

        System.out.println("Main phase of " + currentPlayer.getName());

        Game.instance.getTriggers().trigger(Triggers.MAIN_FILTER);
        int numberOfPasses = 0;

        if(!Utilities.playAllAvailableEffects(currentPlayer, true))
        {
            ++numberOfPasses;
        }

        while(numberOfPasses < 2)
        {
            if(Utilities.playAllAvailableEffects(Game.instance.getPlayer(responsePlayerID), false))
            {
                numberOfPasses = 0;
            }
            else
            {
                numberOfPasses++;
            }

            responsePlayerID = (responsePlayerID + 1) % 2;
        }

        Game.instance.getCardStack().resolve();
    }
}
