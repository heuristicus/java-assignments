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
            readModuleID();
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

    public void readModuleID() throws IOException {
        int modID = Integer.parseInt(sock.getStringMessage());
        System.out.println("Modid " + modID);
        ArrayList<Point> regPoint = new ArrayList<Point>();
        int fyear = database.getFirstModuleYear(modID);
        for (int curYear = fyear; curYear <= Calendar.getInstance().get(Calendar.YEAR); curYear++) {
            regPoint.add(new Point(curYear, database.getNumberStudents(modID, curYear)));
        }
        sock.sendObject(regPoint);
    }

    public static void main(String[] arstring) {
        Server t = new Server(2000);
        t.verifyClient();
    }
}
