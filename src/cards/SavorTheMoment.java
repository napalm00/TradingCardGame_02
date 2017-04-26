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
				@Override
				public Card create()
				{
					return new SavorTheMoment();
				}
			});

	@Override
	public Effect getEffect(Player owner)
	{
		return new SavorTheMomentEffect(owner, this);
	}

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
		return "Take an extra turn after this one. Skip the untap step of that turn.";
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

	private class SavorTheMomentEffect extends AbstractCardEffect
	{
		public SavorTheMomentEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
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

		@Override
		public Player getCurrentPlayer()
		{
			return current;
		}

		@Override
		public Player getCurrentAdversary()
		{
			return adversary;
		}

		@Override
		public Player nextPlayer()
		{
			Game.instance.removeTurnManager(this);
			return current;
		}

	}

}
