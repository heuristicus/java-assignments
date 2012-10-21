/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex15;

/**
 *
 * @author Michal
 */
public class alphaBetaPlayer extends Player {

    int maxPlayer;
    int maxDepth;

    /**
     * Constructor.  Sets the player for which to do the maximising on.
     * @param maxPlayer Player to do maximising on.
     * @param maxDepth The maximum startDepth to recurse down to.
     */
    public alphaBetaPlayer(int maxPlayer, int maxDepth) {
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
                moveUtility = alphaBeta(workState, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (moveUtility > currentBestUtility) {
                    currentBestUtility = moveUtility;
                    bestMove = i;
                }
            }
        }
//        System.out.println(bestMove);
        return bestMove;
    }

    /**
     * 
     * @param s
     * @param player
     * @param startDepth
     * @param alpha
     * @param beta
     * @return
     */
    public int alphaBeta(GameState s, int startDepth, int alpha, int beta) {
        int a = alpha;
        int newAlpha = Integer.MIN_VALUE;
        int b = beta;
        int newBeta = Integer.MAX_VALUE;
        if (startDepth == maxDepth || s.gameOver()) {
            return evaluateState(s);
        } else if (s.getTurn() == maxPlayer) {
            for (int i = 1; i < 7; i++) {
                GameState workState = s.copy();
                try {
                    workState.makeMove(i);
                } catch (IllegalMoveException ex) {
                }
                newAlpha = alphaBeta(workState, startDepth + 1, a, b);
                if (newAlpha >= b) {
                    break;
                } else if (newAlpha > a) {
                    a = newAlpha;
                }
            }
            return a;
        } else {
            for (int i = 1; i < 7; i++) {
                GameState workState = s.copy();
                try {
                    workState.makeMove(i);
                } catch (IllegalMoveException ex) {
                }
                newBeta = alphaBeta(workState, startDepth + 1, a, b);
                if (a >= newBeta) {
                    break;
                } else if (newBeta < b) {
                    b = newBeta;
                }
            }
            return b;
        }
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
