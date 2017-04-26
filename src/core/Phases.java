package core;

/***
 * Game phases enum
 */
public enum Phases
{
    DRAW("Draw"), UNTAP("Untap"), COMBAT("Combat"), MAIN("Main"), END("End"), NULL("Null");
    private static final Phases[] vals = values();
    private final String name;

    Phases(String name)
    {
        this.name = name;
    }

    /***
     * Convert a phase index to its enum value
     * 
     * @param index Phase index
     * 
     * @return		Phases		Phase enum value
     */
    public static Phases idx(int index)
    {
        return vals[index];
    }

    public String toString()
    {
        return name;
    }

    /***
     * Gets the next phase
     * 
     * @return		Phases		The next phase
     */
    public Phases next()
    {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    /***
     * Gets the previous phase
     * 
     * @return		Phases		The previous phase
     */
    public Phases prev()
    {
        return vals[(this.ordinal() + vals.length - 1) % vals.length];
    }

    /***
     * Get this phase index
     * 
     * @return		int		The phase index
     */
    public int getIndex()
    {
        return this.ordinal();
    }
}