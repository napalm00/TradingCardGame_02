package interfaces;

public interface Effect extends GameEntity
{
    boolean play();

    void resolve();

    void remove();
}
