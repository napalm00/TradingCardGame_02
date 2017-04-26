package cards;

import core.AbstractCard;
import core.AbstractCardEffect;
import core.AbstractCreatureDecorator;
import core.DecoratedCreature;
import core.Game;
import core.Player;
import core.StaticInitializer;
import core.Triggers;
import interfaces.CardConstructor;
import interfaces.Card;
import interfaces.Effect;
import interfaces.TargetingEffect;
import interfaces.TriggerAction;
import java.util.ArrayList;
import java.util.Scanner;

public class Afflict extends AbstractCard
{

	static private final String cardName = "Afflict";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new Afflict();
				}
			});

	@Override
	public Effect getEffect(Player owner)
	{
		return new AfflictEffect(owner, this);
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
		return "Target creature gets -1/-1 until end of turn";
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
	
	private class AfflictEffect extends AbstractCardEffect implements TargetingEffect
	{
		private DecoratedCreature target;

		public AfflictEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		public boolean play()
		{
			pickTarget();
			return super.play();
		}

		@Override
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

			final AfflictDecorator decorator = new AfflictDecorator();
			TriggerAction action = new TriggerAction()
			{
				@Override
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

		@Override
		public void pickTarget()
		{
			System.out.println(owner.name() + ": choose target for " + name());

			ArrayList<DecoratedCreature> creatures = new ArrayList<>();
			int i = 1;

			Player player1 = Game.instance.getPlayer(0);
			Player player2 = Game.instance.getPlayer(1);

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

		@Override
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

	class AfflictDecorator extends AbstractCreatureDecorator
	{
		TriggerAction action;

		public void setRemoveAction(TriggerAction a)
		{
			action = a;
		}

		@Override
		public void onRemove()
		{
			System.out.println("Removing " + cardName + " and deregistering end of turn trigger");
			if(action != null)
			{
				Game.instance.getTriggers().remove(action);
			}
			super.onRemove();
		}

		@Override
		public int power()
		{
			return decorated.power() - 1;
		}

		@Override
		public int toughness()
		{
			return decorated.toughness() - 1;
		}

		@Override
		public boolean canBeTargeted()
		{
			return true;
		}
	}
}
