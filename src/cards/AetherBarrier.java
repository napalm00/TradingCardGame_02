/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cards;

import core.AbstractCard;
import core.AbstractCardEffect;
import core.AbstractEnchantment;
import core.Player;
import core.StaticInitializer;
import interfaces.Card;
import interfaces.CardConstructor;
import interfaces.Effect;
import interfaces.GameEntityVisitor;
import interfaces.TargetingEffect;

public class AetherBarrier extends AbstractCard 
{
    static private final String cardName = "Aether Barrier";
      
    static private StaticInitializer initializer = new StaticInitializer(cardName, new CardConstructor()
        {
            @Override
            public Card create()
            {
                return new AetherBarrier();
            }
        }
    );

    @Override
    public Effect getEffect(Player owner)
    {
        return new AetherBarrierEffect(owner);
    }

    public String getName()
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
        return "Whenever a player plays a creature spell,that player sacrifices a permanent";
    }

    @Override
    public String toString()
    {
        return getName() + "[" + ruleText() + "]";
    }

    @Override
    public boolean isInstant()
    {
        return false;
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class AetherBarrierEffect extends AbstractEnchantment implements TargetingEffect
    {
        private Player owner;
        
        public AetherBarrierEffect(Player owner)
        {
            super(owner);
        }

        
        @Override
        public boolean play()
        {
            pickTarget();
            return super.play();
        }
        
        @Override
        public void pickTarget(){
            
        }
        
        public String getName() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }  

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void resolve() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isRemoved() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void accept(GameEntityVisitor gameEntityVisitor) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}