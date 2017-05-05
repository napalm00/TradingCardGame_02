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

public class AetherFlash extends AbstractCard
{
	static private final String cardName = "Aether Flash";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new AetherFlash();
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

                /*this card deals 2 damage to each creature whenever comes into play*/
		private final TriggerAction DamageAction = new TriggerAction()
		{
			@Override
			public void execute(Object args)
			{
				if(args != null && args instanceof Creature)
				{
					Creature c = (Creature) args;
					c.inflictDamage(2);
				}
			}
		};

		@Override
		public void insert()
		{
			super.insert();
			Game.instance.getTriggers().register(Triggers.ENTER_CREATURE_FILTER, DamageAction);
		}

		@Override
		public void remove()
		{
			super.remove();
			Game.instance.getTriggers().remove(DamageAction);
		}

		@Override
		public String name()
		{
			return cardName;
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
		return "Whenever a creature enters comes into play, " + name() + " deals 2 damage to it";
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
