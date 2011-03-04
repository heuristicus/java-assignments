/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author michal
 */
public class testserver {

    SSLSocket sock;
    SSLServerSocket servSock;
    BufferedWriter out;
    BufferedReader in;
    ObjectInputStream objIn;
    int port;

    public testserver(int port) {
        this.port = port;
    }

    public void initialiseSocket() {
        try {
            System.setProperty("javax.net.ssl.keyStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");
            servSock = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(2000);
            System.out.printf("Server listening on port %d.\n", servSock.getLocalPort());
            sock = (SSLSocket) servSock.accept();
            System.out.printf("Client connected from %s.\n", sock.getInetAddress());
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (IOException ex) {
            System.out.printf("IO Exception while attempting to listen at port %d.\n", port);
            ex.printStackTrace();
        }
    }

    public void handshake() {
        try {
            System.out.println("handshaking");
            String greeting = in.readLine();
            System.out.println(greeting);
            sendString("no");
        } catch (IOException ex) {
        }
    }

    public void authenticate() {
    }

    /**
     * Sends a single string to the server. The string should not contain new
     * line characters.
     * @param s
     * @throws IOException
     */
    public void sendString(String s) throws IOException {
        out.write(s);
        out.newLine();
        out.flush();
    }

    public String getStringMessage() throws IOException {
        return in.readLine();
    }

//    public void getSpam() throws IOException {
//        while (true) {
//            System.out.println(in.readLine());
//        }
//    }

    public static void main(String[] arstring) {
//        try {
            testserver t = new testserver(2000);
            t.initialiseSocket();
//            t.getSpam();
            t.handshake();
            t.authenticate();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }




        //        try {
        //            System.setProperty("javax.net.ssl.keyStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
        //            System.setProperty("javax.net.ssl.keyStorePassword", "password");
        //            SSLServerSocket servSock = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(2000);
        //            System.out.printf("Server listening on port %d.\n", servSock.getLocalPort());
        //            SSLSocket sock = (SSLSocket) servSock.accept();
        //            System.out.printf("Client connected from %s.\n", sock.getInetAddress());
        //            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        //            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        //
        //            String greeting = in.readLine();
        //            if (greeting.equals("what ho, old chap?")) {
        //                System.out.println("Client handshake successful.");
        //                out.write("good day, what?");
        //                out.newLine();
        //                out.flush();
        //            } else {
        //                System.out.println("Client handshake failed.");
        //                out.write("you what?");
        //                out.newLine();
        //                out.flush();
        //            }
        //
        //            String auth = in.readLine();
        //            DBAccessor dbAccess = new DBAccessor("jdbc:postgresql://localhost:5432/uschool", "mich", "mich");
        //            //TODO password and user parsing here
        //            if (auth.equals("some tea, chap?")) {
        //                System.out.println("Client authentication successful.");
        //                System.out.println(dbAccess.getUserPrivileges("admin", "admin"));
        //                out.write("love some, old boy");
        //                out.newLine();
        //                out.flush();
        //            } else {
        //                System.out.println("Client authentication failed.");
        //                out.write("what is this muck");
        //                out.newLine();
        //                out.flush();
        //            }
        //
        //            String module = in.readLine();
        //            int mod = Integer.parseInt(module);
        //            ArrayList<Point> regPoint = new ArrayList<Point>();
        //            int fyear = dbAccess.getFirstModuleYear(mod);
        //            for (int curYear = fyear; curYear <= Calendar.getInstance().get(Calendar.YEAR); curYear++) {
        //                regPoint.add(new Point(curYear, dbAccess.getNumberStudents(mod, curYear)));
        //            }
        //
        //            ObjectOutputStream objOut = new ObjectOutputStream(sock.getOutputStream());
        //            objOut.writeObject(regPoint);
        //            objOut.flush();
        //
        //
        //
        //            out.close();
        //            in.close();
        //        } catch (Exception ex) {
        //        }
        //        }

    }
}
