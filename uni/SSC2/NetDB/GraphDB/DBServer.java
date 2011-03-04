/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

/**
 *
 * @author michal
 */
public class DBServer {

    DBAccessor dbAccess;
    DBServerSocket sock;
    int port;
    boolean secure;

    public static void main(String[] args) {
        DBServer s = new DBServer();
    }

    public DBServer() {
        this(findUsablePort(), false);
    }

    public DBServer(int port, boolean secure) {
        this.port = port;
        this.secure = secure;
        //*************CHANGE THE BELOW TO CONNECT TO ANOTHER DB****************
        dbAccess = new DBAccessor("jdbc:postgresql://localhost:5432/uschool", "mich", "mich");
        sock = new DBServerSocket(port, secure);
        start();
    }

    private void start() {
        sock.listen();
        handshake();
    }

    private void handshake() {
        try {
            String greeting = sock.getStringMessage();
            System.out.println(greeting);
            while(true) {
                System.out.println(sock.getStringMessage());
            }
//            System.out.println("received handshake message");
//            if (greeting.equals("graphclient")) {
//                System.out.println("YEEEASD");
//                sock.sendString("graphserver");
//                authenticate();
//            } else {
//                System.out.println("received wrong message from client");
//                sock.disconnect();
//            }
        } catch (IOException ex) {
            System.out.println("Exception while attempting to handshake with client.");
        }
    }

    /**
     * Authenticates the client that has connected. If the username and password
     * that are received do not have high enough privileges or are not valid,
     * the server sends an authfailed message, and disconnects.
     */
    private void authenticate() {
        try {
            String userPass = sock.getStringMessage();
            // Protocol is to split the username and password with :, so
            // it should look like username:password
            String[] userDetails = userPass.split(":");
            try {
                if (userDetails[0].equals("dc") && userDetails.length == 1) {
                    // disconnect the socket, exit the method, and listen for a new connection.
                    System.out.println("Client asked to disconnect.");
                    sock.disconnect();
                    start();
                    // FIXME ***ERROR POTENTIAL HERE***
                    return;
                }
                String priv = dbAccess.getUserPrivileges(userDetails[0], userDetails[1]);
//                String priv = dbAccess.getUserPrivileges("admin", "admin");
                System.out.println(priv);
                if (!priv.equals("administrator")) {
                    // inform client of failed authentication, and see their response.
                    sock.sendString("authfailed");
                    System.out.println("Client authentication failed.");
                    String reauth = sock.getStringMessage();
                    if (reauth.equals("reauth")) {
                        System.out.println("Client requested re-authentication.");
                        // client wants to try authenticating again.
                        authenticate();
                    } else if (reauth.equals("noreauth")) {
                        System.out.println("Client did not request re-authentication.");
                        // no reauth. disconnect from this client and wait for another.
                        sock.disconnect();
                        start();
                        return;
                    }
                } else {
                    sock.sendString("authsuccess");
                    System.out.println("Authentication successful.");
                    receiveData();
                }
            } catch (SQLException ex) {
                System.out.println("User login failed.");
                ex.printStackTrace();
                sock.disconnect();
            }
        } catch (IOException ex) {
            System.out.println("Error while authenticating client.");
            ex.printStackTrace();
        }
    }

    /**
     * Receives the module ID from the client and goes away and queries the
     * database for the data required for the response.
     */
    private void receiveData() {
    }

    private void waitForMessage() {
        while (true) {
            try {
                System.out.println(sock.getStringMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Utilities">
    /**
     * Try and find a port that is not in use within the specified range of ports.
     * @param rangeStart
     * @param rangeEnd
     * @return
     */
    private static int findUsablePort() {
        int rangeStart = 2000;
        int rangeEnd = 65535;
        int usablePort = Integer.MIN_VALUE;
        ServerSocket testSock = null;
        for (int i = rangeStart; i <= rangeEnd; i++) {
            try {
                testSock = new ServerSocket(i);
                usablePort = i;
                break;
            } catch (IOException ex) {
            } finally {
                try {
                    testSock.close();
                } catch (IOException ex) {
                    System.err.printf("Unable to close the test socket at port %d\n", i);
                } catch (Exception ex) {
                    System.err.println("Some other error ocurred while trying to close the test socket. "
                            + "The connection was probably null. This shouldn't be a problem.");
                }
            }
        }
        return usablePort;
    }
    // </editor-fold>
}
