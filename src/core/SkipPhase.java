package core;

import interfaces.Phase;

/***
 * "Fake" phase to skip a phase for the player
 */
public class SkipPhase implements Phase
{
    private final Phases phaseID;
    private int skipCount;

    public SkipPhase(Phases id)
    {
        phaseID = id;
        skipCount = 1;
    }

    public SkipPhase(Phases id, int skip)
    {
        phaseID = id;
        skipCount = skip;
    }

    /***
     * Skip the phase for the given amount of times
     */
    public void execute()
    {
        Player currentPlayer = Game.instance.getCurrentPlayer();
        System.out.println(currentPlayer.name() + " is skipping the " + phaseID + " phase");
        skipCount--;
		
        if(skipCount == 0)
        {
            currentPlayer.removePhase(phaseID, this);
        }
    }
}
