/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex15;

import java.io.Serializable;

/**
 *
 * @author Michal
 */
public class GameState implements Serializable{

    protected StandardKalahBoard currentBoard;
    protected int starts;

    /**
     * Creates a new GameState object.  Requires a StandardKalahBoard.  Will cast
     * the received board to StandardKalahBoard.  The program will not work with
     * a board other than that.
     * @param board A board object.  Must be convertible to StandardKalahBoard.
     * @param starts The starting player at this game state.
     */
    public GameState(Board board, int starts) {
        this.currentBoard = (StandardKalahBoard) board;
        this.starts = starts;
    }

    /**
     * More specialised constructor.  Takes a StandardKalahBoard as a parameter.
     * This removes the problem of a board not being castable to this type of
     * board.
     * @param currentBoard A standard kalah board.
     * @param starts The starting player in this state.
     */
    public GameState(StandardKalahBoard currentBoard, int starts) {
        this.currentBoard = currentBoard;
        this.starts = starts;
    }

    /**
     * Returns the board being used by this object.
     * @return StandardKalahBoard with data representing this state.
     */
    public StandardKalahBoard getBoard() {
        return currentBoard;
    }

    /**
     * Sets the board being used in this state.
     * @param currentBoard A StandardKalahBoard.
     */
    public void setBoard(StandardKalahBoard currentBoard) {
        this.currentBoard = currentBoard;
    }

    /**
     * Gets the starting player in this state.
     * @return An int representing player 0  or player 1.
     */
    public int getTurn() {
        return starts;
    }

    /**
     * Sets the value of the current player.
     * @param currentPlayer Player to play this turn (either 0 or 1).
     */
    public void setCurrentPlayer(int starts) {
        this.starts = starts;
    }

    /**
     * Returns a formatted string with data from the board class.
     * @return A formatted string.
     */
    @Override
    public String toString() {
        return currentBoard.toString();
    }

    /**
     * Checks if the game is over.
     * @return Boolean.  True if game is over, false otherwise.  Check
     * StandardKalahBoard.gameFinished for more details.
     */
    boolean gameOver() {
        return currentBoard.gameFinished(starts);
    }

    /**
     * Clones this object.
     * @return A cloned object.
     */
    GameState copy() {
        return new GameState(currentBoard.makeCopy(), starts);
    }

    /**
     * Makes a move on the board.
     * @param move Number of the pit to move stones from.
     * @throws IllegalMoveException Thrown if the move is not legal.
     */
    void makeMove(int move) throws IllegalMoveException {
        starts = currentBoard.makeMove(starts, move);
    }

}
