/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author michal
 */
public class testclient {

    SSLSocket sock;
    BufferedWriter out;
    BufferedReader in;
    ObjectInputStream objIn;
    String host;
    int port;

    public testclient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Initialises the socket and the streams that the object uses to
     * transfer data.
     */
    public void initialiseSocket() {
        try {
            System.setProperty("javax.net.ssl.trustStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");
            sock = (SSLSocket) SSLSocketFactory.getDefault().createSocket("localhost", 2000);
            System.out.printf("Connected to server at port %d.\n", sock.getPort());
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println("Streams successfully created.");
        } catch (UnknownHostException ex) {
            System.out.printf("Do not know host %s.\n", host);
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IO Exception while attempting to initialise client socket.");
            ex.printStackTrace();
        }
    }

    public void prepareServerSession() {
        try {
            handshake();
            System.out.println("Handshaking successful.");
            authenticate();
            System.out.println("Authentication successful.");
            System.out.println("Login to server successful.");
        } catch (IOException ex) {
            System.out.println("IO Exception while attempting to make a connection.");
            ex.printStackTrace();
        } catch (ActionFailedException ex) {
            System.out.println("An action failed while attempting to make a connection.");
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Point> getRegistrationPoints() throws IOException, ClassNotFoundException, ClassCastException {
        sendModuleData();
        // FIXME ***BELOW MAY CAUSE ERRORS (WHY THE HELL)***
        objIn = new ObjectInputStream(sock.getInputStream());
        ArrayList<Point> regPoints = (ArrayList<Point>) getObjectMessage();
        for (Point point : regPoints) {
            System.out.println(point);
        }
        return regPoints;
    }

    private void handshake() throws IOException, ActionFailedException {
        String greeting = "graphclient";
        sendString(greeting);
        String resp = getStringMessage();
        if (!resp.equals("graphserver")) {
            throw new ActionFailedException("Handshaking with the server failed.");
        }
    }

    private void authenticate() throws IOException, ActionFailedException {
        BufferedReader cmdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your username.");
        String user = cmdIn.readLine();
        System.out.println("Please enter your password.");
        String pass = cmdIn.readLine();

        String authStr = user + ":" + getPasswordHex(pass);

        sendString(authStr);
        String authRes = getStringMessage();
        if (!authRes.equals("authsuccess")) {
            System.out.println("Authentication with server failed. Try again? (y/n)");
            String retry = cmdIn.readLine();
            if (retry.equals("y") || retry.equals("yes")) {
                sendString("reauth");
                authenticate();
            } else {
                sendString("noreauth");
                throw new ActionFailedException("User terminated authentication with server.");
            }
        }
    }

    public void sendModuleData() throws IOException {
        BufferedReader cmdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter module id.");
        String modID = cmdIn.readLine();
        sendString(modID);
    }

    /**
     * Sends a single string to the server. The string should not contain new
     * line characters.
     * @param s
     * @throws IOException
     */
    public void sendString(String s) throws IOException {
        out.write(s);
        out.newLine();
        out.flush();
    }

//    public void spam() throws IOException{
//        while(true){
//        sendString("spam");
//        }
//    }

    public String getStringMessage() throws IOException{
        return in.readLine();
    }

    /**
     * Gets an object from the object input stream.
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object getObjectMessage() throws IOException, ClassNotFoundException {
        return objIn.readObject();
    }

    public static void main(String[] arstring) {
        testclient t = new testclient("localhost", 2000);
        t.initialiseSocket();
        t.prepareServerSession();
        try {
            ArrayList<Point> p = t.getRegistrationPoints();
        } catch (IOException ex) {
            System.out.println("IO exception while getting registration points.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found while getting registration points");
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            System.out.println("Classcast exception when attempting to cast received object.");
            ex.printStackTrace();
        }
        //        try {
        //            System.setProperty("javax.net.ssl.trustStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
        //            System.setProperty("javax.net.ssl.trustStorePassword", "password");
        //            SSLSocket sock = (SSLSocket) SSLSocketFactory.getDefault().createSocket("localhost", 2000);
        //            System.out.println("Connected to server.");
        //            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        //            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        //
        //            String greeting = "what ho, old chap?";
        //            out.write(greeting);
        //            out.newLine();
        //            out.flush();
        //
        //            String resp = in.readLine();
        //            if (resp.equals("good day, what?")) {
        //                System.out.println("well well well");
        //            } else {
        //                System.out.println("oh dear.");
        //                //TODO exit properly.
        //                return;
        //            }
        //
        //            // TODO read username and password
        //            String auth = "some tea, chap?";
        //            out.write(auth);
        //            out.newLine();
        //            out.flush();
        //
        //            String authRes = in.readLine();
        //            if (authRes.equals("love some, old boy")) {
        //                System.out.println("oooh");
        //                // TODO read module id
        //                out.write("3");
        //                out.newLine();
        //                out.flush();
        //            } else {
        //                System.out.println("ahhh");
        //            }
        //
        //            ObjectInputStream objIn = new ObjectInputStream(sock.getInputStream());
        //            ArrayList<Point> regPoints = (ArrayList<Point>) objIn.readObject();
        //            for (Point point : regPoints) {
        //                System.out.println(point);
        //            }
        //
        //
        //            out.close();
        //            in.close();
        //        } catch (Exception ex) {
        //        }
        //        }

    }

    // <editor-fold defaultstate="collapsed" desc="generates a password hex.">
    public String getPasswordHex(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] pass = password.getBytes();
            md.reset();
            md.update(pass);
            byte[] mdByte = md.digest();
            StringBuilder passHex = new StringBuilder();
            for (int i = 0; i
                    < mdByte.length; i++) {
                passHex.append(Integer.toHexString(0xFF & mdByte[i]));
            }
            return passHex.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Encryption algorithm not found.");
            System.exit(1);
        } catch (Exception ex) {
            System.out.println("Exception while trying to process password.");
            System.exit(1);
        } // Should never be reached. An exception will be thrown and the system will exit
        // or the method will return the hex string.
        return null;
    }
// </editor-fold>

}
