package cards;

import core.AbstractCard;
import core.AbstractCardEffect;
import core.Game;
import core.Phases;
import core.Player;
import core.SkipPhase;
import core.StaticInitializer;
import interfaces.CardConstructor;
import interfaces.Card;
import interfaces.Damageable;
import interfaces.Effect;
import interfaces.TargetingEffect;
import java.util.ArrayList;
import java.util.Scanner;

public class Fatigue extends AbstractCard
{
	static private final String cardName = "Fatigue";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new Fatigue();
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
		return "Sorcery";
	}

	@Override
	public String ruleText()
	{
		return "Target player skips his next draw phase";
	}

	@Override
	public String toString()
	{
		return name() + " [" + ruleText() + "]";
	}

	@Override
	public boolean isInstant()
	{
		return false;
	}

	@Override
	public Effect getEffect(Player owner)
	{
		return new FatigueEffect(owner, this);
	}

	private class FatigueEffect extends AbstractCardEffect implements TargetingEffect
	{
		Player target;

		public FatigueEffect(Player p, Card c)
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
				return name() + " [" + target.name() + " skips his next draw phase]";
			}
		}

		@Override
		public void pickTarget()
		{
			System.out.println(owner.name() + ": choose target for " + name());
			System.out.println("1) " + Game.instance.getPlayer(0).name());
			System.out.println("2) " + Game.instance.getPlayer(1).name());

			ArrayList<Damageable> targets = new ArrayList<>();
			targets.add(Game.instance.getPlayer(0));
			targets.add(Game.instance.getPlayer(0));

			Scanner reader = Game.instance.getScanner();
			if(reader.hasNextInt())
			{
				int idx = reader.nextInt() - 1;
				if(idx < 0 || idx > 1)
				{
					target = null;
				}
				else
				{
					target = Game.instance.getPlayer(idx);
				}
			}
		}

		@Override
		public void resolve()
		{
			if(target != null)
			{
				target.setPhase(Phases.DRAW, new SkipPhase(Phases.DRAW));
			}
		}
	}

}
