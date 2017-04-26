package interfaces;

public interface CreatureDecorator extends Creature
{
    CreatureDecorator decorate(Creature creature);

    Creature removeDecorator(CreatureDecorator creatureDecorator);
}
