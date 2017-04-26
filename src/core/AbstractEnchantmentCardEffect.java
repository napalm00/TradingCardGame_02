package core;

import interfaces.Enchantment;
import interfaces.Card;

public abstract class AbstractEnchantmentCardEffect extends AbstractCardEffect {
    protected AbstractEnchantmentCardEffect( Player p, Card c) { super(p,c); }
    
    // deferred method that creates the enchantment upon resolution
    protected abstract Enchantment  createEnchantment();
	
	@Override
	public void remove(){} // Enchantments are never removed
	
    @Override
    public void resolve() {
        Enchantment e=createEnchantment();
        owner.getEnchantments().add(e);
        e.insert();
    }
}
