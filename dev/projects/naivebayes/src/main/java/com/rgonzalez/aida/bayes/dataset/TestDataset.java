/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.dataset;

import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.Word;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Administrador
 */
public class TestDataset {

    private List<Document> testDocuments;
//    private TreeSet<Word> testVocabulary = new TreeSet<Word>();

    public TestDataset() {
        testDocuments = new ArrayList<Document>();
    }

    public void addDocument(Document doc) {
        this.testDocuments.add(doc);
    }

    public List<Document> getTestDocuments() {
        return this.testDocuments;
    }

//    public void initVocabulary() {
//        Iterator<Document> itDoc = testDocuments.iterator();
//        while (itDoc.hasNext()) {
//            Document doc = itDoc.next();
//            TreeMap<Word, Integer> map = doc.getMapFrecuency();
//            Iterator<Word> itWord = map.keySet().iterator();
//            while (itWord.hasNext()) {
//                Word word = itWord.next();
//                testVocabulary.add(word);
//            }
//        }
//    }

    public void initWordRelativeFrecuencys(TreeSet<Word> trainVocabulary) {
        // Las palabras en los documentos viene con frecuencia absoluta
        Iterator<Document> itDoc = testDocuments.iterator();
        while (itDoc.hasNext()) {
            Document doc = itDoc.next();
            TreeMap<Word, Integer> mapFrecuency = doc.getMapFrecuency();
            Iterator<Word> itWord = mapFrecuency.keySet().iterator();
            int maxFrecuency = Integer.MIN_VALUE;
            while (itWord.hasNext()) {
                Word word = itWord.next();
                if (trainVocabulary.contains(word)) {
                    int frecuency = mapFrecuency.get(word).intValue();
                    if (frecuency > maxFrecuency) {
                        maxFrecuency = frecuency;
                    }
                }
            }

            // En la treemap de frecuencias relativas solo se ingresaran
            // las palabras que estan en el vocabulario del training set
            TreeMap<Word, Double> mapRelFrecuency = doc.getMapRelativeFrecuency();
            itWord = mapFrecuency.keySet().iterator();
            while (itWord.hasNext()) {
                Word word = itWord.next();
                mapRelFrecuency.put(word, (double) mapFrecuency.get(word).intValue() / (double) maxFrecuency);
            }
        }

    }
    
    public void initWordWeights(){
        Iterator<Document> itDoc = testDocuments.iterator();
        while (itDoc.hasNext()) {
            Document doc = itDoc.next();
            TreeMap<Word, Double> mapRelFrecuency = doc.getMapRelativeFrecuency();
            TreeMap<Word, Double> mapWeigth = doc.getMapWeigth();
            Iterator<Word> itWord = mapRelFrecuency.keySet().iterator();
            while (itWord.hasNext()) {
                Word word = itWord.next();
                double relFrecuency = mapRelFrecuency.get(word);
                double invFrecuency = word.getInverseFrecuency();
                mapWeigth.put(word, relFrecuency*invFrecuency);
            }
        }
    }

//    public TreeSet<Word> getVocabulary() {
//        return this.testVocabulary;
//    }
}
