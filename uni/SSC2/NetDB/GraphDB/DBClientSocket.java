/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author michal
 */
public class DBClientSocket extends DBSocket {

    String ip;

    public DBClientSocket(String ip, int port, boolean secure) {
        super(port, secure);
        this.ip = ip;
        createSocket();
    }

    @Override
    public void createSocket() {
        try {
            if (secure) {
                System.setProperty("javax.net.ssl.trustStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphserver");
                System.setProperty("javax.net.ssl.trustStorePassword", "password");
                SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                super.sock = (SSLSocket) sslsocketfactory.createSocket("localhost", port);
//                super.sock = (SSLSocket) SSLSocketFactory.getDefault().createSocket(ip, port);
            } else {
                System.out.println("Insecure socket unavailable.");
//                super.sock = new Socket(ip, port);

            }
            openStreams();
        } catch (UnknownHostException ex) {
            System.out.println("Unknown host while creating client socket.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IO Exception while creating client socket.");
            ex.printStackTrace();
        }
    }

    private void openStreams() {
        try {
            super.out = new PrintStream(sock.getOutputStream(), true);
            super.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//            super.objOut = new ObjectOutputStream(sock.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error while attempting to open socket streams.");
            ex.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            out.close();
            objOut.close();
            in.close();
            objIn.close();
            sock.close();
        } catch (IOException ex) {
            System.out.println("Error while disconnecting client socket.");
            ex.printStackTrace();
        }
    }
}
