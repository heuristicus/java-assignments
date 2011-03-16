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

    String connectionName;
    private final Socket sock;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;
    private LibSocketListener listener;

    public LibServSocket(Socket sock, LibServer server, String connectionName) {
        this.sock = sock;
        this.connectionName = connectionName;
        getStreams();
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

    public String getConnectionName() {
        return connectionName;
    }

    private void initListener(LibServer server) {
        listener = new LibSocketListener(objOut, server);
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
            System.out.println("io exception while attempting to disconnect " + connectionName);
            ex.printStackTrace();
        }

    }
}
