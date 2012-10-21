package ex15;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Game {

    private Player[] player = new Player[2];
    private GameState state;
    private GameStateMoveList gameRecord;
    private GameState startState;
    private boolean start = false;
    private boolean viewingGame = false;
    private int command = 0;
    private Scanner inputStream = new Scanner(System.in);

    /**
     * Standard constructor.
     * @param board The board to use with this game.
     * @param A The player to use for player 0.
     * @param B The player to use for player 1.
     * @param starts The player who starts the game.
     */
    public Game(Board board, Player A, Player B, int starts) {
        player[0] = A;
        player[1] = B;
        state = new GameState(board, starts);
        startState = new GameState(board.makeCopy(), starts);
        gameRecord = new GameStateMoveList();

    }

    /**
     * Plays a single game using the specified parameters.
     */
    public void play() {
        System.out.println("If you would like to load a game, please input 'load'.\n"
                + "To start the game, enter 'play'.");
        while (!start) {
            getKeyboardInput();
        }
        System.out.println(state);
        while (!state.gameOver()) {
            int move;
            try {
                move = player[state.getTurn()].pickMove(state);
                checkReturnMove(move);
                switch (command) {
                    case 0:
                        System.out.println("Current turn is " + state.getTurn());
                        GameState prevState = state.copy();
                        state.makeMove(move);
                        gameRecord.add(new GameStateMove(prevState, move));
                        System.out.println("Move made: " + move);
                        System.out.println(state);
                        break;
                    case 1:
                        command = 0;
                        break;
                }
            } catch (IllegalMoveException ex) {
                System.out.println("Illegal move.  Please try again.");
            } catch (NoMoveAvailableException ex) {
                System.out.println("No moves are available, ending the game.");
                break;
            } catch (Exception ex) {
                System.out.println("An exception occurred!");
                ex.printStackTrace();
            }
        }
        reportResult();
        System.out.println("If you would like to save your game, please input 'save',"
                + " press enter, and then enter the full file location you would like to save to.");
        getKeyboardInput();
    }

    /**
     * Plays a specified number of games.  Used for playing AI players against each other.
     * @param numberOfGames Number of games to play.
     */
    public String playMultipleGames(int numberOfGames) {
        double player0Wins = 0;
        double player1Wins = 0;
        double draws = 0;
        double p0percent;
        double p1percent;
        double drawPercent;
        for (int i = 0; i < numberOfGames; i++) {
            System.out.println("game " + i);
            while (!state.gameOver()) {
                int move;
                try {
                    move = player[state.getTurn()].pickMove(state);
                    state.makeMove(move);
                } catch (IllegalMoveException ex) {
                } catch (NoMoveAvailableException ex) {
                    break;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (state.getBoard().score() > 0) {
                player0Wins++;
            } else if (state.getBoard().score() < 0) {
                player1Wins++;
            } else {
                draws++;
            }
            startState.starts = Math.abs(startState.starts - 1);
            state = startState.copy();
        }

        p0percent = (player0Wins / numberOfGames) * 100;
        p1percent = (player1Wins / numberOfGames) * 100;
        drawPercent = (draws / numberOfGames) * 100;

        StringBuilder sb = new StringBuilder();
        sb.append("Player zero (");
        sb.append(player[0].getClass());
        sb.append(") won ");
        sb.append(player0Wins);
        sb.append(" times.\n");
        sb.append("Player one (");
        sb.append(player[1].getClass());
        sb.append(") won ");
        sb.append(player1Wins);
        sb.append(" times.\n");
        sb.append("There were ");
        sb.append(draws);
        sb.append(" draws.");
        sb.append("\nThe win percentage for Player zero was " + p0percent + "%");
        sb.append("\nThe win percentage for Player one was " + p1percent + "%");
        sb.append("\nThe draw percentage was " + drawPercent + "%");
        return sb.toString();
    }

    /**
     * Reports the final result in the game.
     */
    public void reportResult() {
        int score = state.getBoard().score();
        if (score > 0) {
            System.out.println("\n Player 0 won by " + score);
        } else {
            score = -1 * score;
            System.out.println("Player 1 won by  " + score);
        }
        System.out.println(state);
    }

    /**
     * Goes backwards one move in the game.
     */
    public void windBack() {
        if (gameRecord.size() == 0) {
            System.out.println("You cannot go back from the start of the game.");
        } else {
            state = gameRecord.remove().getState();
        }
    }

    /**
     * Returns all parameters to their start states.
     */
    public void startNewGame() {
        state = startState.copy();
        gameRecord = new GameStateMoveList();
    }

    /**
     * Checks the move returned by the keyboard player to see if
     * the player has typed in a command.
     * @param moveNumber
     */
    public void checkReturnMove(int moveNumber) throws FileNotFoundException, IOException, ClassNotFoundException {

        if (moveNumber >= 10) {
            switch (moveNumber) {
                default:
                    break;
                case 10:
                    System.out.println("Are you sure you want to exit? (y/n)");
                    if (getInputValue(1).equals("y")) {
                        System.exit(0);
                    }
                    break;
                case 11:
                    System.out.println("Going back one move...");
                    windBack();
                    break;
                case 12:
                    loadGameRecord();
                    break;
                case 13:
                    saveGameRecord();
                    break;
                case 14:
                    System.out.println("Exiting.");
                    System.exit(0);
                    break;
                case 15:
                    System.out.println("Are you sure you want to restart? (y/n)");
                    if (getInputValue(1).equals("y")) {
                        startNewGame();
                    }
                    break;
            }
            command = 1;
        }
        System.out.println(state);
    }

    /**
     * Gets input from the keyboard and performs various actions
     * depending on what is input.
     */
    public void getKeyboardInput() {
        String in = inputStream.nextLine();

        if (getSubString(4, in).equals("save")) {
            try {
                saveGameRecord();
            } catch (FileNotFoundException ex) {
                System.out.println("File not found.");
            } catch (IOException ex) {
                System.out.println("IO exception occurred.");
            }
            System.out.println("Save successful!");
        } else if (getSubString(4, in).equals("load")) {
            try {
                loadGameRecord();
            } catch (FileNotFoundException ex) {
                System.out.println("File not found.");
            } catch (IOException ex) {
                System.out.println("IO exception occurred.");
            } catch (ClassNotFoundException ex) {
                System.out.println("Class not found.");
            }
            System.out.println("Load successful!");
        } else if (getSubString(4, in).equals("play")) {
            start = true;
            System.out.println("Starting game...");
        } else {
            System.out.println("Unrecognised command.");
        }
    }

    /**
     * Gets a value from the input stream of a specified length.
     * @param lengthOfValue Length of the string to return.
     * @return A string which is the last x characters of the input string,
     * where x is the input parameter.
     */
    public String getInputValue(int lengthOfValue) {
        String subString;
        String input = inputStream.nextLine();
        if (lengthOfValue == 0) {
            return input;
        } else if (input.length() >= lengthOfValue) {
            subString = (String) input.subSequence(input.length() - lengthOfValue, input.length());
            return subString;
        } else {
            return null;
        }
    }

    /**
     * Gets the last 'length' characters of a string.
     * @param length The length of the string you want to have returned.
     * @param workString The string to perform the operation on.
     * @return The last 'length' characters of the string.
     */
    public String getSubString(int length, String workString) {
        return workString.substring(workString.length() - length, workString.length());
    }

    /**
     * Gets the current state of the game.
     * @return GameState representation of the current state of the game.
     */
    public GameState getState() {
        return state;
    }

    /**
     * Sets the current state of the game.
     * @param state State to set the game to.
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * Gets the list of game states along with the moves made on them.
     * @return A GameStateMoveList containing the GameStateMoves made in
     * this game.
     */
    public GameStateMoveList getGameRecord() {
        return gameRecord;
    }

    /**
     * Sets the value of the preceding game moves.
     * @param gameRecord A GameStateMoveList with states preceding
     * the current state.
     */
    public void setGameRecord(GameStateMoveList gameRecord) {
        this.gameRecord = gameRecord;
    }

    /**
     * Saves the gameRecord to a specified file.
     * @param fileName File to save the game record to.
     */
    public void saveGameRecord() throws FileNotFoundException, IOException {
        System.out.println("Please enter the save location.");
        String fileName = getInputValue(0);
        System.out.println("Saving this game to " + fileName + ".");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(getGameRecord());
    }

    /**
     * Loads a game record from a file.
     * @param fileName File to load the game record from.  Must contain
     * a GameStateMoveList object.
     */
    public void loadGameRecord() throws FileNotFoundException, IOException, ClassNotFoundException {
        System.out.println("Please enter the name of the file to load.");
        String fileName = getInputValue(0);
        System.out.println("Loading game from " + fileName + ".");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        setGameRecord((GameStateMoveList) in.readObject());
        lookThroughGame();
    }

    /**
     * Initiates looking through a game.
     */
    private void lookThroughGame() {
        System.out.println("If you would like to go through the game from"
                + " the start, enter 'forwards'.  To go through backwards, enter 'backwards'.");
        String input = inputStream.nextLine();
        viewingGame = true;
        if (getSubString(8, input).equals("forwards")) {
            lookThrough(0);
        } else if (getSubString(9, input).equals("backwards")) {
            lookThrough(1);
        }
    }

    /**
     * Looks through a game.
     * @param startPosition The position at which to start.  0 indicates the
     * start of the game, 1 indicates the end.
     */
    private void lookThrough(int startPosition) {
        System.out.println("To go backwards, enter 'back'.  To go forwards, enter 'next'.\n"
                + "To continue the game from the current state, enter 'play'.  You will need to "
                + "type play again, once 'load successful!' appears.\n"
                + "To stop looking through and start a new game, enter 'new'.");
        boolean done = false;
        start = false;
        GameState currentState = null;
        int currentStateLoc = 0;
        switch (startPosition) {
            case 0:
                currentState = gameRecord.getStateAt(0).getState();
                currentStateLoc = 0;
                break;
            case 1:
                currentState = gameRecord.getStateAt(gameRecord.size() - 1).getState();
                currentStateLoc = gameRecord.size() - 1;
                break;
        }
        while (!done) {
            System.out.println(currentState);
            String in = inputStream.nextLine();
            if (getSubString(3, in).equals("new")) {
                done = true;
                startNewGame();
            } else if (getSubString(4, in).equals("next")) {
                if (currentStateLoc >= gameRecord.size() - 1) {
                    System.out.println("You have reached the end of the game.");
                } else {
                    currentStateLoc++;
                    currentState = gameRecord.getStateAt(currentStateLoc).getState();
                }
            } else if (getSubString(4, in).equals("back")) {
                if (currentStateLoc <= 0) {
                    System.out.println("You have reached the start of the game.");
                } else {
                    currentStateLoc--;
                    currentState = gameRecord.getStateAt(currentStateLoc).getState();
                }
            } else if (getSubString(4, in).equals("play")) {
                done = true;
                gameRecord = new GameStateMoveList();
                state = currentState;
            }

        }
        viewingGame = false;
    }

    /**
     * Gets the player at location i in the player array.
     * @param i Index in the player array to return.
     * @return A player.
     */
    public Player getPlayer(int i) {
        return player[i];


    }

    public static void main(String[] args) {
//        Game test = new Game(new StandardKalahBoard(), new minimaxPlayer(0, 2), new RandomComputerPlayer(), 0);
//        Game test = new Game(new StandardKalahBoard(), new KeyboardPlayer(), new RandomComputerPlayer(), 0);
//        Game test = new Game(new StandardKalahBoard(), new KeyboardPlayer(), new minimaxPlayer(1, 6), 0);
//        Game test = new Game(new StandardKalahBoard(), new minimaxPlayer(0, 4), new KeyboardPlayer(), 0);
//        Game test = new Game(new StandardKalahBoard(), new KeyboardPlayerv2(), new alphaBetaPlayer(1, 8), 0);
//        test.gameRecord.add(null);
//        test.gameRecord.add(null);
//        test.gameRecord.add(null);
//        test.gameRecord.add(null);
//        test.gameRecord.add(null);
//        try {
//            test.saveGameRecord();
//        } catch (FileNotFoundException ex) {
//            System.out.println("broke");
//        } catch (IOException ex) {
//            System.out.println("broke2");
//        }
//        try {
//            test.loadGameRecord();
//        } catch (FileNotFoundException ex) {
//                        System.out.println("broke3");
//
//        } catch (IOException ex) {
//                        System.out.println("broke4");
//
//        } catch (ClassNotFoundException ex) {
//                        System.out.println("broke5");
//
//        }
//        test.play();

//        Game multiPlayTest = new Game(new StandardKalahBoard(), new RandomComputerPlayer(), new RandomComputerPlayer(), 0);
        Game multiPlayTest = new Game(new StandardKalahBoard(), new minimaxPlayer(0, 8), new alphaBetaPlayer(1, 8), 0);
//        Game multiPlayTest = new Game(new StandardKalahBoard(), new minimaxPlayer(0, 6), new RandomComputerPlayer(), 0);
//        Game multiPlayTest = new Game(new StandardKalahBoard(), new RandomComputerPlayer(), new alphaBetaPlayer(1, 8), 0);
        System.out.println(multiPlayTest.playMultipleGames(100));


    }
}
