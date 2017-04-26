package core;

import interfaces.Effect;

/***
 * Represents a stack of enchantments
 */
public class EnchantmentStack extends BaseStack
{
    /***
     * Resolves the stack, playing the effects but NOT removing them
     */
    public void resolve()
    {
        while(!isEmpty())
        {
            Effect effect = pop();

            System.out.println("Enchantment stack: Resolving " + effect);

            effect.resolve();
        }
    }
}
