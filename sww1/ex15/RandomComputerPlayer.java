/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex15;

/**
 *
 * @author Michal
 */
public class RandomComputerPlayer extends Player {

    /**
     *
     * @param s A gamestate s.  Not used by this particular instance of Player,
     * as it plays by choosing a random cup to move from.
     * @return An int representing the cup to move stones from.
     * @throws NoMoveAvailableException
     */
    @Override
    public int pickMove(GameState s) throws NoMoveAvailableException {
        int returnValue = (int)(Math.random() * 6) + 1;
        return returnValue;

    }
}
