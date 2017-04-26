package interfaces;

import core.Player;

public interface Card extends GameEntity
{
    void remove();

    Effect getEffect(Player owner);

    String type();

    String ruleText();

    boolean isInstant();
}
