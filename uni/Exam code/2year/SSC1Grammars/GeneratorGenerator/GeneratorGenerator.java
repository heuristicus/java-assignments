/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneratorGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author michal
 */
public class GeneratorGenerator {

    File inputFile;
    ArrayList<String> grammar;

    public static void main(String[] args) {
        GeneratorGenerator gg = new GeneratorGenerator("grammar.txt");
        gg.readFile();
    }

    public GeneratorGenerator(String inputFile) {
        this.inputFile = new File(inputFile);
        grammar = new ArrayList<String>();
    }

    public void parseGrammar(){

    }

    public void readFile() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(inputFile));
            String curLine = r.readLine();
            while (curLine != null) {
                grammar.add(curLine);
                curLine = r.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
        System.out.println(grammar);
    }
}
