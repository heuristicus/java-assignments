/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import java.io.IOException;

/**
 *
 * @author michal
 */
public class ClientSocketListener implements Runnable{

    private final LibClientSocket sock;
    private final LibClient client;

    public ClientSocketListener(LibClientSocket sock, LibClient client) {
        this.sock = sock;
        this.client = client;
    }

    public void listen() {
        while (!Thread.interrupted()) {
            try {
                String input = (String) sock.readObject();
                if (input.equals("disconnecting")){
                    sock.disconnect(false);
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
