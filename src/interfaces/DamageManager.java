package interfaces;

public interface DamageManager
{
    void inflictDamage(int damage);

    void heal(int points);

    void lose(String string);

    void inflictDamage(Creature creature, int damage);
}
