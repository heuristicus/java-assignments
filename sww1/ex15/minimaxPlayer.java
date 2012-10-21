/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex15;

/**
 *
 * @author Michal
 */
public class minimaxPlayer extends Player {

    int maxPlayer;
    int maxDepth;

    /**
     * Constructor.  Sets the player for which to do the maximising on.
     * @param maxPlayer Player to do maximising on.
     * @param maxDepth The maximum startDepth to recurse down to.
     */
    public minimaxPlayer(int maxPlayer, int maxDepth) {
        this.maxPlayer = maxPlayer;
        this.maxDepth = maxDepth;
    }

    /**
     * Picks a move.
     * @param s A game state to pick a move from.
     * @return An integer which represents the move to be taken.
     * @throws NoMoveAvailableException
     */
    @Override
    public int pickMove(GameState s) throws NoMoveAvailableException {
        int moveUtility = 0;
        int currentBestUtility = Integer.MIN_VALUE;
        int bestMove = 0;

        for (int i = 1; i < 7; i++) {
            GameState workState = s.copy();
//            System.out.println("Move " + i);
//            System.out.println(workState);
//            System.out.println("Stone value before move " + workState.getBoard().getStones(workState.getTurn(), i));
            if (workState.getBoard().getStones(workState.getTurn(), i) != 0) {
                try {
                    workState.makeMove(i);
                } catch (IllegalMoveException ex) {
                }
//                System.out.println(workState);
//                System.out.println("Stone value after move " + workState.getBoard().getStones(workState.getTurn(), i));
                    moveUtility = miniMax(workState, 0);
                    if (moveUtility > currentBestUtility) {
                        currentBestUtility = moveUtility;
                        bestMove = i;
                }
            }
        }
        return bestMove;
    }

    /**
     * Recursively performs minimax on a node up until the base case is reached.
     * @param nodeIndex The node to start the minimax procedure on.
     * @param startDepth The startDepth to which to do the search.
     * @return An integer representing the move that the algorithm chooses as the
     * best, based on the evaluateState method.
     */
    public int miniMax(GameState s, int startDepth) {
        if (startDepth == maxDepth || s.gameOver()) {
            return evaluateState(s);
        } else if (s.getTurn() == maxPlayer) {
            return max(s, startDepth);
        } else {
            return min(s, startDepth);
        }
    }

    /**
     * Returns the value of the move with the minimum utility to the
     * maximising player.
     * @param s State to minimise.
     * @param depth Current depth.
     * @return An integer with the lowest utility of the moves in this state.
     */
    public int min(GameState s, int depth) {
        int minValue = Integer.MAX_VALUE;
        for (int i = 1; i < 7; i++) {
            GameState workState = s.copy();
            try {
                workState.makeMove(i);
            } catch (IllegalMoveException ex) {
            }
            int stateUtility = miniMax(workState, depth + 1);
            if (stateUtility < minValue) {
                minValue = stateUtility;
            }
        }
        return minValue;
    }

    /**
     * Returns the value of the move with the maximum utility
     * to the maximising player.
     * @param s State to maximise.
     * @param depth Current depth.
     * @return An integer with the highest utility of the moves in this state.
     */
    public int max(GameState s, int depth) {
        int maxValue = Integer.MIN_VALUE;
        for (int i = 1; i < 7; i++) {
            GameState workState = s.copy();
            try {
                workState.makeMove(i);
            } catch (IllegalMoveException ex) {
            }
            int stateUtility = miniMax(workState, depth + 1);
            if (stateUtility > maxValue) {
                maxValue = stateUtility;
            }
        }
        return maxValue;
    }

    /**
     * Evaluates the state.
     * @param s A game state to evaluate.
     * @return An integer representing the utility of the state.
     */
    public int evaluateState(GameState s) {
        int score = s.getBoard().score();
        if (maxPlayer == 1) {
            score = score * -1;
        }
        return score;
    }
}
