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
import java.util.HashMap;

/**
 *
 * @author michal
 */
public class GeneratorGenerator {

    File inputFile;
    ArrayList<String> grammar;
    HashMap<Character, ArrayList<String>> symbols;

    public static void main(String[] args) {
        GeneratorGenerator gg = new GeneratorGenerator("grammar.txt");
        gg.readFile();
        gg.parseGrammar();
    }

    public GeneratorGenerator(String inputFile) {
        this.inputFile = new File(inputFile);
        grammar = new ArrayList<String>();
        symbols = new HashMap<Character, ArrayList<String>>();
    }

    public void parseGrammar() {
        for (String string : grammar) {
            char nonTerm = string.charAt(0);
            if (!symbols.containsKey(nonTerm)) {
                ArrayList<String> ruleList = new ArrayList<String>();
                ruleList.add(string.substring(4));
                symbols.put(nonTerm, ruleList);
            } else {
                ArrayList<String> rules = symbols.get(nonTerm);
                /**
                 * Assumes that each rule is defined as
                 * [non-tem] = [rule]
                 * The rule starts at character 4.
                 */
                String rule = string.substring(4);
                if (!rules.contains(rule)) {
                    rules.add(rule);
                }
            }
        }
        genJavaFile();
    }

    public void genJavaFile() {
        genStartMethod();
    }

    public String genStartMethod() {
        String startSymbolMethod = "public String generateString() {\n\tString genned = \"\";\n\t";
        ArrayList<String> rules = symbols.get('S');
        for (String string : rules) {
            char[] chars = string.toCharArray();
            for (char c : chars) {
                if (Character.isUpperCase(c)) {
                    startSymbolMethod += "genned += gen" + c + "();\n\t";
                } else {
                    startSymbolMethod += "System.out.println(\"" + c + "\");\n\t";
                }
            }
        }
        startSymbolMethod += "return genned;\n}";
        System.out.println(startSymbolMethod);
        return startSymbolMethod;
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
    }
}
