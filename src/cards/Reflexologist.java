/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import core.AbstractCard;
import core.AbstractCreature;
import core.AbstractCreatureEffect;
import core.AbstractEffect;
import core.Player;
import core.StaticInitializer;
import interfaces.Card;
import interfaces.CardConstructor;
import interfaces.Creature;
import interfaces.Effect;
import java.util.ArrayList;
import java.util.List;

public class Reflexologist extends AbstractCard
{
	static private final String cardName = "Reflexologist";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new Reflexologist();
				}
			});

	@Override
	public Effect getEffect(Player p)
	{
		return new ReflexologistEffect(p, this);
	}

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
		return "Put in play a creature " + cardName + "(0/1) with tap: " + cardName + " does nothing";
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

	private class ReflexologistEffect extends AbstractCreatureEffect
	{
		public ReflexologistEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		protected Creature createCreature()
		{
			return new ReflexologistCreature(owner);
		}

	}

	private class ReflexologistCreature extends AbstractCreature
	{

		ReflexologistCreature(Player owner)
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
			return 0;
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
		
		@Override
		public List<Effect> effects()
		{
			ArrayList<Effect> effects = new ArrayList<>();
			effects.add(new Reflexology());
			return effects;
		}

		@Override
		public List<Effect> avaliableEffects()
		{
			ArrayList<Effect> effects = new ArrayList<>();
			if(!topDecorator.isTapped())
			{
				effects.add(new Reflexology());
			}
			return effects;
		}

		private class Reflexology extends AbstractEffect
		{
			@Override
			public void resolve()
			{
			}

			@Override
			public String name()
			{
				return "Reflexology";
			}

			@Override
			public String toString()
			{
				return cardName + " tap: does nothing";
			}
		}
	}

}
