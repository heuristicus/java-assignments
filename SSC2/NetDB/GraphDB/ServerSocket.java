/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author michal
 */
public class ServerSocket {

    ObjectOutputStream objOut;
    BufferedWriter out;
    BufferedReader in;
    SSLSocket sock;
    SSLServerSocket servSock;

    int port;

    public ServerSocket(int port) {
        this.port = port;
        initialiseSocket();
    }


    /**
     * gets a new SSL socket for the server, and then listens for a connection.
     * Once a connection is established, streams are created.
     */
    public void initialiseSocket() {
        try {
            System.setProperty("javax.net.ssl.keyStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");
            servSock = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);
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
     * resets this server socket to allow for another client to connect. Does not
     * destroy the server socket. This will close all streams, listen for a new
     * connection, and then recreate all the streams.
     */
    public void resetSocket() {
        try {
            out.close();
            in.close();
            if (objOut != null) {
                objOut.close();
            }
            sock.close();
            System.out.println("Server socket streams reset.");
            System.out.printf("Server listening on port %d.\n", servSock.getLocalPort());
            sock = (SSLSocket) servSock.accept();
            System.out.printf("Client connected from %s.\n", sock.getInetAddress());
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
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
            System.out.println("Server socket killed successfully.");
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

    /**
     * Sends an object to the output stream. Initialises the output stream here
     * because this was causing problems before.
     * @param o
     * @throws IOException
     */
    public void sendObject(Object o) throws IOException {
        objOut = new ObjectOutputStream(sock.getOutputStream());
        objOut.writeObject(o);
        objOut.flush();
    }

    public String getStringMessage() throws IOException {
        return in.readLine();
    }
    
}
