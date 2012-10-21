/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex15;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Michal
 */
class GameStateMoveList implements Serializable{

    private final ArrayList<GameStateMove> moveList;

    /**
     * Default constructor.  Initialises the ArrayList.
     */
    public GameStateMoveList() {
        moveList = new ArrayList<GameStateMove>();
    }

    /**
     * Adds a gameStateMove to the list.
     * @param gameStateMove State to add to the list.
     */
    void add(GameStateMove gameStateMove) {
        moveList.add(gameStateMove);
    }

    /**
     * Gets the number of elements in the list.
     * @return Number of elements in the list.
     */
    int size() {
        return moveList.size();
    }

    /**
     * Gets the object at a specific index.
     * @param index Index to get the object from.
     * @return Object at the index (the object remains in the
     * arraylist)
     */
    GameStateMove getStateAt(int index) {
        return moveList.get(index);
    }

    /**
     * Removes the last element put into the list.
     * @return The last object put into the list (size - 1).
     */
    GameStateMove remove() {
        return moveList.remove(moveList.size() - 1);
    }

    /**
     * Returns the object at a specified index.
     * @param index Index of the object.
     * @return GameStateMove object from the index.
     */
    GameStateMove removeIndex(int index) {
        return moveList.remove(index);
    }

}
