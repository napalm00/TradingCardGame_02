package cards;

import core.AbstractCard;
import core.AbstractCardEffect;
import core.DecoratedCreature;
import core.Game;
import core.Player;
import core.StaticInitializer;
import interfaces.CardConstructor;
import interfaces.Card;
import interfaces.Effect;

import java.util.ArrayList;

public class BoilingEarth extends AbstractCard
{
	static private final String cardName = "Boiling Earth";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new BoilingEarth();
				}
			});

	@Override
	public Effect getEffect(Player owner)
	{
		return new BoilingEarthEffect(owner, this);
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
		return cardName + " deals 1 damage to each creature";
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

	private class BoilingEarthEffect extends AbstractCardEffect
	{
		public BoilingEarthEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		public void resolve()
		{

			ArrayList<DecoratedCreature> creatures = new ArrayList<>();
			creatures.addAll(Game.instance.getPlayer(0).getCreatures());
			creatures.addAll(Game.instance.getPlayer(1).getCreatures());
			for(DecoratedCreature c : creatures)
			{
				if(!c.isRemoved())
				{
					c.inflictDamage(1);
				}
			}
		}
	}

}
