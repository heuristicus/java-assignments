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
    int userID;
    ClientSocketListener listener;
    Thread listenerThread;

    LibClientSocket(String host, int port, int userID) {
        this.host = host;
        this.port = port;
        this.userID = userID;
    }

    public void connect() {
        try {
            sock = new Socket(host, port);
            objOut = new ObjectOutputStream(sock.getOutputStream());
            objIn = new ObjectInputStream(sock.getInputStream());
            System.out.println("Socket successfully initialised.");
            sendObject(userID);
        } catch (IOException ex) {
            System.out.println("Error while initialising client socket.");
            ex.printStackTrace();
        }
    }

//    public void initListener(LibClient client) {
//        listener = new ClientSocketListener(this, client);
//        listenerThread = new Thread(listener);
//        listenerThread.start();
//    }

    public void disconnect(boolean sendMessage) {
        try {
            if (sendMessage == true) {
                sendObject("disconnecting");
            }
//            listenerThread.interrupt();
            objOut.close();
            objIn.close();
            sock.close();
        } catch (IOException ex) {
            System.out.printf("io exception while attempting to disconnect from server.");
            ex.printStackTrace();
        }
    }

    public void sendObject(Object o) throws IOException {
        objOut.writeObject(o);
        objOut.flush();
        objOut.reset();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return objIn.readObject();
    }
}
