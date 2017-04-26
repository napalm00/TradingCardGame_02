/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import core.AbstractCard;
import core.AbstractEnchantment;
import core.AbstractEnchantmentCardEffect;
import core.Game;
import core.Player;
import core.StaticInitializer;
import core.Triggers;
import interfaces.Card;
import interfaces.CardConstructor;
import interfaces.Creature;
import interfaces.Effect;
import interfaces.Enchantment;
import interfaces.TriggerAction;

public class FriendlyEnvironment extends AbstractCard
{
	static private final String cardName = "FriendlyEnvironment";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				public Card create()
				{
					return new FriendlyEnvironment();
				}
			});

	private class FriendlyEnvironmentEffect extends AbstractEnchantmentCardEffect
	{
		public FriendlyEnvironmentEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		protected Enchantment createEnchantment()
		{
			return new FriendlyEnvironmentEnchantment(owner);
		}
	}

	@Override
	public Effect getEffect(Player p)
	{
		return new FriendlyEnvironmentEffect(p, this);
	}

	private class FriendlyEnvironmentEnchantment extends AbstractEnchantment
	{
		public FriendlyEnvironmentEnchantment(Player owner)
		{
			super(owner);
		}

		private final TriggerAction GreetingAction = new TriggerAction()
		{
			@Override
			public void execute(Object args)
			{
				if(args != null && args instanceof Creature)
				{
					Creature c = (Creature) args;
					System.out.println(name() + " says: \"Welcome " + c.name() + "!\"");
				}
			}
		};

		@Override
		public void insert()
		{
			super.insert();
			Game.instance.getTriggers().register(Triggers.ENTER_CREATURE_FILTER, GreetingAction);
		}

		@Override
		public void remove()
		{
			super.remove();
			Game.instance.getTriggers().remove(GreetingAction);
		}

		@Override
		public String name()
		{
			return "Friendly Environment";
		}
	}

	@Override
	public String name()
	{
		return cardName;
	}

	@Override
	public String type()
	{
		return "Enchantment";
	}

	@Override
	public String ruleText()
	{
		return "Whenever a creature enters the game " + name() + " welcomes it";
	}

	@Override
	public String toString()
	{
		return name() + " (" + type() + ") [" + ruleText() + "]";
	}

	@Override
	public boolean isInstant()
	{
		return false;
	}
}
