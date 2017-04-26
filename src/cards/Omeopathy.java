package cards;

import core.AbstractCard;
import core.AbstractCardEffect;
import core.Player;
import core.StaticInitializer;
import interfaces.Effect;
import interfaces.Card;
import interfaces.CardConstructor;

public class Omeopathy extends AbstractCard
{
    static private final String cardName = "Omeopathy";
			
    static private StaticInitializer initializer = new StaticInitializer(cardName, new CardConstructor()
        {
            public Card create()
            {
                return new Omeopathy();
            }
        }
    );

    public Effect getEffect(Player owner)
    {
        return new OmeopathyEffect(owner, this);
    }

    public String getName()
    {
        return cardName;
    }

    public String type()
    {
        return "Instant";
    }

    public String ruleText()
    {
        return cardName + " does nothing";
    }

    public String toString()
    {
        return getName() + "[" + ruleText() + "]";
    }

    public boolean isInstant()
    {
        return true;
    }

    private class OmeopathyEffect extends AbstractCardEffect
    {
        public OmeopathyEffect(Player player, Card card)
        {
            super(player, card);
        }

        public void resolve() {}
    }
}
