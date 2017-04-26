package core;

import java.util.ArrayList;
import java.util.Iterator;
import interfaces.Effect;

/***
 * Represents a basic stack structure, implemented as an ArrayList
 */
public class CardStack implements Iterable<Effect>
{
    private final ArrayList<Effect> stack = new ArrayList<>();

    /***
     * Get the iterator for this stack
     * 
     * @return		Iterator<Effect>		The stack's iterator
     */
    public Iterator<Effect> iterator()
    {
        return stack.iterator();
    }

    /***
     * Push an element into the stack
     * 
     * @param effect element to push
     */
    public void push(Effect effect)
    {
        stack.add(effect);
    }

    /***
     * Pop an element from the stack
     * 
     * @return		Effect		Popped element
     */
    public Effect pop()
    {
        Effect effect = stack.remove(stack.size() - 1);
        return effect;
    }

    /***
     * Remove a specific element from the stack
     * 
     * @param effect Effect to remove
     */
    public void remove(Effect effect)
    {
        stack.remove(effect);
        effect.remove();
    }

    /***
     * Get the stack's size
     * 
     * @return		int		Size of the stack
     */
    public int size()
    {
        return stack.size();
    }

    /***
     * Get an element in the stack at the given position
     * 
     * @param i The position to get the element at
     * 
     * @return		Effect		The returned element, or null if not found
     */
    public Effect get(int i)
    {
        return stack.get(i);
    }

    /***
     * Check whether the stack is empty
     * 
     * @return		boolean		True if empty, false otherwise
     */
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    /***
     * Get the index of a specific element in the stack
     * 
     * @param effect Element to check the index of
     * 
     * @return		int		Index of the effect, or -1 if not found
     */
    public int indexOf(Effect effect)
    {
        return stack.indexOf(effect);
    }
	
	/***
     * Resolves the card stack, playing the effects and removing the cards
     */
    public void resolve()
    {
        while(!isEmpty())
        {
            Effect effect = pop();

            System.out.println("Stack: Resolving " + effect);

            effect.remove();
            effect.resolve();
        }
    }
}
