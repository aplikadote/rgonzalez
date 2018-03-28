/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.core;

import com.rgonzalez.aida.bayes.main.MainSave;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 *
 * @author Administrador
 */
public class FilterManager {

    private Map<Character, Character> hashTildes = new Hashtable<Character, Character>();
    private TreeSet<String> stopWords = new TreeSet<String>();
    private TreeSet<Document> documents = new TreeSet<Document>();
    private TreeSet<Word> vocabulary = new TreeSet<Word>();
    private static String CONFIGURATION_FILE = "cl/usm/aida/bayes/resources/configuration.properties";
    private static String delimitadores;
    private static String a_tildes;
    private static String e_tildes;
    private static String i_tildes;
    private static String o_tildes;
    private static String u_tildes;

    static {
        try {
            InputStream inputStream;
            Properties properties;
            Class clase = MainSave.class;
            ClassLoader loader = clase.getClassLoader();

            inputStream = loader.getResourceAsStream(CONFIGURATION_FILE);
            properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
            delimitadores = properties.getProperty("delimitadores");
            a_tildes = properties.getProperty("a");
            e_tildes = properties.getProperty("e");
            i_tildes = properties.getProperty("i");
            o_tildes = properties.getProperty("o");
            u_tildes = properties.getProperty("u");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FilterManager() {
        initHashTildes();
        initStopWords();
    }

    public void load(String parentDirectory, String filename) {
        String path = parentDirectory + File.separator + filename;
        File file = new File(path);
        Document doc = new Document(filename);

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String linea;

            while ((linea = in.readLine()) != null) {
                StringTokenizer t = new StringTokenizer(linea, delimitadores);

                while (t.hasMoreTokens()) {
                    String aux = t.nextToken();
                    if (aux.length() > 1) {
                        aux = sacarTildes(aux.toLowerCase());
                        if (!esNumero(aux) && !stopWords.contains(aux)) {
                            link(doc, new Word(aux));
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void link(Document doc, Word word) {
        // se agrega la palabra al vocabulario
        if (this.vocabulary.add(word)) {
            doc.addWord(word);
            word.addDocument(doc);
        } else { // no se agrega, solo se crea la asociacion

            Word aux = this.vocabulary.ceiling(word);
            doc.addWord(aux);
            word.addDocument(doc);
        }
        this.documents.add(doc);
    }

    private String sacarTildes(String palabra) {
        char array[] = palabra.toCharArray();
        for (int i = 0; i < palabra.length(); i++) {
            if (hashTildes.containsKey(palabra.charAt(i))) {
                array[i] = hashTildes.get(palabra.charAt(i));
            }
        }
        return new String(array);
    }

    private boolean esNumero(String aux) {
        try {
            new Integer(aux);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void initHashTildes() {
        String[] array_a_tildes = a_tildes.split(",");
        String[] array_e_tildes = e_tildes.split(",");
        String[] array_i_tildes = i_tildes.split(",");
        String[] array_o_tildes = o_tildes.split(",");
        String[] array_u_tildes = u_tildes.split(",");

        for (int i = 0; i < array_a_tildes.length; i++) {
            hashTildes.put(array_a_tildes[i].toCharArray()[0], 'a');
            hashTildes.put(array_e_tildes[i].toCharArray()[0], 'e');
            hashTildes.put(array_i_tildes[i].toCharArray()[0], 'i');
            hashTildes.put(array_o_tildes[i].toCharArray()[0], 'o');
            hashTildes.put(array_u_tildes[i].toCharArray()[0], 'u');
        }

//        Enumeration<Character> en = hashTildes.keys();
//        while(en.hasMoreElements()){
//            Character element = en.nextElement();
//            System.out.println( element + " " + hashTildes.get(element));
//        }
    }

    private void initStopWords() {
        File dir = new File("stopWords");
        File file;
        BufferedReader var;
        String ficheros[];
        String linea;
        StringTokenizer t;

        try {
            if (dir.isDirectory()) {
                ficheros = dir.list();
                for (int i = 0; i < ficheros.length; i++) {
                    String archivo = dir.toString() + File.separator + ficheros[i];
                    file = new File(archivo);
                    var = new BufferedReader(new FileReader(file));

                    while ((linea = var.readLine()) != null) {
                        t = new StringTokenizer(linea, ", .");

                        while (t.hasMoreTokens()) {
                            String p = t.nextToken();
                            if (p.length() > 1) {
                                stopWords.add(sacarTildes(p));
                            }
                        }
                    }
                    var.close();
                }
            } else {
                System.out.println("No es directorio en Filtro");
                System.exit(0);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TreeSet<Document> getDocuments() {
        return documents;
    }

    public TreeSet<Word> getVocabulary() {
        return vocabulary;
    }
}
