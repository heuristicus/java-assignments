/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author michal
 */
public class ClientSocket {

    ObjectInputStream objIn;
    BufferedWriter out;
    BufferedReader in;
    SSLSocket sock;
    SSLServerSocket servSock;
    int port;
    String host;

    public ClientSocket(int port, String host) {
        this.port = port;
        this.host = host;
        initialiseSocket();
    }

    /**
     * Initialises the socket and the streams that the object uses to
     * transfer data.
     */
    public void initialiseSocket() {
        try {
            System.setProperty("javax.net.ssl.trustStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");
            sock = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);
            System.out.printf("Connected to server at port %d.\n", sock.getPort());
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println("Streams successfully created.");
        } catch (UnknownHostException ex) {
            System.out.printf("Do not know host %s.\n", host);
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IO Exception while attempting to initialise client socket.");
            ex.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            out.close();
            in.close();
            if (objIn != null) {
                objIn.close();
            }
            sock.close();
            System.out.println("Client socket disconnected successfully.");
        } catch (IOException ex) {
            System.out.println("Failed to close the socket.");
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

    public String getStringMessage() throws IOException {
        return in.readLine();
    }

    /**
     * Gets an object from the object input stream. Initialises the object stream
     * here, because otherwise it seems like the streams freeze or something.
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object getObjectMessage() throws IOException, ClassNotFoundException {
        objIn = new ObjectInputStream(sock.getInputStream());
        return objIn.readObject();
    }
}
