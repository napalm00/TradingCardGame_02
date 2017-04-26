package core;

import interfaces.Creature;
import interfaces.DamageManager;

/***
 * Represents the base damage manager
 */
public class BaseDamageManager implements DamageManager
{
    Player player;

    public BaseDamageManager(Player player)
    {
        this.player = player;
    }

    /***
     * Inflict damage to the player, making the game end if the player dies
     * 
     * @param damage The amount of damage to inflict
     */
    public void inflictDamage(int damage)
    {
        player.changeLife(-damage);
        if(player.getLife() <= 0)
        {
            player.lose("No more life points");
        }
    }

    /***
     * Heal the player by the specified amount
     * 
     * @param points The amount of health points to restore
     */
    public void heal(int points)
    {
        player.changeLife(points);
    }

    /***
     * Lose the game
     * 
     * @param string Loss reason
     */
    public void lose(String string)
    {
        throw new EndOfGame(player.name() + " lost the game, reason: " + string);
    }

    /***
     * Inflict damage to a creature
     * 
     * @param creature The creature to damage
     * @param damage The amount of damage to inflict
     */
    public void inflictDamage(Creature creature, int damage)
    {
        creature.inflictDamage(damage);
    }
}
