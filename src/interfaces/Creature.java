package interfaces;

import core.DecoratedCreature;

import java.util.List;

public interface Creature extends Named
{
    int power();

    int toughness();

    void tap();

    void untap();

    boolean isTapped();

    boolean canAttack();

    void attack();

    boolean canDefend();

    void defend();

    void inflictDamage(int damage);

    int getDamage();

    void resetDamage();

    void destroy();

    void onRemove();

	boolean canBeTargeted();
	
    List<Effect> effects();

    List<Effect> avaliableEffects();

    DecoratedCreature getTopDecorator();

    void setTopDecorator(DecoratedCreature decoratedCreature);
}
