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

    public LibSocketListener(LibServSocket sock) {
        this.sock = sock;
    }

    public void listen() {
        while (!Thread.interrupted()) {
            try {
                String input = (String) sock.readObject();
                System.out.println(input);
                if (input.equals("disconnecting")){
                    sock.disconnect(false);
                } else if(input.equals("list")){
                    sock.listBooks();
                } else if (input.equals("reserve")){
                    sock.reserveBook();
                } else if (input.equals("loan")){
                    sock.loanBook();
                } else if (input.equals("return")){
                    sock.returnBook();
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
