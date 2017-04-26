package core;

import interfaces.TurnManager;

/***
 * Base turn manager of the game
 */
public class BaseTurnManager implements TurnManager
{
    private final Player[] players;
    int currentPlayerID = 1;

    public BaseTurnManager(Player[] players)
    {
        this.players = players;
    }

    /***
     * Get the current player playing the turn
     * 
     * @return		Player		The current player playing the turn
     */
    public Player getCurrentPlayer()
    {
        return players[currentPlayerID];
    }

    /***
     * Get the current adversary of the player playing the turn
     * 
     * @return		Player		The current player's adversary
     */
    public Player getCurrentAdversary()
    {
        return players[(currentPlayerID + 1) % 2];
    }

    /***
     * Switch turn to the next player
     * 
     * @return		Player		The next player
     */
    public Player nextPlayer()
    {
        currentPlayerID = (currentPlayerID + 1) % 2;
        return getCurrentPlayer();
    }
}
