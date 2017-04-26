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

public class NorwoodRanger extends AbstractCard
{
	static private final String cardName = "Norwood Ranger";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new NorwoodRanger();
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
		return "Put in play a creature " + cardName + "(1/2)";
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
		return new NorwoodRangerEffect(p, this);
	}

	private class NorwoodRangerEffect extends AbstractCreatureEffect
	{
		public NorwoodRangerEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		protected Creature createCreature()
		{
			return new NorwoodRangerCreature(owner);
		}
	}

	private class NorwoodRangerCreature extends AbstractCreature
	{

		NorwoodRangerCreature(Player owner)
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
			return 1;
		}

		@Override
		public int toughness()
		{
			return 2;
		}
		
		@Override
		public boolean canBeTargeted()
		{
			return true;
		}
	}

}
