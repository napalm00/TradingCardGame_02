package interfaces;

public interface DamageManagerDecorator extends DamageManager
{
    DamageManagerDecorator decorate(DamageManager damageManager);

    DamageManager removeDecorator(DamageManagerDecorator damageManagerDecorator);
}
