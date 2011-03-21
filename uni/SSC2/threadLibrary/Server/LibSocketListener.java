/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;

/**
 *
 * @author michal
 */
public class LibSocketListener implements Runnable {
    
    private final LibServSocket sock;
    private final LibServer server;

    public LibSocketListener(LibServSocket sock, LibServer server) {
        this.sock = sock;
        this.server = server;
    }

    public void listen() {
        System.out.println("listen?");
        while (!Thread.interrupted()) {
            System.out.println("listening");
            try {
                String input = (String) sock.readObject();
                System.out.println(input);
                if (input.equals("disconnecting")){
                    server.removeConnection(sock.getUserID());
                }
            } catch (IOException ex) {
                System.out.println("IO exception while reading client request.");
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                System.out.println("Could not find class while reading client request.");
                ex.printStackTrace();
            }
        }
    }

    public void run() {
        listen();
    }
}
