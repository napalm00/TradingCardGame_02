package cards;

import core.AbstractCard;
import core.AbstractCardEffect;
import core.CardStack;
import core.Game;
import core.Player;
import core.StaticInitializer;
import interfaces.CardConstructor;
import interfaces.Card;
import interfaces.Effect;
import interfaces.TargetingEffect;
import java.util.Scanner;

public class Cancel extends AbstractCard
{
	static private final String cardName = "Cancel";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				public Card create()
				{
					return new Cancel();
				}
			});

	public String name()
	{
		return cardName;
	}

	public String type()
	{
		return "Instant";
	}

	public String ruleText()
	{
		return "Counter target spell";
	}

	public String toString()
	{
		return name() + " [" + ruleText() + "]";
	}

	public boolean isInstant()
	{
		return true;
	}

	public Effect getEffect(Player owner)
	{
		return new CancelEffect(owner, this);
	}

	private class CancelEffect extends AbstractCardEffect implements TargetingEffect
	{
		Effect target;

		public CancelEffect(Player p, Card c)
		{
			super(p, c);
		}

		public boolean play()
		{
			pickTarget();
			return super.play();
		}

		public String toString()
		{
			if(target == null)
			{
				return super.toString() + " (no target)";
			}
			else
			{
				return name() + " [Counter " + target.name() + "]";
			}
		}

		public void pickTarget()
		{
			System.out.println(owner.name() + ": choose target for " + name());
			CardStack stack = Game.instance.getCardStack();
			int i = 1;
			for(Effect e : stack)
			{
				System.out.println(i + ") " + e.name());
				++i;
			}

			Scanner reader = Game.instance.getScanner();
			if(reader.hasNextInt())
			{
				int idx = reader.nextInt() - 1;
				if(idx < 0 || idx >= stack.size())
				{
					target = null;
				}
				else
				{
					target = stack.get(idx);
				}
			}
		}

		public void resolve()
		{
			if(target == null)
			{
				System.out.println(cardName + " has no target");
			}
			else if(Game.instance.getCardStack().indexOf(target) == -1)
			{
				System.out.println(cardName + " target is not on the stack anymore");
			}
			else
			{
				System.out.println(cardName + " removing " + target.name() + " from stack");
				Game.instance.getCardStack().remove(target);
			}
		}
	}

}
