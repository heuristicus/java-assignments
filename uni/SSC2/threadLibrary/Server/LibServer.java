/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author michal
 */
public class LibServer {

    int port;
    ServerSocket servSock;
    ArrayList<LibServSocket> connections;

    public LibServer(int port, int maxConnections){
        this.port = port;
        connections = new ArrayList<LibServSocket>();
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

    public void shutdown(){
        for (LibServSocket libServSocket : connections) {
            libServSocket.disconnect();
        }
        try {
            servSock.close();
        } catch (IOException ex) {
            System.out.println("Error while attempting to close server socket.");
            ex.printStackTrace();
        }
    }



}
