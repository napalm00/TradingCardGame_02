package cards;

import core.AbstractCard;
import core.AbstractCreatureDecorator;
import core.AbstractCreature;
import core.AbstractCreatureEffect;
import core.AbstractDamageManagerDecorator;
import core.AbstractEffect;
import core.DecoratedCreature;
import core.Game;
import core.Player;
import core.StaticInitializer;
import core.Triggers;
import interfaces.CardConstructor;
import interfaces.Card;
import interfaces.Creature;
import interfaces.Damageable;
import interfaces.Effect;
import interfaces.Enchantment;
import interfaces.GameEntityVisitor;
import interfaces.TargetingEffect;
import interfaces.TriggerAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BenevolentAncestor extends AbstractCard
{
	static private final String cardName = "Benevolent Ancestor";

	static private StaticInitializer initializer
			= new StaticInitializer(cardName, new CardConstructor()
			{
				@Override
				public Card create()
				{
					return new BenevolentAncestor();
				}
			});

	@Override
	public Effect getEffect(Player p)
	{
		return new BenevolentAncestorEffect(p, this);
	}

	@Override
	public String name()
	{
		return cardName;
	}

	@Override
	public String type()
	{
		return "Creature";
	}

	@Override
	public String ruleText()
	{
		return "Put in play a creature " + cardName + "(0/4) with tap: prevent the next 1 damage that would be delt to target creature or player this turn";
	}

	@Override
	public String toString()
	{
		return name() + "[" + ruleText() + "]";
	}

	@Override
	public boolean isInstant()
	{
		return false;
	}

	private class BenevolentAncestorEffect extends AbstractCreatureEffect
	{
		public BenevolentAncestorEffect(Player p, Card c)
		{
			super(p, c);
		}

		@Override
		protected Creature createCreature()
		{
			return new BenevolentAncestorCreature(owner);
		}

	}

	private class BenevolentAncestorCreature extends AbstractCreature
	{

		BenevolentAncestorCreature(Player owner)
		{
			super(owner);
		}

		@Override
		public String name()
		{
			return cardName;
		}

		public boolean CanAttack()
		{
			return false;
		}

		@Override
		public int power()
		{
			return 0;
		}

		@Override
		public int toughness()
		{
			return 4;
		}
		
		@Override
		public boolean canBeTargeted()
		{
			return true;
		}

		@Override
		public List<Effect> effects()
		{
			ArrayList<Effect> effects = new ArrayList<>();
			effects.add(new ProtectionEffect(owner));
			return effects;
		}

		@Override
		public List<Effect> avaliableEffects()
		{
			ArrayList<Effect> effects = new ArrayList<>();
			if(!topDecorator.isTapped())
			{
				effects.add(new ProtectionEffect(owner));
			}
			return effects;
		}

		private class ProtectionEffect extends AbstractEffect implements TargetingEffect
		{
			private Player owner;
			private Damageable target;

			public ProtectionEffect(Player p)
			{
				owner = p;
			}

			@Override
			public String name()
			{
				return "prevent 1 damage";
			}

			@Override
			public String toString()
			{
				if(target == null)
				{
					return "prevent the next 1 damage to target creature or player this turn";
				}
				else if(target.isRemoved())
				{
					return "prevent the next 1 damage to (removed target)";
				}
				else
				{
					return "prevent the next 1 damage to " + target.name();
				}
			}

			@Override
			public boolean play()
			{
				if(isTapped)
				{
					return false;
				}
				topDecorator.tap();
				pickTarget();
				return super.play();
			}

			@Override
			public void pickTarget()
			{
				System.out.println(owner.name() + ": choose target for " + name());

				ArrayList<Damageable> targets = new ArrayList<>();
				int i = 1;

				Player curPlayer = Game.instance.getPlayer(0);
				System.out.println(i + ") " + curPlayer.name());
				targets.add(curPlayer);
				++i;
				for(DecoratedCreature c : curPlayer.getCreatures())
				{
					if(c.canBeTargeted())
					{
						System.out.println(i + ") " + curPlayer.name() + ": " + c.name());
						targets.add(c);
						++i;
					}
				}

				curPlayer = Game.instance.getPlayer(1);
				System.out.println(i + ") " + curPlayer.name());
				targets.add(curPlayer);
				++i;
				for(DecoratedCreature c : curPlayer.getCreatures())
				{
					if(c.canBeTargeted())
					{
						System.out.println(i + ") " + curPlayer.name() + ": " + c.name());
						targets.add(c);
						++i;
					}
				}

				Scanner reader = Game.instance.getScanner();
				if(reader.hasNextInt())
				{
					int idx = reader.nextInt() - 1;
					if(idx < 0 || idx >= targets.size())
					{
						target = null;
					}
					else
					{
						target = targets.get(idx);
					}
				}
			}

			@Override
			public void resolve()
			{
				if(target == null)
				{
					System.out.println(cardName + ": damage prevention effect has no target");
					return;
				}

				target.accept(new GameEntityVisitor()
				{

					@Override
					public void visit(Player p)
					{
						final BenevolentAncestorDamageStrategy st = new BenevolentAncestorDamageStrategy();
						final Player ptarget = p;

						ptarget.addDamageStrategy(st);
						Game.instance.getTriggers().register(Triggers.END_FILTER,
								new TriggerAction()
								{
							@Override
									public void execute(Object args)
									{
										System.out.println("Removing " + cardName + ": damage prevention effect");
										ptarget.removeDamageStrategy(st);
										Game.instance.getTriggers().remove(this);
									}
								}
						);

					}

					@Override
					public void visit(Card c)
					{
					} //should not happen

					@Override
					public void visit(Effect e)
					{
					}//should not happen

					@Override
					public void visit(DecoratedCreature c)
					{
						final DecoratedCreature ctarget = c;
						if(ctarget.isRemoved())
						{
							System.out.println(cardName + ": damage prevention effect targets a removed creature");
							return;
						}
						final BenevolentAncestorCreatureDecorator cr = new BenevolentAncestorCreatureDecorator();

						ctarget.addDecorator(cr);
						Game.instance.getTriggers().register(Triggers.END_FILTER,
								new TriggerAction()
								{
							@Override
									public void execute(Object args)
									{
										System.out.println("Removing " + cardName + ": damage prevention effect");
										ctarget.removeDecorator(cr);
										Game.instance.getTriggers().remove(this);
									}
								}
						);
					}
					
					@Override
					public void visit(Enchantment e)
					{
					}//should not happen

				}); //end accept

			}
		}

	}

	private class BenevolentAncestorDamageStrategy extends AbstractDamageManagerDecorator
	{
		int prevention = 1;

		@Override
		public void inflictDamage(int dmg)
		{
			System.out.println(cardName + " preventing 1 damage to player");
			if(dmg <= prevention)
			{
				prevention -= dmg;
			}
			else
			{
				decorated.inflictDamage(dmg - prevention);
				prevention = 0;
			}
		}
	}

	private class BenevolentAncestorCreatureDecorator extends AbstractCreatureDecorator
	{
		int prevention = 1;

		@Override
		public void inflictDamage(int dmg)
		{
			System.out.println(cardName + " preventing 1 damage to player");
			if(dmg <= prevention)
			{
				prevention -= dmg;
			}
			else
			{
				decorated.inflictDamage(dmg - prevention);
				prevention = 0;
			}
		}
	}

}
