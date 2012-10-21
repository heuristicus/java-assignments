package ex15;

import java.io.*;

/** The functionality required of a Board representation.
 * (not necessarily a Kalah Board).
 * Moves are numbered 1 ....
 * The players are numbered 0 and 1.
 **/
public abstract class Board implements Serializable{

    /** Tests whether the game is over if it is the given player's
     * turn to play */
    public abstract boolean gameFinished(int playerNum);

    /** Makes a move on the board.
     * @param turn The number of the player whose turn it is.
     * @param move The number of the move to be made.
     *@return The number of the player whose turn it is next.
     **/
    public abstract int makeMove(int turn, int move)
            throws IllegalMoveException;

    /** Calculates a numeric score for the board,
     * from the point of view of player 0 */
    public abstract int score();

    /**
     * Needed in case we want to try a move on a makeCopy of
     * the board or record a makeCopy of the board
     */
    public abstract Board makeCopy();

    /** Used to record boards in an output file */
    public abstract void saveTo(ObjectOutputStream out)
            throws IOException;

    /** Used to load boards from an input file */
    public abstract void loadFrom(ObjectInputStream in)
            throws IOException, ClassNotFoundException;
}
