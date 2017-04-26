package cards;

import core.AbstractCard;
import core.AbstractCreatureEffect;
import core.Player;
import core.StaticInitializer;
import core.AbstractCreature;
import interfaces.CardConstructor;
import interfaces.Card;
import interfaces.Creature;
import interfaces.Effect;

public class BronzeSable extends AbstractCard
{
	static private final String cardName = "Bronze Sable";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new BronzeSable();
				}
			});

	@Override
	public String name()
	{
		return cardName;
	}

	@Override
	public String type()
	{
		return "Creature";
	}

	@Override
	public String ruleText()
	{
		return "Put in play a creature " + cardName + "(2/1)";
	}

	@Override
	public String toString()
	{
		return name() + "[" + ruleText() + "]";
	}

	@Override
	public boolean isInstant()
	{
		return false;
	}

	@Override
	public Effect getEffect(Player p)
	{
		return new BronzeSableEffect(p, this);
	}

	private class BronzeSableEffect extends AbstractCreatureEffect
	{
		public BronzeSableEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		protected Creature createCreature()
		{
			return new BronzeSableCreature(owner);
		}

	}

	private class BronzeSableCreature extends AbstractCreature
	{

		BronzeSableCreature(Player owner)
		{
			super(owner);
		}

		@Override
		public String name()
		{
			return cardName;
		}

		@Override
		public int power()
		{
			return 2;
		}

		@Override
		public int toughness()
		{
			return 1;
		}
		
		@Override
		public boolean canBeTargeted()
		{
			return true;
		}

	}

}
