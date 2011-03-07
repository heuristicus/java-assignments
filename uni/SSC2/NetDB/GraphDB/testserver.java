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
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author michal
 */
public class testserver {

    SSLSocket sock;
    SSLServerSocket servSock;
    BufferedWriter out;
    BufferedReader in;
    ObjectOutputStream objOut;
    int port;
    DBAccessor database;

    public testserver(int port) {
        this.port = port;
        database = new DBAccessor("jdbc:postgresql://localhost:5432/uschool", "mich", "mich");
    }

    public void initialiseSocket() {
        try {
            System.setProperty("javax.net.ssl.keyStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");
            servSock = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(2000);
            System.out.printf("Server listening on port %d.\n", servSock.getLocalPort());
            sock = (SSLSocket) servSock.accept();
            System.out.printf("Client connected from %s.\n", sock.getInetAddress());
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (IOException ex) {
            System.out.printf("IO Exception while attempting to listen at port %d.\n", port);
            ex.printStackTrace();
        }
    }

    /**
     * Verifies that the client can use the database for what this server provides.
     */
    public void verifyClient() {
        try {
            handshake();
            System.out.println("Handshaking successful.");
            authenticate();
            System.out.println("Authentication successful.");
            System.out.println("Client privileges verified.");
            readModuleID();
        } catch (ActionFailedException ex) {
            System.out.println("Action failed while performing initial interactions with the client.");
            System.out.println(ex.getMessage());
            resetSocket();
        } catch (IOException ex) {
            System.out.println("IO Exception while doing intial interaction with client.");
            ex.printStackTrace();
            resetSocket();
        }
    }

    public void handshake() throws ActionFailedException, IOException {
        String greeting = in.readLine();
        if (!greeting.equals("graphclient")) {
            throw new ActionFailedException("Received incorrect message from client.");
        }
        sendString("graphserver");
    }

    /**
     * Authenticates the client with the username and password.
     * @throws IOException
     * @throws ActionFailedException
     */
    public void authenticate() throws IOException, ActionFailedException {
        try {
            String userDetails = getStringMessage();
            String[] spDet = userDetails.split(":");
//            System.out.println(spDet[0] + " " + spDet[1]);
            String role = database.getUserPrivileges(spDet[0], spDet[1]);
            System.out.println(role);
            if (!role.equals("administrator")) {
                sendString("authfailed");
                String reply = getStringMessage();
                if (reply.equals("reauth")) {
                    authenticate();
                } else {
                    database.disconnect();
                    throw new ActionFailedException("User quit authentication process.");
                }
            }
            sendString("authsuccess");
        } catch (SQLException ex) {
            System.out.println("Failed to get user details from the database.");
            ex.printStackTrace();
        }
    }

    public void readModuleID() throws IOException {
        int modID = Integer.parseInt(getStringMessage());
        System.out.println("Modid " + modID);
        ArrayList<Point> regPoint = new ArrayList<Point>();
        int fyear = database.getFirstModuleYear(modID);
        for (int curYear = fyear; curYear <= Calendar.getInstance().get(Calendar.YEAR); curYear++) {
            regPoint.add(new Point(curYear, database.getNumberStudents(modID, curYear)));
        }
        objOut = new ObjectOutputStream(sock.getOutputStream());
        sendObject(regPoint);
    }

    /**
     * resets this server socket to allow for another client to connect. Does not
     * destroy the server socket. Will close all streams, and then re-initialise them
     * and listen for another client, after which it will call the connection process.
     */
    public void resetSocket() {
        try {
            out.close();
            in.close();
            if (objOut != null) {
                objOut.close();
            }
            sock.close();
            System.out.printf("Server listening on port %d.\n", servSock.getLocalPort());
            sock = (SSLSocket) servSock.accept();
            System.out.printf("Client connected from %s.\n", sock.getInetAddress());
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            verifyClient();
        } catch (IOException ex) {
            System.out.println("Error while attempting to reset the socket.");
            ex.printStackTrace();
        }
    }

    /**
     * Destroys the socket entirely, killing the server.
     */
    public void killSocket() {
        try {
            out.close();
            in.close();
            if (objOut != null) {
                objOut.close();
            }
            sock.close();
            servSock.close();
        } catch (IOException ex) {
            System.out.println("IO exception while attempting to kill server.");
            ex.printStackTrace();
        }
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

    public void sendObject(Object o) throws IOException {
        objOut.writeObject(o);
        objOut.flush();
    }

    public String getStringMessage() throws IOException {
        return in.readLine();
    }

//    public void getSpam() throws IOException {
//        while (true) {
//            System.out.println(in.readLine());
//        }
//    }
    public static void main(String[] arstring) {
//        try {
        testserver t = new testserver(2000);
        t.initialiseSocket();
//            t.getSpam();
        t.verifyClient();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }




        //        try {
        //            System.setProperty("javax.net.ssl.keyStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
        //            System.setProperty("javax.net.ssl.keyStorePassword", "password");
        //            SSLServerSocket servSock = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(2000);
        //            System.out.printf("Server listening on port %d.\n", servSock.getLocalPort());
        //            SSLSocket sock = (SSLSocket) servSock.accept();
        //            System.out.printf("Client connected from %s.\n", sock.getInetAddress());
        //            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        //            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        //
        //            String greeting = in.readLine();
        //            if (greeting.equals("what ho, old chap?")) {
        //                System.out.println("Client handshake successful.");
        //                out.write("good day, what?");
        //                out.newLine();
        //                out.flush();
        //            } else {
        //                System.out.println("Client handshake failed.");
        //                out.write("you what?");
        //                out.newLine();
        //                out.flush();
        //            }
        //
        //            String auth = in.readLine();
        //            DBAccessor dbAccess = new DBAccessor("jdbc:postgresql://localhost:5432/uschool", "mich", "mich");
        //            //TODO password and user parsing here
        //            if (auth.equals("some tea, chap?")) {
        //                System.out.println("Client authentication successful.");
        //                System.out.println(dbAccess.getUserPrivileges("admin", "admin"));
        //                out.write("love some, old boy");
        //                out.newLine();
        //                out.flush();
        //            } else {
        //                System.out.println("Client authentication failed.");
        //                out.write("what is this muck");
        //                out.newLine();
        //                out.flush();
        //            }
        //
        //            String module = in.readLine();
        //            int mod = Integer.parseInt(module);
        //            ArrayList<Point> regPoint = new ArrayList<Point>();
        //            int fyear = dbAccess.getFirstModuleYear(mod);
        //            for (int curYear = fyear; curYear <= Calendar.getInstance().get(Calendar.YEAR); curYear++) {
        //                regPoint.add(new Point(curYear, dbAccess.getNumberStudents(mod, curYear)));
        //            }
        //
        //            ObjectOutputStream objOut = new ObjectOutputStream(sock.getOutputStream());
        //            objOut.writeObject(regPoint);
        //            objOut.flush();
        //
        //
        //
        //            out.close();
        //            in.close();
        //        } catch (Exception ex) {
        //        }
        //        }

    }
}
