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
		@Override
		public Card create()
		{
			return new Omeopathy();
		}
	}
	);

	@Override
	public Effect getEffect(Player owner)
	{
		return new OmeopathyEffect(owner, this);
	}

	@Override
	public String name()
	{
		return cardName;
	}

	@Override
	public String type()
	{
		return "Instant";
	}

	@Override
	public String ruleText()
	{
		return cardName + " does nothing";
	}

	@Override
	public String toString()
	{
		return name() + "[" + ruleText() + "]";
	}

	@Override
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

		@Override
		public void resolve()
		{
		}
	}
}
