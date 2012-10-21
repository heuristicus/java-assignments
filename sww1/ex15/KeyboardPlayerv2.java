/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex15;

import java.util.Scanner;

/**
 *
 * @author Michal
 */
public class KeyboardPlayerv2 extends Player {

    /**
     * Gets an integer value from the user's input in the console and returns
     * that.
     * @param s A game state in which to choose the move.
     * @return Integer input by the user into the console.
     * @throws NoMoveAvailableException
     */
    @Override
    public int pickMove(GameState s) throws NoMoveAvailableException {
        int cupNumber = Integer.MIN_VALUE;
        System.out.println("Please enter the number of the pit you wish to move from (1 to 6)");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        /**
         * Returns a different integer depending on how long the string is,
         * and what the value of it is.  These integers are handled in the
         * game class.
         */
        if (input.length() == 1) {
            for (int i = input.length(); i > 0; i--) {
                try {
                    cupNumber = Integer.parseInt(input.substring(i - 1, i));
                } catch (Exception e) {
                    continue;
                }
                break;
            }
        } else if (input.length() == 4) {
            if (input.equals("exit")) {
                return 10;
            } else if (input.equals("undo")) {
                return 11;
            } else if (input.equals("load")) {
                return 12;
            } else if (input.equals("save")) {
                return 13;
            }
        } else if (input.length() == 5) {
            if (input.equals("fexit")) {
                return 14;
            }
        } else if (input.length() == 7) {
            if (input.equals("restart")) {
                return 15;
            }
        }
        if (cupNumber == Integer.MIN_VALUE) {
            System.out.println("You have not entered a valid integer, please try again.");
            pickMove(s);
        }
        return cupNumber;
    }

}
