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
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author michal
 */
public class DBClientSocket {

    SSLSocket sock;
    BufferedReader in;
    BufferedWriter out;
//    ObjectInputStream objIn;
//    ObjectOutputStream objOut;
    int port;
    boolean secure;
    String ip;

    public static void main(String[] args) {
        DBClientSocket s = new DBClientSocket("localhost", 2000);
        try {
            System.out.println(s.getStringMessage());
            s.sendString("YOUDIENOW");
        } catch (IOException ex) {
            System.out.println("NOOOOOO");
            ex.printStackTrace();
        }
    }

    public DBClientSocket(String ip, int port) {
        this.ip = ip;
        this.port = port;
        initSock();
    }

    private void initSock() {
        try {
            System.setProperty("javax.net.ssl.trustStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphserver");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            sock = (SSLSocket) sslsocketfactory.createSocket(ip, port);
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

    public void disconnect() {
        try {
            out.close();
//            objOut.close();
            in.close();
//            objIn.close();
            sock.close();
        } catch (IOException ex) {
            System.out.println("Error while disconnecting client socket.");
            ex.printStackTrace();
        }
    }

    public void sendString(String s) {
        try {
            out.write(s);
            out.newLine();
        } catch (IOException ex) {
            System.out.println("Error while attempting to send a string.");
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
