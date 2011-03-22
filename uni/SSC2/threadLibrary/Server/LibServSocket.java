/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    RequestManager requestManager;
    Thread listenerThread;
    Thread requestThread;

    public LibServSocket(Socket sock, RequestManager requestManager) {
        this.sock = sock;
        this.requestManager = requestManager;
        getStreams();
        initConnectionName();
        initListener();
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

    private void initListener() {
        listener = new LibSocketListener(this);
        listenerThread = new Thread(listener);
        listenerThread.start();
    }

    public void sendObject(Object o) throws IOException {
        objOut.writeObject(o);
        objOut.flush();
        objOut.reset();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return objIn.readObject();
    }

    public void disconnect(boolean sendMessage) {
        try {
            if (sendMessage == true) {
                sendObject("disconnecting");
            }
            listenerThread.interrupt();
            objOut.close();
            objIn.close();
            sock.close();
        } catch (IOException ex) {
            System.out.printf("io exception while attempting to disconnect user ID %d.\n", userID);
            ex.printStackTrace();
        }
    }

    public void listBooks() {
        try {
            sendObject(requestManager.getBookList());
        } catch (IOException ex) {
            System.out.println("IO exception while attempting to list books.");
            ex.printStackTrace();
        }
    }

    public void reserveBook() {
        try {
            int bookID = (Integer) readObject();
            sendObject(requestManager.reserveBook(bookID, getUserID()));
        } catch (IOException ex) {
            System.out.println("IO exception while reserving book.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found.");
            ex.printStackTrace();
        }
    }

    public void loanBook() {
        try {
            int bookID = (Integer) readObject();
            sendObject(requestManager.loanBook(bookID, getUserID()));
        } catch (IOException ex) {
            System.out.println("IO exception while loaning book.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found.");
            ex.printStackTrace();
        }
    }

    public void returnBook() {
        try {
            int bookID = (Integer) readObject();
        } catch (IOException ex) {
            System.out.println("IO exception while returning book.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found.");
            ex.printStackTrace();
        }
    }
}
