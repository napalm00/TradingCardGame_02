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

public class AetherBarrier extends AbstractCard
{
	static private final String cardName = "Aether Barrier";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new AetherBarrier();
				}
			});

	private class AetherBarrierEffect extends AbstractEnchantmentCardEffect
	{
		public AetherBarrierEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		protected Enchantment createEnchantment()
		{
			return new AetherBarrierEnchantment(owner);
		}
	}

	@Override
	public Effect getEffect(Player p)
	{
		return new AetherBarrierEffect(p, this);
	}

	private class AetherBarrierEnchantment extends AbstractEnchantment
	{
		public AetherBarrierEnchantment(Player owner)
		{
			super(owner);
		}

		private final TriggerAction SacrificeAction = new TriggerAction()
		{
			@Override
			public void execute(Object args)
			{
				if(args != null && args instanceof Creature)
				{
					Creature c = (Creature) args;
					c.destroy();
				}
			}
		};

		@Override
		public void insert()
		{
			super.insert();
			Game.instance.getTriggers().register(Triggers.ENTER_CREATURE_FILTER, SacrificeAction);
		}

		@Override
		public void remove()
		{
			super.remove();
			Game.instance.getTriggers().remove(SacrificeAction);
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
		return "Whenever a player plays a creature spell,that player sacrifices a permanent";
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
