/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author michal
 */
public class LibClient {

    private final String host;
    private final int port;
    LibClientSocket sock;
    BufferedReader cmd;

    public static void main(String[] args) {
        LibClient c = new LibClient("localhost", 2000);
        c.readCommands();
    }

    public LibClient(String host, int port) {
        this.host = host;
        this.port = port;
        cmd = new BufferedReader(new InputStreamReader(System.in));
        initSock();
    }

    private void initSock() {
        try {
            int userID = Integer.MIN_VALUE;
            boolean success = false;
            // get the user id - user must enter an integer.
            while (!success) {
                System.out.println("Enter your user ID.");
                System.out.print("$ ");
                String input = cmd.readLine();
                try {
                    userID = Integer.parseInt(input);
                } catch (NumberFormatException ex){
                    System.out.println("Your user ID should be an integer.");
                    continue;
                }
                success = true;
            }
            sock = new LibClientSocket(host, port, userID);
            sock.connect();
            sock.initListener(this);
        } catch (IOException ex) {
            System.out.println("Error while initialising socket.");
            ex.printStackTrace();
        }
    }

    private void readCommands() {
        boolean stop = false;
        while (!stop) {
            try {
                System.out.println("Enter a command. (list, reserve, loan, return, exit)");
                String command = cmd.readLine();
                if (command.equals("exit")){
                    sock.disconnect(true);
                    System.out.println("Disconnected from server.");
                    System.exit(0);
                }
            } catch (IOException ex) {
                System.out.println("IO exception while reading a command.");
                ex.printStackTrace();

            }
        }
    }
}
