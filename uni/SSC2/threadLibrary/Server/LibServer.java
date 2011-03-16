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
    ArrayList<Book> books;
    Map connections;

    public LibServer(int port, int maxConnections, ArrayList<Book> books){
        this.port = port;
        this.books = books;
        connections = new HashMap<String, LibServSocket>();
        initServSock();
    }

    private void initServSock(){
        try {
            servSock = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Error while initialising server socket.");
            ex.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return servSock;
    }

    public void addConnection(LibServSocket sock){
        connections.put(sock.getConnectionName(), sock);
    }

    public void shutdown(){
        Set connKeys = connections.keySet();
        for (Object connection : connKeys) {
            LibServSocket currConn = (LibServSocket) connections.get(connection);
            currConn.disconnect();
        }
        try {
            servSock.close();
        } catch (IOException ex) {
            System.out.println("Error while attempting to close server socket.");
            ex.printStackTrace();
        }
    }



}
