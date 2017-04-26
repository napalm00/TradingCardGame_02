package core;

import interfaces.TriggerAction;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * *
 * Trigger system for event calling
 */
public class Triggers
{
	public static final int DRAW_FILTER = 1;
	public static final int UNTAP_FILTER = 2;
	public static final int COMBAT_FILTER = 4;
	public static final int MAIN_FILTER = 8;
	public static final int END_FILTER = 16;
	public static final int ENTER_CREATURE_FILTER = 32;
	public static final int EXIT_CREATURE_FILTER = 64;
	public static final int ENTER_ENCHANTMENT_FILTER = 128;
	public static final int EXIT_ENCHANTMENT_FILTER = 256;

	ArrayList<Triggers.Entry> actions = new ArrayList<>();

	/**
	 * *
	 * Register a listener for a specific trigger filter
	 *
	 * @param triggerFilter The filter to listen to
	 * @param action The action to call when the event is triggered
	 */
	public void register(int triggerFilter, TriggerAction action)
	{
		actions.add(new Triggers.Entry(triggerFilter, action));
	}

	/**
	 * *
	 * Remove a listener
	 *
	 * @param action The listener action callback to remove
	 */
	public void remove(TriggerAction action)
	{
		Triggers.Entry result = null;
		for(Triggers.Entry entry : actions)
		{
			if(entry.action == action)
			{
				result = entry;
			}
		}

		if(result != null)
		{
			result.valid = false;
			actions.remove(result);
		}
	}

	/**
	 * *
	 * Trigger an event
	 *
	 * @param event
	 */
	public void trigger(int event)
	{
		trigger(event, null);
	}

	public void trigger(int event, Object args)
	{
		ArrayDeque<Triggers.Entry> triggerableActions = new ArrayDeque<>();

		for(Triggers.Entry p : actions)
		{
			if((p.filter & event) != 0)
			{
				triggerableActions.push(p);
			}
		}

		//execute last-inserted-first
		while(!triggerableActions.isEmpty())
		{
			Triggers.Entry entry = triggerableActions.pop();
			entry.action.execute(args);
		}
	}

	/**
	 * *
	 * Trigger entry, used internally to represent elements of the triggers
	 * array
	 */
	private class Entry
	{
		public int filter;
		public TriggerAction action;
		public boolean valid = true;

		public Entry(int filter, TriggerAction action)
		{
			this.filter = filter;
			this.action = action;
		}
	}
}
