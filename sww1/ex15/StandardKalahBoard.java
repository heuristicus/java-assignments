/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex15;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Michal
 */
public class StandardKalahBoard extends KalahBoard implements Serializable {

    private static final int DEFAULT_BOARD_SIZE = 14, DEFAULT_BEAD_NUMBER = 3;
    private final int PLAYER_ZERO = 0, PLAYER_ONE = 1;
    protected int[] gameBoard = new int[DEFAULT_BOARD_SIZE];
    protected int currentPlayer = PLAYER_ZERO;

    /**
     * Standard constructor.  Initialises the array with the default number of beads.
     */
    public StandardKalahBoard() {
        initialise(DEFAULT_BEAD_NUMBER);
    }

    /**
     * Constructor that takes the number of stones to be placed in each pit
     * as a parameter.  Initialises the array with that number of stones.
     * @param stonesPerPit Number of stones to place in each pit.
     */
    public StandardKalahBoard(int stonesPerPit) {
        this.initialise(stonesPerPit);
    }

    /**
     * Extra constructor.  Makes a new board from an array of ints and a player value.
     * @param gameBoard A gameboard from another Standardkalahboard object.
     * @param currentPlayer A player number, either 0 or 1.
     */
    public StandardKalahBoard(int[] gameBoard, int currentPlayer) {
        this.gameBoard = gameBoard;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Initialises the array which represents the pits with a specified number of stones in
     * each pit, defined by the parameter stonesPerPit.
     *
     * Does not populate array locations 0 or 7.  Location 0 is player 0's kalah, and location
     * 7 is player 1's kalah.
     *
     * @param stonesPerPit Number of stones to place into each pit.
     */
    @Override
    public void initialise(int stonesPerPit) {
        for (int i = 1; i < gameBoard.length; i++) {
            if (i != 7) {
                gameBoard[i] = stonesPerPit;
            }
        }
    }

    /**
     * Gets the number of stones in a specific pit, defined by which player is input.
     * @param playerNum Player whose pit you want to check.
     * @param pitNum Number of the pit you want to check,
     * @return
     */
    @Override
    public int getStones(int playerNum, int pitNum) {
        switch (playerNum) {
            case 0:
                return gameBoard[pitNum];
            case 1:
                return gameBoard[pitNum + 7];
        }
        return 0;
    }

    /**
     * Gets the number of stones in the player's kalah.
     * @param playerNum Player whose kalah to return the stones from. (1 or 0)
     * @return Number of stones in the players kalah.
     */
    @Override
    public int getKalah(int playerNum) {
        switch (playerNum) {
            case 0:
                return gameBoard[7];
            case 1:
                return gameBoard[0];
        }
        return 0;
    }

    /**
     * Returns the contents of a specific pit.
     * @param index The index of the pit to check.
     * @return An int containing the number of stones in the specified pit.
     */
    public int getPit(int index) {
        return gameBoard[index];
    }

    /**
     * Returns the total number of stones in the input player's pits,
     * excluding the kalah.
     * @param player The player's pits to check.
     * @return An int with the total number of stones in the pits.
     */
    public int getPitTotal(int player) {
        int total = 0;
        switch (player) {
            case 0:
                for (int i = 1; i < 7; i++) {
                    total += gameBoard[i];
                }
                break;
            case 1:
                for (int i = 8; i < gameBoard.length - 1; i++) {
                    total += gameBoard[i];
                }
                break;
        }
        return total;
    }

    /**
     * Scores the board.  Uses the p0Score and p1Score methods to do so.
     * @return An integer representing the score of player 0 relative to player 1.
     * (i.e. a negative score means that p0 is losing.)
     */
    @Override
    public int score() {
        return p0Score() - p1Score();
    }

    /**
     * Gets p0's score.
     * @return The number of stones currently in p0's kalah and pots.
     */
    public int p0Score() {
        return (getKalah(0) + getPitTotal(0));

    }

    /**
     * Gets p1's score.
     * @return The number of stones currently in p1s kalah and pots.
     */
    public int p1Score() {
        return (getKalah(1) + getPitTotal(1));
    }

    /**
     * Checks whether the game is finished given the current player.
     * @param playerNum Number of current player (0 or 1)
     * @return Boolean representing whether the game is ended or not.  Returns true when
     * the input player number's pits contain no stones (excluding the kalah).  Returns false
     * otherwise.
     */
    @Override
    public boolean gameFinished(int playerNum) {

        /*
         * If either of the sides are empty, this code adds all the stones on
         * the non-empty side to that side's player's kalah, and returns true.
         */
        if (getPitTotal(0) == 0) {
            gameBoard[0] += getPitTotal(1);
            for (int i = 8; i < gameBoard.length; i++) {
                gameBoard[i] = 0;
            }
//            System.out.println("Final board state:");
//            System.out.println(this);
            return true;
        } else if (getPitTotal(1) == 0) {
            gameBoard[7] += getPitTotal(0);
            for (int i = 1; i < 7; i++) {
                gameBoard[i] = 0;
            }
//            System.out.println("Final board state:");
//            System.out.println(this);
            return true;
        }
        return false;
    }

    /**
     * Makes a move on the board.
     * @param turn The player making the move.
     * @param move The pit from which to move stones.
     * @return An integer representing the next player.
     * @throws IllegalMoveException
     */
    @Override
    public int makeMove(int turn, int move) throws IllegalMoveException {
        currentPlayer = turn;
        if (turn == 1) {
            move += 7;
        }
        if (notLegal(turn, move)) {
            throw new IllegalMoveException(move);
        }
        int stonesInHand = gameBoard[move];
        gameBoard[move] = 0;
        int pitCounter = 0;
        switch (turn) {
            case 0:
                for (pitCounter = move + 1; stonesInHand
                        > 0; pitCounter++) {
                    if (pitCounter % 14 != 0) {
                        gameBoard[pitCounter % 14] += 1;
                        stonesInHand--;
                    }
                }
                break;
            case 1:
                for (pitCounter = move + 1; stonesInHand
                        > 0; pitCounter++) {
                    if (pitCounter % 14 != 7) {
                        gameBoard[pitCounter % 14] += 1;
                        stonesInHand--;
                    }
                }
                break;
        }

        /*
         * One has to be taken off here, because the loop will go to the number above
         * the loop condition and finish the loop.  If this is left as is, pitcounter is
         * incremented one above the actual final pit.
         */
        pitCounter--;

        return doEndMoves(turn, pitCounter % 14);


    }

    /**
     * Executes moves possible at the end of each turn (capture move, extra turn).
     * @param playerTurn The number of the player this turn.
     * @param lastPit The pit into which the last stone was placed.
     * @return The next player.
     */
    public int doEndMoves(int playerTurn, int lastPit) {
        int nextPlayer = Math.abs(playerTurn - 1);
        int oppositePit = 14 - lastPit;

        if (lastPit == 0 || lastPit == 7) {
//            System.out.println("Player " + playerTurn + " ends in kalah.");
            nextPlayer = playerTurn;
        } else if (gameBoard[lastPit] - 1 == 0 && gameBoard[oppositePit] != 0) {
            switch (playerTurn) {
                case 0:
                    if (lastPit < 7) {
                        gameBoard[7] += (gameBoard[oppositePit] + 1);
//                        System.out.println("Player 0 capture.");
                        gameBoard[lastPit] = 0;
                        gameBoard[oppositePit] = 0;
                    }

                    break;
                case 1:
                    if (lastPit > 7) {
                        gameBoard[0] += (gameBoard[oppositePit] + 1);
//                        System.out.println("Player 1 capture.");
                        gameBoard[lastPit] = 0;
                        gameBoard[oppositePit] = 0;
                    }
                    break;
            }

        }
        return nextPlayer;


    }

    /**
     * Returns a boolean value representing whether the move being attempted is legal.
     * @param chosenPit The pit that the player wishes to move beads from.
     * @return Boolean value representing whether the move is allowed or not.  True if the move
     * is illegal, false otherwise.
     */
    public boolean notLegal(int player, int chosenPit) {
        switch (player) {
            case 0:
                return (gameBoard[chosenPit] == 0 || chosenPit <= 0 || chosenPit > 6);
            case 1:
                return (gameBoard[chosenPit] == 0 || chosenPit < 8 || chosenPit > 13);
        }
        return true;


    }

    /**
     * Makes a copy of the current board by creating a new kalah board from the current state
     * of the board.
     * @return
     */
    @Override
    public Board makeCopy() {
        return new StandardKalahBoard(gameBoard.clone(), currentPlayer);
    }

    /**
     * Writes the current object to the output stream.  The file to which data is output to
     * is not specified in this method or class, and so the output stream must be
     * created elsewhere.
     * @param out An object output stream outputting to a file to which to save the
     * object.
     * @throws IOException
     */
    @Override
    public void saveTo(ObjectOutputStream out) throws IOException {
        out.writeObject(this);
    }

    /**
     * Loads a board from an input stream.  Assigns the gameboard and currentplayer variables
     * in the read object to the current one.
     * @param in An inputStream.  Must be initialised before this method is called.
     * Must contain StandardKalahBoard object.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public void loadFrom(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.gameBoard = ((StandardKalahBoard) in.readObject()).gameBoard;
        this.currentPlayer = ((StandardKalahBoard) in.readObject()).currentPlayer;
    }

    /**
     * Creates a stringBulider and then appends various values so that the string
     * contains an approximate representation of what a kalah board looks like,
     * filled with the data that is present in the gameBoard array.
     * @return A formatted string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = gameBoard.length - 1; i
                > 7; i--) {
            sb.append("\t");
            sb.append(gameBoard[i]);
        }
        sb.append("\n");
        sb.append(gameBoard[0]);
        sb.append("\t\t\t\t\t\t\t\t");
        sb.append(gameBoard[7]);
        sb.append("\n");
        for (int i = 1; i
                <= 6; i++) {
            sb.append("\t");
            sb.append(gameBoard[i]);
        }
        sb.append("\n");
        return sb.toString();
    }

    public int total() {
        int total = 0;
        for (int i : gameBoard) {
            total += i;
        }
        return total;
    }
}






































