/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuzzWords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author michal
 */
public class BuzzWords {

    String buzzWordFile;
    ArrayList<String> buzzWords = new ArrayList<String>();
    ArrayList<BuzzPlayer> players = new ArrayList<BuzzPlayer>();

    public static void main(String[] args) {
        BuzzWords b = new BuzzWords("commonwords.txt", "http://cs.bham.ac.uk/");
        b.scorePlayers();
    }

    /**
     * Default constructor
     */
    public BuzzWords() {
        getPlayerURLsFromConsole();
    }

    public BuzzWords(String _buzzWordFile, String _webURL) {
        buzzWordFile = _buzzWordFile;
        players.add(new BuzzPlayer(_webURL));
    }

    public BuzzWords(String _buzzWordFile, String _p1WebURL, String _p2WebURL) {
        players.add(new BuzzPlayer(_p1WebURL));
        players.add(new BuzzPlayer(_p2WebURL));
        buzzWordFile = _buzzWordFile;
    }

    /**
     * Allows initialisation of this object with a number of players defined
     * by the size of the _playerURLs list.  Takes each string from the arraylist
     * that is passed to the constructor and constructs new buzzplayers from each url.
     * @param _playerURLs An arraylist of urls, corresponding to each player's url choice.
     * Note that the strings are not checked to ensure that the url is valid, and so
     * if invalid urls are entered, the program will not function entirely correctly.
     */
    public BuzzWords(ArrayList<String> _playerURLs, String _buzzWordFile) {
        for (String string : _playerURLs) {
            players.add(new BuzzPlayer(string));
        }
        buzzWordFile = _buzzWordFile;
    }

    /**
     * Gets buzzwords from the file specified in the constructor call.
     */
    private void getBuzzWords() {
        getBuzzWordsFromFile(buzzWordFile);
    }

    /**
     * Gets buzzwords from the file passed to the method
     */
    public void getBuzzWordsFromFile(String _filename) {
        BufferedReader fileIn = null;
        try {
            fileIn = new BufferedReader(new FileReader(new File(_filename)));
            boolean readComplete = false;

            while (!readComplete) {
                String word = fileIn.readLine();
                if (word == null) {
                    readComplete = true;
                } else {
                    buzzWords.add(word);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("Something bad happened.");
        } finally {
            try {
                fileIn.close();
            } catch (IOException ex) {
                System.out.println("Something really, really bad happened.  Exiting.");
                System.exit(0);
            }
        }
    }

    /**
     * Gets URLs from players using the console.
     */
    private void getPlayerURLsFromConsole() {
    }

    public void scorePlayers() {
        getBuzzWords();
        int currentLeader = 0;
        for (int i = 0; i < players.size(); i++) {
            matchStrings(players.get(i));
            /* if the player at location i's score is greater than the current leader's score
             * then the new leader is player i, otherwise the leader remains the same.
             */
            currentLeader = players.get(i).getScore() > players.get(currentLeader).getScore() ? i : currentLeader;
        }
        System.out.println("The winner is player " + currentLeader + " with " + players.get(currentLeader).getScore() + " points.");
    }

    /**
     * Matches strings from the url to buzzwords until the stream no longer contains data.
     * @return
     */
    private void matchStrings(BuzzPlayer _player) {
        try {
            _player.openURLConnection();
            String urlLine = _player.getPageReader().readLine();
            System.out.println(urlLine);
            while (urlLine != null) {
                _player.addToScore(matchURLWord(urlLine));
                urlLine = _player.getPageReader().readLine();
            }
            _player.closeURLConnection();
        } catch (IOException e) {
            System.out.println("An IO exception ocurred.");
        }
    }

    /**
     * Matches a buzzword to a string pulled from a url.
     * @param _urlLine The string containing words from the url.
     * @return An int containing the total number of matches that ocurred.
     */
    public int matchURLWord(String _urlLine) {
        int totalMatches = 0;
//        String tString = "This this this this is a test to check the proper balance of your loudspeakers.";
        for (String string : buzzWords) {
            int wordMatches = 0;
//            System.out.println("Checking for string " + string + ".");
            Pattern p = Pattern.compile("\\s" + string + "\\s", Pattern.CASE_INSENSITIVE | Pattern.CANON_EQ);
            Matcher m = p.matcher(_urlLine);
            boolean strFound = m.find();
            while (strFound) {
                wordMatches++;
                strFound = m.find();
            }
            if (wordMatches > 0) {
                System.out.println("String \"" + string + "\" found " + wordMatches + " times in string \"" + _urlLine + "\"");
            }
            totalMatches += wordMatches;
        }
        return totalMatches;
    }

    /**
     * Prints the contents of the buzzWords array - this gives all the
     * current buzzwords.
     */
    public void printCurrentBuzzWords() {
        for (String string : buzzWords) {
            System.out.println(string);
        }
    }

    /**
     * gets the player at a specific index of the array
     * @param index Index at which the player is
     * @return Player at index.
     */
    public BuzzPlayer getPlayer(int index) {
        return players.get(index);
    }
}
