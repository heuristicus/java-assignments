/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author michal
 */
public class DBClient {

    DBClientSocket sock;

    public static void main(String[] args) {
        DBClient c = new DBClient("127.0.0.1", 2000, true);
        c.getRegistrationPoints();
    }

    public DBClient(String ip, int port, boolean secure) {
        sock = new DBClientSocket(ip, port, secure);
    }

    public void getRegistrationPoints() {
        try {
            handshake();
            System.out.println("Handshaking complete.");
            authenticate();
            System.out.println("Authentication complete.");
        } catch (DisconnectRequestException ex) {
            System.out.println("error getting registration.");
            ex.printStackTrace();
        }

    }

    /**
     * Send a message to the server, to check that it provides the service you want.
     */
    private void handshake() throws DisconnectRequestException {
        try {
            sock.sendString("graphclient");
            System.out.println("sent handshake message.");
            String reply = sock.getStringMessage();
            System.out.println("received server reply.");
            if (!reply.equals("graphserver")) {
                sock.disconnect();
                throw new DisconnectRequestException("Server does not handle requests.");
            }
        } catch (IOException ex) {
            System.out.println("Error while handshaking with server.");
            ex.printStackTrace();
        }
    }

    /**
     * Authenticate with the server, reading in username and password data from
     * the commandline. Can also disconnect at this point.
     */
    private void authenticate() throws DisconnectRequestException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the user to connect with, or enter quit to stop.");
            String user = in.readLine();
            if (user.equals("quit")) { // user wants to quit
                // send a disconnect message and then disconnect the socket.
                sock.sendString("dc");
                sock.disconnect();
                throw new DisconnectRequestException("User sent quit message.");
            } else { // authenticate the user details
                System.out.println("Enter the password.");
                String password = in.readLine();
                // combine the user and password according to protocol.
                String combo = user + ":" + password;
                sock.sendString(combo);
                String authResp = sock.getStringMessage(); // get server response
                if (authResp.equals("authfailed")) {
                    // failed auth, see if you want to try again
                    System.out.println("Authentication failed. Try again? (y/n)");
                    String again = in.readLine();
                    if (again.equals("y")) {
                        // send a message to redo authentication
                        sock.sendString("reauth");
                        authenticate();
                    } else {
                        // send a message to disconnect.
                        sock.sendString("noreauth");
                        System.out.println("Ok, disconnecting.");
                        sock.disconnect();
                        throw new DisconnectRequestException("Client requested disconnect.");
                    }
                } else if (authResp.equals("authsuccess")) {
                    return;
                } else {
                    throw new DisconnectRequestException("Some weird stuff happened. Server sent a strange authentication message.");
                }
            }
        } catch (IOException ex) {
            System.out.println("IO exception while authenticating with server.");
            ex.printStackTrace();
        }
    }

    /**
     * Sends a module ID to the
     */
    private void sendModID() {
    }
}
