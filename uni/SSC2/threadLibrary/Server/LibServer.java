/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author michal
 */
public class LibServer {

    int port;
    ServerSocket servSock;
    private final int maxConnections;
    ArrayList<Book> books;
    Map connections;
    ServerConnHandler handler;
    Thread connectionHandler;

    public static void main(String[] args) {
        LibServer s = new LibServer(2000, 10, null);
    }

    public LibServer(int port, int maxConnections, ArrayList<Book> books){
        this.port = port;
        this.maxConnections = maxConnections;
        this.books = books;
        connections = new HashMap<Integer, LibServSocket>();
        initServSock();
        initConnectionHandler();
    }

    private void initServSock(){
        try {
            servSock = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Error while initialising server socket.");
            ex.printStackTrace();
        }
    }

    private void initConnectionHandler(){
        handler = new ServerConnHandler(maxConnections, this);
        connectionHandler = new Thread(handler);
        connectionHandler.start();
        System.out.println("Connection handler initialised.");
    }

    public ServerSocket getServerSocket() {
        return servSock;
    }

    public void addConnection(LibServSocket sock){
        connections.put(sock.getUserID(), sock);
        System.out.printf("Added connection with user ID %d to server.\n", sock.getUserID());
        System.out.println(connections);
    }

    public void removeConnection(int userID){
        LibServSocket conn = (LibServSocket) connections.get(userID);
        conn.disconnect(false);
        connections.remove(userID);
        System.out.printf("Connection with user ID %d terminated.\n", userID);
    }

    public void shutdown(){
        connectionHandler.interrupt(); // stop accepting connections
        Set connKeys = connections.keySet();
        for (Object connection : connKeys) {
            LibServSocket currConn = (LibServSocket) connections.get(connection);
            currConn.disconnect(true);
        }
        connections.clear();
        try {
            servSock.close();
            System.out.println("Server shut down.");
        } catch (IOException ex) {
            System.out.println("Error while attempting to close server socket.");
            ex.printStackTrace();
        }
    }



}
