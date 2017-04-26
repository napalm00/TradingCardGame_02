package interfaces;

public interface GameEntity extends Named
{
    boolean isRemoved();

    void accept(GameEntityVisitor gameEntityVisitor);
}
