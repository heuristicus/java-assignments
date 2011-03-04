/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author michal
 */
public class testclient {

    public static void main(String[] arstring) {
        try {
            System.setProperty("javax.net.ssl.trustStore", "/home/michal/Dropbox/Work/Programming/java/uni/SSC2/NetDB/GraphDB/graphstore");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sock = (SSLSocket) sslsocketfactory.createSocket("localhost", 2000);
            System.out.println("Connected to server.");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            String greeting = "what ho, old chap?";
            out.write(greeting);
            out.newLine();
            out.flush();

            String resp = in.readLine();
            if (resp.equals("good day, what?")){
                System.out.println("well well well");
            } else {
                System.out.println("oh dear.");
            }

            String auth = "some tea, chap?";
            out.write(auth);


            out.close();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
