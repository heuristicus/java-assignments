/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author michal
 */
public class LibServSocket {

    int userID;
    private final Socket sock;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;
    private LibSocketListener listener;

    public LibServSocket(Socket sock, LibServer server) {
        this.sock = sock;
        getStreams();
        initConnectionName();
        initListener(server);
    }

    private void getStreams() {
        try {
            objOut = new ObjectOutputStream(sock.getOutputStream());
            objIn = new ObjectInputStream(sock.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error while attempting to initialise socket streams.");
            ex.printStackTrace();
        }
    }

    /**
     * Gets a name for this socket from the client.
     */
    private void initConnectionName() {
        try {
            userID = (Integer) readObject();
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found while getting connection name.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IO exception while trying to get a connection name.");
            ex.printStackTrace();
        }
    }

    public int getUserID() {
        return userID;
    }

    private void initListener(LibServer server) {
        listener = new LibSocketListener(this, server);
    }

    public void sendObject(Object o) throws IOException {
        objOut.writeObject(o);
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return objIn.readObject();
    }

    public void disconnect() {
        try {
            sendObject("disconnecting");
            objOut.close();
            objIn.close();
            sock.close();
        } catch (IOException ex) {
            System.out.printf("io exception while attempting to disconnect user ID %d.\n", userID);
            ex.printStackTrace();
        }
    }
}
