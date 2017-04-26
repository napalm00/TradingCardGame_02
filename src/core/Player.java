package core;

import interfaces.*;

import java.util.*;

public class Player implements Damageable
{
    private final Library library = new Library(this);
    private final ArrayList<DecoratedCreature> creatures = new ArrayList<>();
    DamageManager damageStrategyStack = new BaseDamageManager(this);
    private String name;
    private int life = 10;
    private EnumMap<Phases, Deque<Phase>> phases = new EnumMap<>(Phases.class);
    private Deque<PhaseManager> phaseManagerStack = new ArrayDeque<PhaseManager>();
    private ArrayList<Card> hand = new ArrayList<>();
    private int maxHandSize = 7;

    public Player()
    {
        phaseManagerStack.push(new BasePhaseManager());

        phases.put(Phases.DRAW, new ArrayDeque<Phase>());
        setPhase(Phases.DRAW, new BaseDrawPhase());

        phases.put(Phases.UNTAP, new ArrayDeque<Phase>());
        setPhase(Phases.UNTAP, new BaseUntapPhase());

        phases.put(Phases.COMBAT, new ArrayDeque<Phase>());
        setPhase(Phases.COMBAT, new BaseCombatPhase());

        phases.put(Phases.MAIN, new ArrayDeque<Phase>());
        setPhase(Phases.MAIN, new BaseMainPhase());

        phases.put(Phases.END, new ArrayDeque<Phase>());
        setPhase(Phases.END, new BaseEndPhase());

        phases.put(Phases.NULL, new ArrayDeque<Phase>());
    }

    /***
     * Returns the player's getName
     * 
     * @return		String		The player's getName
     */
    public String getName()
    {
        return name;
    }
    
    /***
     * Sets the player's name given in input
     * 
     * @param name The name to set 
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /***
     * Checks if the player has been removed
     * 
     * @return		boolean		If the player has been removed	
     */
    public boolean isRemoved()
    {
        return false;
    }
        
    /***
     * Accepts a GameEntityVisitor
     * 
     * @param gameEntityVisitor Game entity visitor
     */
    public void accept(GameEntityVisitor gameEntityVisitor)
    {
        gameEntityVisitor.visit(this);
    }
    
    /***
     * Returns the owner's deck
     * 
     * @return		Library		The player's deck
     */
    public Library getDeck()
    {
        return library;
    }
    
    /***
     * Adds the player's deck to the library
     * 
     * @param deck The player's deck
     */
    public void setDeck(Iterator<Card> deck)
    {
        library.add(deck);
    }

    /***
     * Returns the player's life
     * 
     * @return		int		The player's life
     */
    public int getLife()
    {
        return life;
    }
    
    /***
     * Allows to set the player's life
     * 
     * @param points The life points to set
     */
    public void setLife(int points)
    {
        life = points;
    }

    /***
     * Adds the input value to the player's life
     * 
     * @param points The points to add to the player's life
     */
    public void changeLife(int points)
    {
        life += points;
    }
    
    /***
     * Adds damages to the damageStrategyStack
     * 
     * @param damageManagerDecorator Damage to be add to the damageStrategyStack
     */
    public void addDamageStrategy(DamageManagerDecorator damageManagerDecorator)
    {
        damageStrategyStack = damageManagerDecorator.decorate(damageStrategyStack);
    }

    /***
     * If there is a DamageStrategyDecorator d in the damageStrategyStack, it will be removed
     * 
     * @param damageStrategyDecorator Damage strategy to be removed from the damageStrategyDecorator
     */
    public void removeDamageStrategy(DamageManagerDecorator damageStrategyDecorator)
    {
        if(damageStrategyStack instanceof DamageManagerDecorator)
        {
            damageStrategyStack = ((DamageManagerDecorator) damageStrategyStack).removeDecorator(damageStrategyDecorator);
        }
    }
        
    /***
     * Inserts the damage in the damageStrategyStack
     * 
     * @param points Damage points to be inflicted
     */
    public void inflictDamage(int points)
    {
        damageStrategyStack.inflictDamage(points);
    }

    /***
     * Inserts the heal in the damageStrategyStack
     * 
     * @param points Heal points 
     */
    public void heal(int points)
    {
        damageStrategyStack.heal(points);
    }
    
    /***
     * The player s loses the game
     * 
     * @param string Lose message
     */
    public void lose(String string)
    {
        damageStrategyStack.lose(string);
    }

    /***
     * Inflicts damage to a creature
     * 
     * @param creature target creature
     * @param points damage points
     */
    public void inflictDamage(Creature creature, int points)
    {
        damageStrategyStack.inflictDamage(creature, points);
    }

    void executeTurn()
    {
        // Prints the player name, life and their creatures in play
        System.out.println("Turn of Player: " + name);
        System.out.println("Life of Player: " + name + "is" + getLife());
        if(getCreatures().isEmpty())
        {
            System.out.println("No creatures on field");
        }
        else
        {
            System.out.println("Field creatures:");
            for(DecoratedCreature decoratedCreature : getCreatures())
            {
                System.out.println(decoratedCreature);
            }
        }
        System.out.println("");

        Player adversary = Game.instance.getCurrentAdversary();
        System.out.println("Adversary life: " + adversary.getLife());
        if(adversary.getCreatures().isEmpty())
        {
            System.out.println("No creatures on adversary field");
        }
        else
        {
            System.out.println("Adversary creatures on field:");
            for(DecoratedCreature decoratedCreature : adversary.getCreatures())
            {
                System.out.println(decoratedCreature);
            }
        }
        System.out.println("");

        // Goes to the next phase, if it is not null
        Phase cur_phase;
        while((cur_phase = getPhase(nextPhase())) != null)
        {
            try
            {
                cur_phase.execute();
            }
            catch(InterruptedException interruptedException)
            {
                interruptedException.printStackTrace();
            }
        }
    }
    
    /***
     * Returns the specific phase
     * 
     * @param phase The phase
     * 
     * @return		Phase		The corresponding phase
     */
    public Phase getPhase(Phases phase)
    {
        return phases.get(phase).peek();
    }

    /***
     * Sets the phase 
     * 
     * @param phaseID The phase ID
     * @param phase The phase
     */
    public void setPhase(Phases phaseID, Phase phase)
    {
        phases.get(phaseID).push(phase);
    }

    /***
     * Removes the phase
     * 
     * @param phaseID The phase ID
     * @param phase The phase
     */
    public void removePhase(Phases phaseID, Phase phase)
    {
        phases.get(phaseID).remove(phase);
    }

    /***
     * Pushes the PhaseManger into the phaseManagerStack
     * 
     * @param phaseManager The phaseManager to be set
     */
    public void setPhaseManager(PhaseManager phaseManager)
    {
        phaseManagerStack.push(phaseManager);
    }
    
    /***
     * Removes the PhaseManager from the phaseManagerStack
     * 
     * @param phaseManager The phaseManager to be removed
     */
    public void removePhaseManager(PhaseManager phaseManager)
    {
        phaseManagerStack.remove(phaseManager);
    }
    
    /***
     * Returns the current phase
     * 
     * @return		Phases		The current phase
     */
    public Phases currentPhase()
    {
        return phaseManagerStack.peek().currentPhase();
    }
    
    /***
     * Returns the next phase
     * 
     * @return		Phases		The next phase
     */
    public Phases nextPhase()
    {
        return phaseManagerStack.peek().nextPhase();
    }
    
    /***
     * Returns the ArrayList hand
     * 
     * @return		List<Card>	The hand
     */
    public List<Card> getHand()
    {
        return hand;
    }
    
    /***
     * Returns the maximum size of the ArrayList hand
     * 
     * @return		int		The maximum hand size
     */
    public int getMaxHandSize()
    {
        return maxHandSize;
    }
    
    /***
     * Sets the maximum size of the ArrayList hand
     * 
     * @param size The maximum size to set for the hand
     */
    public void setMaxHandSize(int size)
    {
        maxHandSize = size;
    }

    /***
     * Allows to draw a card and prints the card drawn; the card is added to the player hand
     */
    public void draw()
    {
        Card drawn = library.draw();
        System.out.println(getName() + " has drawn: " + drawn.getName());
        hand.add(drawn);
    }

    /***
     * Allows to discard a card: prints all the cards in the player's hand and, if the index read is correct, the corresponding card is removed from the player hand
     */
    public void selectDiscard()
    {
        Scanner reader = Game.instance.getScanner();

        System.out.println(getName() + " discard a card: ");
        for(int i = 0; i != hand.size(); ++i)
        {
            System.out.println(Integer.toString(i + 1) + ") " + hand.get(i));
        }

        int index = reader.nextInt() - 1;
        if(index >= 0 && index < hand.size())
        {
            hand.remove(index);
        }
    }
    
    /***
     * Returns the ArrayList creatures
     *  @return		List<DecoratedCreature>		The creature list
     */
    public List<DecoratedCreature> getCreatures()
    {
        return creatures;
    }
}
