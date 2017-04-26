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
import interfaces.Effect;
import interfaces.TurnManager;

public class SavorTheMoment extends AbstractCard
{
	static private final String cardName = "Savor the Moment";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				public Card create()
				{
					return new SavorTheMoment();
				}
			});

	public Effect getEffect(Player owner)
	{
		return new SavorTheMomentEffect(owner, this);
	}

	public String name()
	{
		return cardName;
	}

	public String type()
	{
		return "Sorcery";
	}

	public String ruleText()
	{
		return "Take an extra turn after this one. Skip the untap step of that turn.";
	}

	public String toString()
	{
		return name() + "[" + ruleText() + "]";
	}

	public boolean isInstant()
	{
		return false;
	}

	private class SavorTheMomentEffect extends AbstractCardEffect
	{
		public SavorTheMomentEffect(Player p, Card c)
		{
			super(p, c);
		}

		public void resolve()
		{
			Game.instance.getCurrentPlayer().setPhase(Phases.UNTAP, new SkipPhase(Phases.UNTAP));
			Game.instance.setTurnManager(new ExtraTurn());
		}
	}

	private class ExtraTurn implements TurnManager
	{
		private Player current;
		private Player adversary;

		public ExtraTurn()
		{
			current = Game.instance.getCurrentPlayer();
			adversary = Game.instance.getCurrentAdversary();
		}

		public Player getCurrentPlayer()
		{
			return current;
		}

		public Player getCurrentAdversary()
		{
			return adversary;
		}

		public Player nextPlayer()
		{
			Game.instance.removeTurnManager(this);
			return current;
		}

	}

}
