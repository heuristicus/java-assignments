/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author michal
 */
public class LibServer {

    public static final String BOOK_FILE_LOC = "books_list.txt";
    int port;
    ServerSocket servSock;
    private final int maxConnections;
    ArrayList<Book> books;
    ServerConnHandler handler;
    Thread connectionHandler;
    RequestManager requestManager;

    public static void main(String[] args) {
        LibServer s = new LibServer(2000, 10);
    }

    public LibServer(int port, int maxConnections) {
        this.port = port;
        this.maxConnections = maxConnections;
        books = new ArrayList<Book>();
        getBookList();
        initServSock();
        initConnectionHandler();
        initRequestManager(books);
    }

    private void getBookList() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(BOOK_FILE_LOC)));
            String currentLine = read.readLine();
            int bookCount = 1;
            try {
                while (currentLine != null) {
                    String[] splitLine = currentLine.split("::");
                    books.add(new Book(splitLine[0], splitLine[1], bookCount));
                    currentLine = read.readLine();
                    bookCount++;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println(currentLine);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find book file.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IO exception while reading book file.");
            ex.printStackTrace();
        }
    }

    private void initRequestManager(ArrayList<Book> books) {
        requestManager = new RequestManager(books);
    }

    private void initServSock() {
        try {
            servSock = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Error while initialising server socket.");
            ex.printStackTrace();
        }
    }

    private void initConnectionHandler() {
        handler = new ServerConnHandler(maxConnections, this);
        connectionHandler = new Thread(handler);
        connectionHandler.start();
        System.out.println("Connection handler initialised.");
    }

    public ServerSocket getServerSocket() {
        return servSock;
    }

    public void shutdown() {
        connectionHandler.interrupt(); // stop accepting connections
        try {
            servSock.close();
            System.out.println("Server shut down.");
        } catch (IOException ex) {
            System.out.println("Error while attempting to close server socket.");
            ex.printStackTrace();
        }
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

}
