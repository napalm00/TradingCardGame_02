package cards;

import core.AbstractCard;
import core.AbstractCardEffect;
import core.AbstractDamageManagerDecorator;
import core.Game;
import core.Player;
import core.StaticInitializer;
import core.Triggers;
import interfaces.CardConstructor;
import interfaces.Card;
import interfaces.Creature;
import interfaces.Effect;
import interfaces.TriggerAction;

public class Darkness extends AbstractCard
{
	static private final String cardName = "Darkness";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				public Card create()
				{
					return new Darkness();
				}
			});

	public Effect getEffect(Player owner)
	{
		return new DarknessEffect(owner, this);
	}

	public String name()
	{
		return cardName;
	}

	public String type()
	{
		return "Instant";
	}

    //should have been "prevent all combat damage", but dealing with this would require
	//the game to keep track of different types of damages just for this card
	public String ruleText()
	{
		return "Prevents all damage that would be dealt this turn";
	}

	public String toString()
	{
		return name() + "[" + ruleText() + "]";
	}

	public boolean isInstant()
	{
		return true;
	}

	private class DarknessEffect extends AbstractCardEffect
	{
		public DarknessEffect(Player p, Card c)
		{
			super(p, c);
		}

		public void resolve()
		{
			final DarknessDamageStrategy p0 = new DarknessDamageStrategy();
			final DarknessDamageStrategy p1 = new DarknessDamageStrategy();

			Game.instance.getPlayer(0).addDamageStrategy(p0);
			Game.instance.getPlayer(1).addDamageStrategy(p1);

			Game.instance.getTriggers().register(Triggers.END_FILTER,
					new TriggerAction()
					{
						public void execute(Object args)
						{
							System.out.println("Remove " + cardName);
							Game.instance.getPlayer(0).removeDamageStrategy(p0);
							Game.instance.getPlayer(1).removeDamageStrategy(p1);
							Game.instance.getTriggers().remove(this);
						}
					}
			);
		}
	}

	private class DarknessDamageStrategy extends AbstractDamageManagerDecorator
	{
		public void inflictDamage(int dmg)
		{
			System.out.println(cardName + " preventing damage to player");
		}

		public void inflictDamage(Creature c, int dmg)
		{
			System.out.println(cardName + " preventing damage to " + c.name());
		}
	}

}
