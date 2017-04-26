package core;

import interfaces.Phase;

/***
 * Draw phase of the game
 */
public class BaseDrawPhase implements Phase
{
    /***
     * Execute the draw phase.
     * Draws the current player's cards from the deck, discarding extra ones
     */
    public void execute()
    {
        Player currentPlayer = Game.instance.getCurrentPlayer();

        System.out.println("Draw phase of " + currentPlayer.getName());

        Game.instance.getTriggers().trigger(Triggers.DRAW_FILTER);
        currentPlayer.draw();

        while(currentPlayer.getHand().size() > currentPlayer.getMaxHandSize())
        {
            currentPlayer.selectDiscard();
        }
    }
}
