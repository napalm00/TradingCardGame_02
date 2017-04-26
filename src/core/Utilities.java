package core;

import interfaces.Card;
import interfaces.Creature;
import interfaces.Effect;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Utilities
{
    public static boolean playAllAvailableEffects(Player activePlayer, boolean isMain)
    {
        // If there are no more cards, pass automatically
        if(activePlayer.getHand().isEmpty() && activePlayer.getCreatures().isEmpty())
        {
            System.out.println(activePlayer.getName() + " has no more cards, pass");
            return false;
        }
		
        ArrayList<Effect> avaibleEffects = new ArrayList<>();
        Scanner reader = Game.instance.getScanner();

        // Print a menu so the user can choose which cards/effects to play
        System.out.println(activePlayer.getName() + " select card/effect to play, 0 to pass");
        int i = 0;
        for(Card card : activePlayer.getHand())
        {
            if(isMain || card.isInstant())
            {
                avaibleEffects.add(card.getEffect(activePlayer));
                System.out.println(Integer.toString(i + 1) + ") " + card);
                i++;
            }
        }

        for(Creature creature : activePlayer.getCreatures())
        {
            for(Effect effect : creature.avaliableEffects())
            {
                avaibleEffects.add(effect);
                System.out.println(Integer.toString(i + 1) + ") " + creature.getName() + ": [" + effect + "]");
                i++;
            }
        }
		
        int index = Integer.parseInt(reader.nextLine()) - 1;
        if(index < 0 || index >= avaibleEffects.size())
        {
            System.out.println("Card index invalid, skipping");
            return false;
        }
		
        avaibleEffects.get(index).play();
        
        return true;
    }
}
