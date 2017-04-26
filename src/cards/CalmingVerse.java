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

public class CalmingVerse extends AbstractCard
{
	static private final String cardName = "Calming Verse";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new CalmingVerse();
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
		return "Destroy all enchantments you don't control";
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
		return new CalmingVerseEffect(owner, this);
	}

	private class CalmingVerseEffect extends AbstractCardEffect
	{
		Effect target;

		public CalmingVerseEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		public boolean play()
		{
			return super.play();
		}

		@Override
		public String toString()
		{
			return super.toString();
		}

		@Override
		public void resolve()
		{
			Player enemyPlayer = Game.instance.getPlayer(0);
			if(enemyPlayer.equals(owner))
			{
				enemyPlayer = Game.instance.getPlayer(1);
			}
			
			enemyPlayer.getEnchantments().clear();
			System.out.println(cardName + " removed all enchantments of " + enemyPlayer.name());
		}
	}

}
