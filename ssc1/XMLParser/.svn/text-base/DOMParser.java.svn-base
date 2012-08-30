/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLParser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author michal
 */
public class DOMParser {

    Document xml;

    public static void main(String[] args) {
        DOMParser p = new DOMParser();
        p.getXMLFromFile("xml2.xml");
//        p.getXMLFromWeb("http://www.cs.bham.ac.uk/~hxt/2010/19343/xml-exercise.shtml");
        p.parseTree();
    }

    public void DOMParser() {
    }

    public void getXMLFromFile(String _fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            xml = builder.parse(new File(_fileName));
        } catch (SAXException ex) {
            System.out.println("Some SAX error ocurred.");
        } catch (IOException ex) {
            System.out.println("An IO exception ocurred when getting XML from file.");
        } catch (ParserConfigurationException ex) {
            System.out.println("An error ocurred when attempting to make the parser.");
        }
    }

    public void getXMLFromWeb(String _url) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            URL webURL = new URL(_url);
            URLConnection connection = webURL.openConnection();
            xml = builder.parse(connection.getInputStream());
        } catch (SAXException ex) {
            System.out.println("Some SAX error ocurred.");
        } catch (IOException ex) {
            System.out.println("An IO exception ocurred when getting XML from the web.");
        } catch (ParserConfigurationException ex) {
            System.out.println("An error ocurred when attempting to make the parser.");
        }
    }

    public void parseTree() {
        xml.getDocumentElement().normalize();
        parseTreeHelper(xml.getFirstChild(), 0);
    }

    /**
     * Recursive helper method which prints the contents of the DOM tree.
     * @param _child The child to start parsing
     * @param _indent The number of times to indent the node or data
     */
    private void parseTreeHelper(Node _child, int _indent) {
        if (_child.getNodeType() != Node.TEXT_NODE) {
            indentText(_indent);
            System.out.println("Node: " + _child.getNodeName()); // Prints the node name
            NodeList children = _child.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                parseTreeHelper(children.item(i), _indent + 1);
            }
        } else if (!_child.getTextContent().trim().isEmpty()) { // Prints the data in the node, but only if it is not whitespace, and then returns.
            indentText(_indent);
            System.out.println("Data:" + _child.getTextContent());
            return;
        }
    }

    /**
     * Indents text using tabs and vertical bars.
     * @param _tabs Number of tabs to insert
     */
    private void indentText(int _tabs) {
        for (int i = 0; i < _tabs; i++) {
            System.out.print("|\t");
        }
    }
}
