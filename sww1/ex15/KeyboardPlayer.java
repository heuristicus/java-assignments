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
public class KeyboardPlayer extends Player {

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
        if (input.length() >= 4 && input.subSequence(input.length() - 4, input.length()).equals("exit")) {
            System.exit(0);
        } else if (input.length() >= 4 && input.subSequence(input.length() - 4, input.length()).equals("undo")) {
            System.out.println("Can't do this yet.  Piffle.");
        }
        for (int i = input.length(); i > 0; i--) {
            try {
                cupNumber = Integer.parseInt(input.substring(i - 1, i));
            } catch (Exception e) {
                continue;
            }
            break;
        }
        if (cupNumber == Integer.MIN_VALUE) {
            System.out.println("You have not entered a valid integer, please try again.");
            pickMove(s);
        }
        return cupNumber;
    }
}
