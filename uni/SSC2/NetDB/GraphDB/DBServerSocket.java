/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author michal
 */
public class DBServerSocket {

    SSLSocket sock;
    BufferedReader in;
    BufferedWriter out;
//    ObjectInputStream objIn;
//    ObjectOutputStream objOut;
    int port;
    boolean secure;
    SSLServerSocket servSock;

    DBServerSocket(int port) {
        this.port = port;
        initSock();
    }

    private void initSock() {
        try {
            System.setProperty("javax.net.ssl.keyStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            servSock = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);
            sock = (SSLSocket) servSock.accept();
            System.out.println("Connected to a server.");
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println("Sucessfully initialised input and output streams.");
        } catch (UnknownHostException ex) {
            System.out.println("Could not find host.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IO Exception while initalising the socket.");
            ex.printStackTrace();
        }
    }

    /**
     * Closes all connections made with a client, and starts listening for other connections.
     */
    public void disconnect() {
        try {
            out.close();
//            objOut.close();
            in.close();
//            objIn.close()
            sock.close();
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
//            objOut.close();
            in.close();
//            objIn.close();
            sock.close();
            servSock.close();
        } catch (IOException ex) {
            System.out.println("Error while disconnecting the server socket.");
            ex.printStackTrace();
        }

    }
    // </editor-fold>

    public void sendString(String s) {
        try {
            out.write(s);
            out.newLine();
        } catch (IOException ex) {
            System.out.println("Error while attempting to write a string to output.");
            ex.printStackTrace();
        }
    }

    public void sendObject(Object o) {
    }

    public String getStringMessage() throws IOException {
        StringBuilder build = new StringBuilder();
        String inLine = in.readLine();
        System.out.println(inLine);
        while (!inLine.equals("")) {
            build.append(inLine);
            inLine = in.readLine();
        }
        return build.toString();
    }
    
//    public Object getObjectMessage() throws IOException, ClassNotFoundException {
//        return objIn.readObject();
//    }
}
