/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import core.AbstractCard;
import core.AbstractEnchantment;
import core.AbstractEnchantmentCardEffect;
import core.DecoratedCreature;
import core.Game;
import core.Player;
import core.StaticInitializer;
import interfaces.Card;
import interfaces.CardConstructor;
import interfaces.Effect;
import interfaces.Enchantment;
import java.util.ArrayList;
import java.util.Scanner;

public class Abduction extends AbstractCard
{
	static private final String cardName = "Abduction";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new Abduction();
				}
			});

	private class FriendlyEnvironmentEffect extends AbstractEnchantmentCardEffect
	{
		public FriendlyEnvironmentEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		protected Enchantment createEnchantment()
		{
			return new FriendlyEnvironmentEnchantment(owner);
		}
	}

	@Override
	public Effect getEffect(Player p)
	{
		return new FriendlyEnvironmentEffect(p, this);
	}

	private class FriendlyEnvironmentEnchantment extends AbstractEnchantment
	{
            private Player owner;
            private DecoratedCreature target;
            
            public FriendlyEnvironmentEnchantment(Player owner)
            {
		super(owner);
            }
    
            @Override
            public String toString()
            {
                if(target == null)
                    return super.toString() + " (no target)";
                else
                    return name()+ " [" + target.name()+ " untap]";
            }
  
            public void pickTarget()
            {
                System.out.println(owner.name()+ ": choose target for " + name());
                ArrayList<DecoratedCreature> creatures = new ArrayList<>();
                int i = 1;

                Player player1 = Game.instance.getPlayer(0);
                Player player2 = Game.instance.getPlayer(1);

                for(DecoratedCreature decoratedCreature : player1.getCreatures())
                {
                    System.out.println(i + ") " + player1.name()+ ": " + decoratedCreature);
                    i++;
                    creatures.add(decoratedCreature);
                }
                for(DecoratedCreature decoratedCreature : player2.getCreatures())
                {
                    System.out.println(i + ") " + player2.name()+ ": " + decoratedCreature);
                    i++;
                    creatures.add(decoratedCreature);
                }

                Scanner reader = Game.instance.getScanner();
                System.out.println("Enter a Target");
                int idx = Integer.parseInt(reader.nextLine()) - 1;

                if(idx < 0 || idx >= creatures.size())
                    target = null;
                else
                    target = creatures.get(idx);
            }

            public void resolve()
            {
                if(target != null)
                    target.untap();
            }
	
            @Override
            public String name()
            {
                return cardName;
            }
	}

	@Override
	public String name()
	{
		return cardName;
	}

	@Override
	public String type()
	{
		return "Enchantment";
	}

	@Override
	public String ruleText()
	{
		return "When Abduction comes into play, untap enchanted creature. You control enchated creature.";
	}

	@Override
	public String toString()
	{
		return name() + " (" + type() + ") [" + ruleText() + "]";
	}

	@Override
	public boolean isInstant()
	{
		return false;
	}
}