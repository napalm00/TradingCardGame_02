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
import core.AbstractEnchantment;
import core.Game;
import core.Phases;
import core.Player;
import core.SkipPhase;
import core.StaticInitializer;
import core.Triggers;
import interfaces.Card;
import interfaces.CardConstructor;
import interfaces.Creature;
import interfaces.Effect;
import interfaces.TriggerAction;
import interfaces.TurnManager;
import java.util.ArrayList;
import java.util.List;

public class ArgothianEnchantress extends AbstractCard
{
	static private final String cardName = "Argothian Enchantress";
	
	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new ArgothianEnchantress();
				}
			});

	@Override
	public Effect getEffect(Player p)
	{
		return new ArgothianEnchantressEffect(p, this);
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
		return "Whenever you cast an enchantment spell, draw a card";
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

	private class ArgothianEnchantressEffect extends AbstractCreatureEffect
	{
		public ArgothianEnchantressEffect(Player p, Card c)
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
			return false;
		}
		
		@Override
		public List<Effect> effects()
		{
			ArrayList<Effect> effects = new ArrayList<>();
			effects.add(new DrawEffect());
			return effects;
		}

		@Override
		public List<Effect> avaliableEffects()
		{
			ArrayList<Effect> effects = new ArrayList<>();
			if(!topDecorator.isTapped())
			{
				effects.add(new DrawEffect());
			}
			return effects;
		}
	}
	
	private class DrawEffect extends AbstractEffect
	{
		private final TriggerAction DrawCardAction = new TriggerAction()
		{
			@Override
			public void execute(Object args)
			{
				if(args != null && args instanceof AbstractEnchantment)
				{
					AbstractEnchantment enchantment = (AbstractEnchantment)args;
					if(enchantment.getOwner().equals(Game.instance.getCurrentPlayer()))
					{
						Game.instance.getCurrentPlayer().setPhase(Phases.DRAW, new SkipPhase(Phases.DRAW));
					}
				}
			}
		};
		
		@Override
		public void resolve()
		{
			Game.instance.getTriggers().register(Triggers.ENTER_ENCHANTMENT_FILTER, DrawCardAction);
		}

		@Override
		public void remove()
		{
			super.remove();
			Game.instance.getTriggers().remove(DrawCardAction);
		}

		@Override
		public String name()
		{
			return "DrawEffect";
		}

		@Override
		public String toString()
		{
			return cardName + " DrawEffect";
		}
	}
}
