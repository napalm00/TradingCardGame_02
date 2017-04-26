package core;

/***
 * Represents an End-of-game exception
 */
public class EndOfGame extends RuntimeException
{
    public EndOfGame() {}

    public EndOfGame(String message)
    {
        super(message);
    }
}
