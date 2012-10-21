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
class GameStateMove implements Serializable{

    private GameState prevState;
    private int move;

    /**
     * Default constructor.
     * @param prevState Previous state of the game.
     * @param move The move that the player made on the game state.
     */
    public GameStateMove(GameState prevState, int move) {
        this.prevState = prevState;
        this.move = move;
    }

    /**
     * Gets the state of the game.
     * @return
     */
    GameState getState() {
        return prevState;
    }

    /**
     * Outputs the state and the move that was made on it.
     * @return
     */
    @Override
    public String toString() {
        return prevState.toString() + "\nThe move made on this state was " + move;
    }



}
