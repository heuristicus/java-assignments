/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuzzWords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author michal
 */
public class BuzzPlayer {

    String webURL;
    URL url = null;
    URLConnection urlC = null;
    InputStreamReader readIn = null;
    BufferedReader buffR = null;
    int score = 0;

    /**
     * Default constructor.
     * Takes a url string and initialises the various streams and things required
     * for a connection to the stream to be made.
     * @param _url
     */
    public BuzzPlayer(String _url) {
        webURL = _url;
    }

    /**
     * Connects to the url specified in the constructor parameters.
     *
     * Much of this method was written using http://java.sys-con.com/node/39248
     * as a guide to setting up the correct streams for reading from URLs, and therefore it
     * bears quite a significant resemblance.
     */
    public void openURLConnection() {
        try {
            url = new URL(webURL);
            urlC = url.openConnection();
            readIn = new InputStreamReader(urlC.getInputStream());
            buffR = new BufferedReader(readIn);
        } catch (MalformedURLException ex) {
            System.out.println("The URL " + webURL + "seems to be invalid.  Please check it.");
        } catch (IOException ex) {
            System.out.println("An error occured when attempting to connect to the URL.");
        }
    }

    /**
     * Closes all streams that this object contains.
     */
    public void closeURLConnection() {
        try {
            readIn.close();
            buffR.close();
        } catch (IOException ex) {
            System.out.println("Could not close streams properly.  Odd things may happen.");
        }
    }

    /**
     * Gets the reader that is connected to the URL.
     * @return A bufferedreader connected to the url specified by the player.
     */
    public BufferedReader getPageReader() {
        return buffR;
    }

    /**
     * Gets the web url that this player is connected to, or will connect to.
     * @return The web url.
     */
    public String getWebURL() {
        return webURL;
    }

    /**
     * Changes the URL that this player is connected to.  First closes all the
     * connections to the old URL, then changes the URL to be used to the one in
     * the parameters, and finally opens a connection to the new URL.
     * @param webURl
     */
    public void setWebURL(String _webURL) {
        closeURLConnection();
        webURL = _webURL;
        openURLConnection();
    }

    /**
     * Gets this player's current score.
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds to the current score of this player.
     * @param score The number of points to add.
     */
    public void addToScore(int _points) {
        score += _points;
    }



}
