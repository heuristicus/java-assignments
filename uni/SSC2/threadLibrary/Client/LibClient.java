/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;

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
        while (true) {
            c.getBookList();
        }
//        c.readCommands();
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
                } catch (NumberFormatException ex) {
                    System.out.println("Your user ID should be an integer.");
                    continue;
                }
                success = true;
            }
            sock = new LibClientSocket(host, port, userID);
            sock.connect();
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
                System.out.print("$ ");
                String command = cmd.readLine();
                if (command.equals("exit")) {
                    sock.disconnect(true);
                    System.out.println("Disconnected from server.");
                    System.exit(0);
                } else if (command.equals("list")) {
                    getBookList();
                } else if (command.equals("reserve")) {
                    System.out.println("Enter the book ID.");
                    System.out.print("$ ");
                    int bookID = Integer.parseInt(cmd.readLine());
                    System.out.println(bookID);
                    doReserve(bookID);
                } else if (command.equals("loan")) {
                    System.out.println("Enter the book ID.");
                    System.out.print("$ ");
                    int bookID = Integer.parseInt(cmd.readLine());
                    doLoan(bookID);
                } else if (command.equals("return")) {
                    System.out.println("Enter the book ID.");
                    System.out.print("$ ");
                    int bookID = Integer.parseInt(cmd.readLine());
                    doReturn(bookID);
                } else {
                    System.out.println("Unrecognised command.");
                }

            } catch (IOException ex) {
                System.out.println("IO exception while reading a command.");
                ex.printStackTrace();
            }
        }

    }

    public void getBookList() {
        try {
            sock.sendObject("list");
            String list = (String) sock.readObject();
            System.out.println(list);
        } catch (SocketException ex) {
            System.out.println("Server no longer exists. Disconnecting.");
            sock.disconnect(false);
            System.exit(0);
        } catch (ClassNotFoundException ex) {
            System.out.println("Could not find class while getting book list.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Failed to get book list.");
            ex.printStackTrace();
        }
    }

    private void doReserve(int bookID) {
        try {
            sock.sendObject("reserve");
            sock.sendObject(bookID);
            String result = (String) sock.readObject();
            System.out.println(result);
        } catch (SocketException ex) {
            System.out.println("Server no longer exists. Disconnecting.");
            sock.disconnect(false);
            System.exit(0);
        } catch (ClassNotFoundException ex) {
            System.out.println("class not found");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IO exception while attempting to reserve book.");
            ex.printStackTrace();
        }
    }

    private void doReturn(int bookID) {
        try {
            sock.sendObject("return");
            sock.sendObject(bookID);
            String result = (String) sock.readObject();
            System.out.println(result);
        } catch (SocketException ex) {
            System.out.println("Server no longer exists. Disconnecting.");
            sock.disconnect(false);
            System.exit(0);
        } catch (ClassNotFoundException ex) {
            System.out.println("class not found");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IO exception while attempting to reserve book.");
            ex.printStackTrace();
        }
    }

    private void doLoan(int bookID) {
        try {
            sock.sendObject("loan");
            sock.sendObject(bookID);
            String result = (String) sock.readObject();
            System.out.println(result);
        } catch (SocketException ex) {
            System.out.println("Server no longer exists. Disconnecting.");
            sock.disconnect(false);
            System.exit(0);
        } catch (ClassNotFoundException ex) {
            System.out.println("class not found");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IO exception while attempting to reserve book.");
            ex.printStackTrace();

        }
    }
}
