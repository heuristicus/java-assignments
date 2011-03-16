/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

/**
 *
 * @author michal
 */
public class LibClient {

    private final String host;
    private final int port;
    LibClientSocket sock;


    public static void main(String[] args) {
        LibClient c = new LibClient("localhost", 2000);
    }
    

    public LibClient(String host, int port) {
        this.host = host;
        this.port = port;
        initSock();
    }

    private void initSock(){
        sock = new LibClientSocket(host, port);
        sock.connect();
    }



}
