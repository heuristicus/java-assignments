/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author michal
 */
public class DBServerSocket extends DBSocket {

    SSLServerSocket servSock;

    DBServerSocket(int port, boolean secure) {
        super(port, secure);
        createSocket();
    }

    // <editor-fold defaultstate="collapsed" desc="Connection methods.">
    public void createSocket() {
        try {
            if (secure) {
                System.setProperty("javax.net.ssl.keyStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
                System.setProperty("javax.net.ssl.keyStorePassword", "password");
                SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
                servSock = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);
//                servSock = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);
            } else {
                System.out.println("Insecure socket unavailable.");
//                servSock = new ServerSocket(port);
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
            super.sock = (SSLSocket) servSock.accept();
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
    public void stop() {
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
