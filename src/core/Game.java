package core;

import interfaces.TurnManager;
import interfaces.Card;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class Game
{
    public static final Game instance = new Game();

    // Players
    private final Player[] players = new Player[2];

    // Turns
    private final Deque<TurnManager> turnManagerStack = new ArrayDeque<>();

    // Card stack
    private CardStack cardStack = new CardStack();

    // Triggers
    private Triggers triggers = new Triggers();

    // IO
    private Scanner reader = new Scanner(System.in);
    
    /***
     * Inserts in the turn manager a base turn.
     * Creates the two players, inserting them in the players ArrayList and setting their name to Player 1 and Player 2
     */
    private Game()
    {
        turnManagerStack.push(new BaseTurnManager(players));

        players[0] = new Player();
        players[0].setName("Player 1");

        players[1] = new Player();
        players[1].setName("Player 2");
    }

    public static void main(String[] args)
    {
        System.out.println("Game is starting\n");
        
       instance.setup();
       instance.run();
    }

    /***
     * Creates a new ArrayList for cards and sets name and deck for both players.
     * Cards for each player deck are in two files containing a card per line. 
     * If a deck can't be read throws an exception 
     */
    public void setup()
    {
        System.out.println("Please write player 1's name");
        players[0].setName(reader.nextLine());
    
        int count = 0;
        
        ArrayList<Card> playerOneDeck = new ArrayList<>();
        System.out.println(players[0].name() + " give the filename of your deck");
        try
        {
            Scanner playerOneDeckFile = new Scanner(new File(reader.nextLine()));
            while(playerOneDeckFile.hasNextLine() && count <= 20)
            {
                playerOneDeck.add(CardFactory.construct(playerOneDeckFile.nextLine()));
                count++;
            }
        }
        catch(IOException ex)
        {
            throw new RuntimeException("Cannot read player 1's deck file");
        }
        players[0].setDeck(playerOneDeck.iterator());

        System.out.println("Please write player 2's name");
        players[1].setName(reader.nextLine());
        
        count = 0;

        ArrayList<Card> playerTwoDeck = new ArrayList<>();
        System.out.println(players[1].name() + " give the filename of your deck");
        try
        {
            Scanner playerTwoDeckFile = new Scanner(new File(reader.nextLine()));
            while(playerTwoDeckFile.hasNextLine() && count <= 20)
            {
                playerTwoDeck.add(CardFactory.construct(playerTwoDeckFile.nextLine()));
                count++;
            }
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Cannot read player 1's deck file");
        }
        players[1].setDeck(playerTwoDeck.iterator());
    }
    
    /***
     * Shuffles players decks and the player draws five cards: there is a check on he cards ArrayList, if the player's cards ArrayList is not empty it returns a card which will be added to the player's hand.
     * Stars the game for both players and checks if there is an end game exception.
     * The other run() methods manage game phases.
     */
    public void run()
    {
        players[0].getDeck().shuffle();
        players[1].getDeck().shuffle();

        for(int i = 0; i < 5; i++)
        {
            players[0].draw();
        }
        for(int i = 0; i < 5; i++)
        {
            players[1].draw();
        }

        players[0].setPhase(Phases.DRAW, new SkipPhase(Phases.DRAW));

        try
        {
            while(true)
            {
                instance.nextPlayer().executeTurn();
            }
        }
        catch(EndOfGame exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    /***
     * Adds a turn to the TurnManager
     * 
     * @param turnManager The turn manager to add
     */
    public void setTurnManager(TurnManager turnManager)
    {
        turnManagerStack.push(turnManager);
    }
    
    /***
     * Removes a turn from the TurnManager
     * 
     * @param turnManager The turn manager to remove
     */
    public void removeTurnManager(TurnManager turnManager)
    {
        turnManagerStack.remove(turnManager);
    }

    /***
     * Returns the player at the given index
     * 
     * @param index The player's index
     * 
     * @return		Player		The player at the given index, or null if out of bounds
     */
    public Player getPlayer(int index)
    {
        if(index < 0 || index >= players.length)
        {
            return null;
        }
		
        return players[index];
    }

    /***
     * Returns the current player
     * 
     * @return		Player		The current player
     */
    public Player getCurrentPlayer()
    {
        return turnManagerStack.peek().getCurrentPlayer();
    }

    /***
     * Returns the current adversary player
     * 
     * @return		Player		The current adversary
     */
    public Player getCurrentAdversary()
    {
        return turnManagerStack.peek().getCurrentAdversary();
    }

    /***
     * Returns the player which will play the next turn 
     * 
     * @return		Player		The next player
     */
    public Player nextPlayer()
    {
        return turnManagerStack.peek().nextPlayer();
    }
    
    /***
     * Returns the cardStack
     *	
     * @return		CardStack       The card stack structure
     */
    public CardStack getCardStack()
    {
        return cardStack;
    }

    /***
     * Gets the trigger manager (used to add and remove triggers)
     * 
     * @return		Triggers		The trigger manager
     */
    public Triggers getTriggers()
    {
        return triggers;
    }

    /*** 
     * Gets the console scanner (System.in)
     * 
     * @return		Scanner		The console scanner
     */
    public Scanner getScanner()
    {
        return reader;
    }
}
