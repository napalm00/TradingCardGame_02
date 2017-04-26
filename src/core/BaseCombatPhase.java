package core;

import interfaces.Phase;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/***
 * Combat phase class, not yet implemented
 */
public class BaseCombatPhase implements Phase
{
    /***
     * Not yet implemented
     */
    public void execute()
    {
		final Player current_player = Game.instance.getCurrentPlayer();
        final Player adversary = Game.instance.getCurrentAdversary();
        final int current_player_idx = (Game.instance.getPlayer(0) == current_player) ? 0 : 1;
        ArrayList<CombatEntry> combat = new ArrayList<>();
        int active_player;
        int number_passes;

        System.out.println(current_player.getName() + ": combat phase");

        Game.instance.getTriggers().trigger(Triggers.COMBAT_FILTER);


        // declare attackers
        if (!current_player.getCreatures().isEmpty()) {
            System.out.println(current_player.getName() + " choose attackers");
            ArrayList<DecoratedCreature> potential_attackers = new ArrayList<>();
            int i = 1;
            for (DecoratedCreature c : current_player.getCreatures()) {
                if (c.canAttack()) {
                    System.out.println(i + ") " + c);
                    potential_attackers.add(c);
                    ++i;
                }
            }

            Scanner creature_list = Game.instance.getScanner();
            while (creature_list.hasNextInt()) {
                int idx = creature_list.nextInt() - 1;
                if (idx >= 0 && idx < potential_attackers.size()) {
                    CombatEntry entry = new CombatEntry();
                    entry.attacker = potential_attackers.get(idx);
                    entry.attacker.attack();
                    combat.add(entry);
                }
            }
        }


        System.out.println("Attacker declaration response subphase");
        //allow other player to respond first
        active_player = (current_player_idx + 1) % 2;
        number_passes = 0;
        while (number_passes < 2) {
            if (Utilities.playAllAvailableEffects(Game.instance.getPlayer(active_player), false))
                number_passes = 0;
            else ++number_passes;

            active_player = (active_player + 1) % 2;
        }
		
		Game.instance.getCardStack().resolve();

        HashSet<DecoratedCreature> defenders = new HashSet<>();

        // declare defenders
        if (!adversary.getCreatures().isEmpty()) {
            for (CombatEntry e : combat) {
                if (e.attacker.isRemoved()) continue;
                System.out.println(adversary.getName() + " choose creatures defending from " + e.attacker);
                ArrayList<DecoratedCreature> potential_defenders = new ArrayList<>();
                int i = 1;
                for (DecoratedCreature c : adversary.getCreatures()) {
                    if (c.canDefend() && !defenders.contains(c)) {
                        System.out.println(i + ") " + c);
                        potential_defenders.add(c);
                        ++i;
                    }
                }
                Scanner creature_list = Game.instance.getScanner();
                while (creature_list.hasNextInt()) {
                    int idx = creature_list.nextInt() - 1;
                    if (idx >= 0 && idx < potential_defenders.size()) {
                        DecoratedCreature defender = potential_defenders.get(idx);
                        if (!defenders.contains(defender)) {
                            defender.defend();
                            e.defenders.add(defender);
                            defenders.add(defender);
                        }
                    }
                }
            }
        }

        System.out.println("Defender declaration response subphase");
        //allow priority player to respond first
        active_player = current_player_idx;
        number_passes = 0;
        while (number_passes < 2) {
            if (Utilities.playAllAvailableEffects(Game.instance.getPlayer(active_player), false))
                number_passes = 0;
            else ++number_passes;

            active_player = (active_player + 1) % 2;
        }
		
        Game.instance.getCardStack().resolve();


        //distribute damage
        System.out.println("Damage distribution subphase");
        for (CombatEntry e : combat) {
            if (e.attacker.isRemoved()) continue;

            boolean is_blocked = false;
            int defender_damage = 0;
            int attacker_residual_power = e.attacker.power();

            for (DecoratedCreature defender : e.defenders) {
                if (defender.isRemoved()) continue;
                is_blocked = true;

                //compute damage attacker inflict to this defender
                int attacker_damage = Math.min(attacker_residual_power,
                        defender.toughness() - defender.getDamage());
                //accumulate defender damage
                defender_damage += Math.max(0, defender.power());

                System.out.println(e.attacker + " deals " + attacker_damage + " damage to " + defender);
                System.out.println(defender + " deals " + attacker_damage + " damage to " + e.attacker);

                //inflict damage to defender
                if (attacker_damage > 0) {
                    defender.inflictDamage(attacker_damage);
                    attacker_residual_power -= attacker_damage;
                }
            }


            if (!is_blocked) {
                //inflict damage to adversary player
                System.out.println(e.attacker + " deals " + e.attacker.power() + " damage to " + adversary.getName());
                adversary.inflictDamage(Math.max(0, e.attacker.power()));
            } else {
                //inflict cumulative damage to attacker
                defender_damage = Math.min(defender_damage,
                        e.attacker.toughness() - e.attacker.getDamage());
                e.attacker.inflictDamage(defender_damage);
            }

        }

    }

    private class CombatEntry {
        public DecoratedCreature attacker;
        public ArrayList<DecoratedCreature> defenders = new ArrayList<>();
    }
}
