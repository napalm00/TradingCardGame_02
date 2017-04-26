package core;

import interfaces.GameEntity;

/***
 * Base abstract class that represents a game entity
 */
public abstract class AbstractGameEntity implements GameEntity
{
    protected boolean isRemoved = false;

    /***
     * Check whether the entity is removed
     * 
     * @return		boolean		True if the entity is removed, false otherwise
     */
    public boolean isRemoved()
    {
        return isRemoved;
    }

    /***
     * Remove the entity
     */
    public void remove()
    {
        isRemoved = true;
    }
}
