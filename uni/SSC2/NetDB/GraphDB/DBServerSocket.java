/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author michal
 */
public class DBServerSocket extends DBSocket{

    ServerSocket servSock;

    DBServerSocket(int port, boolean secure) {
        super(port, secure);
    }

    // <editor-fold defaultstate="collapsed" desc="Connection methods.">

    public void createSocket() {
        try {
            if (secure) {
                servSock = SSLServerSocketFactory.getDefault().createServerSocket(port);
            } else {
                servSock = new ServerSocket(port);
            }
        } catch (IOException ex) {
            System.out.println("Exception while attempting to create socket.");
            ex.printStackTrace();
        }
    }

    /**
     * Listens for a connection from a client. Intialises input and output streams when this happens.
     */
    public void listen() {
        try {
            System.out.printf("Server listening on port %d\n", port);
            super.sock = servSock.accept();
            System.out.printf("Server connected to client at %s\n", sock.getInetAddress());
            super.out = new PrintStream(sock.getOutputStream(), true);
            super.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//            super.objOut = new ObjectOutputStream(sock.getOutputStream());
//            super.objIn = new ObjectInputStream(sock.getInputStream());
        } catch (IOException ex) {
            System.out.println("Exception while listening for a connection from a client.");
            ex.printStackTrace();
        }
    }

    /**
     * Closes all connections made with a client, and starts listening for other connections.
     */
    public void disconnect() {
        try {
            out.close();
            objOut.close();
            in.close();
            sock.close();
            listen();
        } catch (IOException ex) {
            System.out.println("Error while closing connection with client.");
            ex.printStackTrace();
        }
    }

    /**
     * Stops this socket. Used when you want to take the server down.
     */
    public void stop(){
        try {
            out.close();
            objOut.close();
            in.close();
            sock.close();
            servSock.close();
        } catch (IOException ex) {
            System.out.println("Error while disconnecting the server socket.");
            ex.printStackTrace();
        }

    }

    // </editor-fold>

}
