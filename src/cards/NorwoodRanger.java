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
				public Card create()
				{
					return new NorwoodRanger();
				}
			});

	public String name()
	{
		return cardName;
	}

	public String type()
	{
		return "Creature";
	}

	public String ruleText()
	{
		return "Put in play a creature " + cardName + "(1/2)";
	}

	public String toString()
	{
		return name() + "[" + ruleText() + "]";
	}

	public boolean isInstant()
	{
		return false;
	}

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

		public String name()
		{
			return cardName;
		}

		public int power()
		{
			return 1;
		}

		public int toughness()
		{
			return 2;
		}

	}

}
