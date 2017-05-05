/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import core.AbstractCard;
import core.AbstractCreatureDecorator;
import core.AbstractEnchantment;
import core.AbstractEnchantmentCardEffect;
import core.DecoratedCreature;
import core.Game;
import core.Player;
import core.StaticInitializer;
import core.Triggers;
import interfaces.Card;
import interfaces.CardConstructor;
import interfaces.Creature;
import interfaces.Effect;
import interfaces.Enchantment;
import interfaces.TargetingEffect;
import interfaces.TriggerAction;
import java.util.ArrayList;
import java.util.Scanner;

public class AncestralMask extends AbstractCard
{
	static private final String cardName = "Ancestral Mask";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				public Card create()
				{
					return new AncestralMask();
				}
			});

	private class AncestralMaskEffect extends AbstractEnchantmentCardEffect implements TargetingEffect
	{
		private DecoratedCreature target;
		private int powerIncrement;
		private int toughnessIncrement;

		public AncestralMaskEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		protected Enchantment createEnchantment()
		{
			return new AncestralMaskEnchantment(owner);
		}
		
		public boolean play()
		{
			pickTarget();
			return super.play();
		}

		public void resolve()
		{
			if(target == null)
			{
				return;
			}

			if(target.isRemoved())
			{
				System.out.println("Attaching " + cardName + " to removed creature");
				return;
			}

			final AncestralMask.AncestralMaskDecorator decorator = new AncestralMask.AncestralMaskDecorator(powerIncrement, toughnessIncrement);
			TriggerAction action = new TriggerAction()
			{
				public void execute(Object args)
				{
					if(!target.isRemoved())
					{
						System.out.println("Triggered removal of " + cardName + " from " + target);
						target.removeDecorator(decorator);
					}
					else
					{
						System.out.println("Triggered dangling removal of " + cardName + " from removed target. Odd should have been invalidated!");
					}
				}
			};
			System.out.println("Attaching " + cardName + " to " + target.name() + " and registering end of turn trigger");
			Game.instance.getTriggers().register(Triggers.END_FILTER, action);

			decorator.setRemoveAction(action);
			target.addDecorator(decorator);
		}

		public void pickTarget()
		{
			System.out.println(owner.name() + ": choose target for " + name());

			ArrayList<DecoratedCreature> creatures = new ArrayList<>();
			int i = 1;

			Player player1 = Game.instance.getPlayer(0);
			Player player2 = Game.instance.getPlayer(1);

			for(Enchantment e : player1.getEnchantments())
			{
				powerIncrement += 2;
				toughnessIncrement += 2;
			}
			
			for(Enchantment e : player2.getEnchantments())
			{
				powerIncrement += 2;
				toughnessIncrement += 2;
			}
			
			for(DecoratedCreature c : player1.getCreatures())
			{
				if(c.canBeTargeted())
				{
					System.out.println(i + ") " + player1.name() + ": " + c);
					++i;
					creatures.add(c);
				}
			}
			for(DecoratedCreature c : player2.getCreatures())
			{
				if(c.canBeTargeted())
				{
					System.out.println(i + ") " + player2.name() + ": " + c);
					++i;
					creatures.add(c);
				}
			}

			Scanner reader = Game.instance.getScanner();
			if(reader.hasNextInt())
			{
				int idx = reader.nextInt() - 1;
				if(idx < 0 || idx >= creatures.size())
				{
					target = null;
				}
				else
				{
					target = creatures.get(idx);
				}
			}
		}

		public String toString()
		{
			if(target == null)
			{
				return super.toString() + " (no target)";
			}
			else if(target.isRemoved())
			{
				return super.toString() + " (removed creature)";
			}
			else
			{
				return name() + " [" + target.name() + " gets -1/-1 until end of turn]";
			}
		}
	}

	@Override
	public Effect getEffect(Player p)
	{
		return new AncestralMaskEffect(p, this);
	}

	private class AncestralMaskEnchantment extends AbstractEnchantment
	{
		public AncestralMaskEnchantment(Player owner)
		{
			super(owner);
		}

		@Override
		public void insert()
		{
			super.insert();
		}

		@Override
		public void remove()
		{
			super.remove();
		}

		@Override
		public String name()
		{
			return cardName;
		}
	}
	
        /*enchanted creature gets +2/+2 for each other enchantment on the battlefield*/
	class AncestralMaskDecorator extends AbstractCreatureDecorator
	{
		TriggerAction action;
		int powerIncrement;
		int toughnessIncrement;

		public AncestralMaskDecorator(int powerIncrement, int toughnessIncrement)
		{
			this.powerIncrement = powerIncrement;
			this.toughnessIncrement = toughnessIncrement;
		}
		
		public void setRemoveAction(TriggerAction a)
		{
			action = a;
		}

		public void onRemove()
		{
			System.out.println("Removing " + cardName + " and deregistering end of turn trigger");
			if(action != null)
			{
				Game.instance.getTriggers().remove(action);
			}
			
			super.onRemove();
		}

		public int power()
		{
			return decorated.power() + this.powerIncrement;
		}

		public int toughness()
		{
			return decorated.toughness() + this.toughnessIncrement;
		}
		
		@Override
		public boolean canBeTargeted()
		{
			return true;
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
		return "Enchanted creature gets +2/+2 for each other enchantment on the battlefield";
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
