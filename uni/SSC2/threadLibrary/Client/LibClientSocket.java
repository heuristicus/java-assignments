/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author michal
 */
public class LibClientSocket {

    private final String host;
    private final int port;
    ObjectInputStream objIn;
    ObjectOutputStream objOut;
    Socket sock;

    LibClientSocket(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect(){
        try {
            sock = new Socket(host, port);
            objOut = new ObjectOutputStream(sock.getOutputStream());
            objIn = new ObjectInputStream(sock.getInputStream());
            System.out.println("Socket successfully initialised.");
        } catch (IOException ex) {
            System.out.println("Error while initialising client socket.");
            ex.printStackTrace();
        }
    }

}
