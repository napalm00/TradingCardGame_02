package interfaces;

import core.Phases;

public interface PhaseManager
{
    Phases currentPhase();

    Phases nextPhase();
}
