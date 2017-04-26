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
import java.util.ArrayList;
import java.util.Scanner;

public class Deflection extends AbstractCard
{
	static private final String cardName = "Deflection";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new Deflection();
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
		return "Instant";
	}

	@Override
	public String ruleText()
	{
		return "Change the target of target spell with a single target";
	}

	@Override
	public String toString()
	{
		return name() + " [" + ruleText() + "]";
	}

	@Override
	public boolean isInstant()
	{
		return true;
	}

	@Override
	public Effect getEffect(Player owner)
	{
		return new DeflectionEffect(owner, this);
	}

	private class DeflectionEffect extends AbstractCardEffect implements TargetingEffect
	{
		TargetingEffect target;

		public DeflectionEffect(Player p, Card c)
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
		public String toString()
		{
			if(target == null)
			{
				return super.toString() + " (no target)";
			}
			else
			{
				return name() + " [Change the target of " + target.name() + " with a single target]";
			}
		}

		@Override
		public void pickTarget()
		{
			System.out.println(owner.name() + ": choose target for " + name());
			CardStack stack = Game.instance.getCardStack();
			ArrayList<TargetingEffect> effects = new ArrayList<>();
			int i = 1;
			for(Effect e : stack)
			{
				if(e instanceof TargetingEffect)
				{
					TargetingEffect te = (TargetingEffect) e;
					effects.add(te);
					System.out.println(i + ") " + te.name());
					++i;
				}
			}

			Scanner reader = Game.instance.getScanner();
			if(reader.hasNextInt())
			{
				int idx = reader.nextInt() - 1;
				if(idx < 0 || idx >= effects.size())
				{
					target = null;
				}
				else
				{
					target = effects.get(idx);
				}
			}
		}

		@Override
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
				target.pickTarget();
			}
		}
	}

}
