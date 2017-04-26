package core;

import interfaces.PhaseManager;

/***
 * Base phase manager of the game
 */
public class BasePhaseManager implements PhaseManager
{
    private Phases currentPhaseID = Phases.NULL;

    /***
     * Get the current phase of the game
     * 
     * @return		Phases		Current phase ID (enum)
     */
    public Phases currentPhase()
    {
        return currentPhaseID;
    }

    /***
     * Switch to the next phase of the game
     * 
     * @return		Phases		Next phase ID (enum)
     */
    public Phases nextPhase()
    {
        currentPhaseID = currentPhaseID.next();
        return currentPhase();
    }
}
