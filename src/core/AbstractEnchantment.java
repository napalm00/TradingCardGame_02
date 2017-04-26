package core;

import interfaces.Enchantment;

/***
 * Base abstract class that represents an effect
 */
public abstract class AbstractEnchantment implements Enchantment
{
	protected Player owner;        
    protected AbstractEnchantment(Player owner) { this.owner=owner; }
	
	@Override
        public void insert() {
            Game.instance.getTriggers().trigger(Triggers.ENTER_ENCHANTMENT_FILTER,this);
        }
    
    @Override
        public void remove() {
            owner.getEnchantments().remove(this);
            Game.instance.getTriggers().trigger(Triggers.EXIT_ENCHANTMENT_FILTER,this);
        }
        
    @Override
        public String toString() {
            return getName() + " (Enchantment)";
        }
	
}
