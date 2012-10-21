/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parsing;

/**
 * D -> $
 * D -> [D]D
 *
 * @author michal
 */
public class Parser1 {

    String parseString;

    public static void main(String[] args) {
        Parser1 p = new Parser1("[][[][[]]][[[][][][]]]$");
        try {
            p.parseD();
            System.out.println("Successfully parsed.");
        } catch (FormatException ex) {
            System.out.println("Incorrectly formatted string.");
        }
    }

    public Parser1(String parseString) {
        this.parseString = parseString;
    }

    public char lookahead() {
        System.out.println(parseString);
        if (parseString.length() != 0) {
            return parseString.charAt(0);
        } else {
            return '　';
        }
    }

    public void match(char c) throws FormatException {
        if (parseString.charAt(0) == c) {
            parseString = parseString.substring(1);
        } else {
            throw new FormatException();
        }
    }

    public void parseD() throws FormatException {
        switch (lookahead()) {
            case '[':
                match('[');
                parseD();
                match(']');
                parseD();
                break;
            case ']':
            case '$':
                break;
            default:
                throw new FormatException();
        }
    }
}
