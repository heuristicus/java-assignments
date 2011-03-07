/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.awt.Point;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michal
 */
public class Server {

    ServerSocket sock;
    int port;
    DBAccessor database;

    public Server(int port) {
        this.port = port;
        database = new DBAccessor("jdbc:postgresql://localhost:5432/uschool", "mich", "mich");
        sock = new ServerSocket(port);
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
            waitForRequest();
        } catch (ActionFailedException ex) {
            System.out.println("Action failed while performing initial interactions with the client.");
            System.out.println(ex.getMessage());
            sock.resetSocket();
        } catch (IOException ex) {
            System.out.println("IO Exception while doing intial interaction with client.");
            ex.printStackTrace();
            sock.resetSocket();
        }
    }

    /**
     * Server waits for the client to send something, and if it requests a new
     * data set, it will respond with that data.
     */
    public void waitForRequest() {
        try {
            System.out.println("Waiting for a request.");
            String request = sock.getStringMessage();
            while (true) {
                // FIXME this likely doesn't work - have to have some way of knowing
                // when the client drops the connection.
                if (request != null) {
                    if (request.equals("regpointreq")) {
                        /*
                         * check if the client requested some new registration points
                         * if it did, send a ready response, and then get ready to receive
                         * data
                         */
                        System.out.println("Received a regpoint request from the client.");
                        sock.sendString("ready");
                        processRegPointRequest();
                    } else if (request.equals("disconnect")) {
                        System.out.println("Received a disconnect request from client.");
                        // client asked for a disconnect, so oblige and then wait for another connection.
                        sock.sendString("disconnecting");
                        break;
                    }
                    request = sock.getStringMessage();
                }
            }
        } catch (IOException ex) {
            System.out.println("IO Error while waiting for a request.");
            ex.printStackTrace();
        }
        sock.resetSocket();
        verifyClient();
    }

    public void handshake() throws ActionFailedException, IOException {
        String greeting = sock.getStringMessage();
        if (!greeting.equals("graphclient")) {
            throw new ActionFailedException("Received incorrect message from client.");
        }
        sock.sendString("graphserver");
    }

    /**
     * Authenticates the client with the username and password.
     * @throws IOException
     * @throws ActionFailedException
     */
    public void authenticate() throws IOException, ActionFailedException {
        try {
            String userDetails = sock.getStringMessage();
            String[] spDet = userDetails.split(":");
//            System.out.println(spDet[0] + " " + spDet[1]);
            String role = database.getUserPrivileges(spDet[0], spDet[1]);
            System.out.println(role);
            if (!role.equals("administrator")) {
                sock.sendString("authfailed");
                String reply = sock.getStringMessage();
                if (reply.equals("reauth")) {
                    authenticate();
                } else {
                    database.disconnect();
                    throw new ActionFailedException("User quit authentication process.");
                }
            }
            sock.sendString("authsuccess");
        } catch (SQLException ex) {
            System.out.println("Failed to get user details from the database.");
            ex.printStackTrace();
        }
    }

    /**
     * Processes a request for a set of registration points. This will read a
     * module ID from the client, and then grab the corresponding data from
     * the database, and return it to the client as an arraylist.
     * @throws IOException
     */
    public void processRegPointRequest() throws IOException {
        int modID = Integer.parseInt(sock.getStringMessage());
        System.out.println("Modid " + modID);
        sock.sendObject(getRegPoints(modID));
    }

    private ArrayList<Point> getRegPoints(int modID) {
        ArrayList<Point> regPoint = new ArrayList<Point>();
        int fyear = database.getFirstModuleYear(modID);
        for (int curYear = fyear; curYear <= Calendar.getInstance().get(Calendar.YEAR); curYear++) {
            regPoint.add(new Point(curYear, database.getNumberStudents(modID, curYear)));
        }
        return regPoint;
    }

    public static void main(String[] arstring) {
        Server t = new Server(2000);
        t.verifyClient();
    }
}
