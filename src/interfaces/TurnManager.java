package interfaces;

import core.Player;

public interface TurnManager
{
    Player getCurrentPlayer();

    Player getCurrentAdversary();

    Player nextPlayer();
}
