/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author michal
 */
public class ServerConnHandler implements Runnable{

    LibServer server;
    int maxConnections;
    int currentConnections;

    public ServerConnHandler(int maxConnections, LibServer server){
        this.server = server;
        this.maxConnections = maxConnections;
        currentConnections = 0;
    }

    public void listenForConnections(){
        while (!Thread.interrupted() && currentConnections <= maxConnections){
            try {
                System.out.println("Listening for connection on port " + server.servSock.getLocalPort());
                Socket sock = server.getServerSocket().accept();
                System.out.println("Got a connection from client at " + sock.getInetAddress());
                LibServSocket s = new LibServSocket(sock, server);
                server.addConnection(s);
            } catch (IOException ex) {
                System.out.println("Error while listening for connections.");
                ex.printStackTrace();
            }
        }
    }

    public void run() {
        listenForConnections();
    }

}
