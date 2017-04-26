package interfaces;

import core.DecoratedCreature;
import core.Player;

public interface GameEntityVisitor
{
    void visit(Player player);

    void visit(Card card);

    void visit(Effect effect);

    void visit(DecoratedCreature decoratedCreature);
}
