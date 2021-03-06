package cards;

import core.AbstractCard;
import core.AbstractCardEffect;
import core.Game;
import core.Phases;
import core.Player;
import core.StaticInitializer;
import interfaces.CardConstructor;
import interfaces.Card;
import interfaces.Creature;
import interfaces.Effect;
import interfaces.PhaseManager;

public class WorldAtWar extends AbstractCard
{
	static private final String cardName = "World at War";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new WorldAtWar();
				}
			});

	@Override
	public Effect getEffect(Player owner)
	{
		return new WorldAtWarEffect(owner, this);
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
		return "After the main phase this turn, there's an additional combat pahse followed by an additional main phase. At the baginning of that combat, untap all creatures";
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

	private class WorldAtWarEffect extends AbstractCardEffect
	{
		public WorldAtWarEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		public void resolve()
		{
			Game.instance.getCurrentPlayer().setPhaseManager(new TurnChange());
		}
	}

	private class TurnChange implements PhaseManager
	{

		private int phaseidx = 0;

		@Override
		public Phases currentPhase()
		{
			Phases result;
			switch(phaseidx)
			{
				case 0:
					result = Phases.MAIN;
					break;
				case 1:
					result = Phases.COMBAT;
					break;
				case 2:
					result = Phases.MAIN;
					break;
				default: //should not happen!
					System.out.println(cardName + " turn beyond range. Odd! Removing it");
					Game.instance.getCurrentPlayer().removePhaseManager(this);
					result = Game.instance.getCurrentPlayer().currentPhase();
			}
			return result;
		}

		@Override
		public Phases nextPhase()
		{
			++phaseidx;
			if(phaseidx > 2)
			{
				System.out.println("Removing " + cardName + " turn changes");
				Game.instance.getCurrentPlayer().removePhaseManager(this);
				return Game.instance.getCurrentPlayer().nextPhase();
			}
			if(phaseidx == 1)
			{
                //should only untap creatures that have attacked, but I am ignoring this distinction
				//dealing with it would require the game to know abou this card before
				//it was played and keep the attacker info just for its use
				System.out.println(cardName + " untapping creatures before new combat phase");
				for(Creature c : Game.instance.getCurrentPlayer().getCreatures())
				{
					c.untap();
				}
			}
			return currentPhase();
		}

	}

}
