/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import core.AbstractCard;
import core.AbstractCardEffect;
import core.Game;
import core.Player;
import core.StaticInitializer;
import interfaces.Card;
import interfaces.CardConstructor;
import interfaces.Effect;
import interfaces.Enchantment;
import interfaces.TargetingEffect;
import java.util.ArrayList;
import java.util.Scanner;

public class AuraBlast extends AbstractCard{

    static private final String cardName = "Aura Blast";

    static private StaticInitializer initializer = new StaticInitializer(cardName, new CardConstructor()
        {
            @Override
            public Card create()
            {
                return new AuraBlast();
            }
        }
    );

    public String getName()
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
        return "Destroy target enchantment and draw a card.";
    }

    @Override
    public String toString()
    {
        return getName() + " [" + ruleText() + "]";
    }

    @Override
    public boolean isInstant()
    {
        return true;
    }

    @Override
    public Effect getEffect(Player owner)
    {
        return new AuraBlast.AuraBlastEffect(owner, this);
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class AuraBlastEffect extends AbstractCardEffect implements TargetingEffect
    {
        Effect target;
  
        public AuraBlastEffect(Player player, Card card)
        {
            super(player, card);
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
                return super.toString() + " (no target)";
            else
                return getName() + " [Destroy the target : " + target.name() + ", draw a card]";
        }

        @Override
        public void pickTarget()
        {
            System.out.println(owner.name()+ ": choose target for " + getName());
            ArrayList<Enchantment> stack=new ArrayList<>();
            stack.addAll(Game.instance.getPlayer(0).getEnchantments());
            stack.addAll(Game.instance.getPlayer(1).getEnchantments());
            
            int i = 1;

            for(Enchantment enchantmentTarget: stack)
            {
                System.out.println(i + ")  " + enchantmentTarget);
                i++;
            }
           
            Scanner reader = Game.instance.getScanner();
            System.out.println("Enter a Target");
            int id = Integer.parseInt(reader.nextLine()) - 1;
            
            if(id < 0 || id >= stack.size())
                target = null;
            else
                target = (Effect) stack.get(id);
        }

        @Override
        public void resolve()
        {
            if(target == null)
                System.out.println(cardName + " has no target");
            else if(Game.instance.getCardStack().indexOf(target) == -1)
                System.out.println(cardName + " target is not on the stack anymore");
            else
            {
                System.out.println(cardName + " removing " + target.name() + " from stack");
                Game.instance.getPlayer(0).getEnchantments().remove(target);
                Game.instance.getPlayer(1).getEnchantments().remove(target);
            }
            Game.instance.getCurrentPlayer().draw();
        }
    }
    
}