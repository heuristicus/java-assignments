package ex15;

public abstract class Player {

    public abstract int pickMove(GameState s)
            throws NoMoveAvailableException;
}
