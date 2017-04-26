package core;

import interfaces.Card;
import interfaces.GameEntityVisitor;

/***
 * Base abstract class that represents a card
 */
public abstract class AbstractCard extends AbstractGameEntity implements Card
{
    public void accept(GameEntityVisitor gameEntityVisitor)
    {
        gameEntityVisitor.visit(this);
    }
}
