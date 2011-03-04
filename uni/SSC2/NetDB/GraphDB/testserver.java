/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author michal
 */
public class testserver {

    public static void main(String[] arstring) {
        try {
            System.setProperty("javax.net.ssl.keyStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");
            SSLServerSocket servSock = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(2000);
            System.out.printf("Server listening on port %d.\n", servSock.getLocalPort());
            SSLSocket sock = (SSLSocket) servSock.accept();
            System.out.printf("Client connected from %s.\n", sock.getInetAddress());
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            String greeting = in.readLine();
            if (greeting.equals("what ho, old chap?")) {
                System.out.println("Client handshake successful.");
                out.write("good day, what?");
                out.newLine();
                out.flush();
            } else {
                System.out.println("Client handshake failed.");
                out.write("you what?");
                out.newLine();
                out.flush();
            }

            String auth = in.readLine();
            DBAccessor dbAccess = new DBAccessor("jdbc:postgresql://localhost:5432/uschool", "mich", "mich");
            //TODO password and user parsing here
            if (auth.equals("some tea, chap?")) {
                System.out.println("Client authentication successful.");
                System.out.println(dbAccess.getUserPrivileges("admin", "admin"));
                out.write("love some, old boy");
                out.newLine();
                out.flush();
            } else {
                System.out.println("Client authentication failed.");
                out.write("what is this muck");
                out.newLine();
                out.flush();
            }

            String module = in.readLine();
            int mod = Integer.parseInt(module);
            ArrayList<Point> regPoint = new ArrayList<Point>();
            int fyear = dbAccess.getFirstModuleYear(mod);
            for (int curYear = fyear; curYear <= Calendar.getInstance().get(Calendar.YEAR); curYear++) {
                regPoint.add(new Point(curYear, dbAccess.getNumberStudents(mod, curYear)));
            }

            ObjectOutputStream objOut = new ObjectOutputStream(sock.getOutputStream());
            objOut.writeObject(regPoint);
            objOut.flush();



            out.close();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
