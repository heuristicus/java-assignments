/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author michal
 */
public class StAXParser {

    String fileName;
    XMLStreamReader parser;

    public static void main(String[] args) {
        StAXParser stax = new StAXParser("xml2.xml");
        stax.parse();
//        stax.getXMLFromWeb("http://www.cs.bham.ac.uk/~hxt/2010/19343/xml-exercise.shtml");
//        stax.parse();
    }

    public StAXParser(String _fileName) {
        fileName = _fileName;
        getXMLFromFile();
    }

    private void getXMLFromFile() {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(new File(fileName)));
            XMLInputFactory factory = XMLInputFactory.newInstance();
            parser = factory.createXMLStreamReader(fileReader);
        } catch (XMLStreamException ex) {
            System.out.println("Something happened with the XML stream creation.");
        } catch (FileNotFoundException ex) {
            System.out.println("Something went wrong when attempting to open the file.");
        }
    }

    public void getXMLFromWeb(String _url) {
        InputStream webIn = null;
        try {
            URL webURL = new URL(_url);
            URLConnection conn = webURL.openConnection();
            webIn = conn.getInputStream();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            parser = factory.createXMLStreamReader(webIn);
        } catch (XMLStreamException ex) {
            System.out.println("An XML error ocurred.");
        } catch (IOException ex) {
            System.out.println("An error ocurred when trying to access the URL.");
        } finally {
            try {
                webIn.close();
            } catch (IOException ex) {
                System.out.println("An IO exception ocurred when attempting to close the web stream.");
            }
        }

    }

    public void parse() {
        int indentation = 0;
        int count = 0;
        try {
            while (parser.hasNext()) {
                if (parser.isStartElement()) {
                    indentText(indentation);
                    System.out.println("Node: " + parser.getName());
                    indentation++;
                }
                if (parser.isEndElement()) {
                    indentation--;
                }
                if (parser.hasText() && !parser.isWhiteSpace()) {
                    indentText(indentation);
                    System.out.println("Data: " + parser.getText());
                }
                parser.next();
            }
        } catch (XMLStreamException ex) {
            System.out.println("Something went wrong when trying to parse the XML.");
        } finally {
            try {
                parser.close();
            } catch (XMLStreamException ex) {
                System.out.println("An exception ocurred when attempting to close the parse stream.");
            }
        }
    }

    private void indentText(int _indent) {
        for (int i = 0; i < _indent; i++) {
            System.out.print("|\t");
        }
    }
}
