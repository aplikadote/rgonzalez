/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.core;

import com.rgonzalez.aida.bayes.dataset.ClusterLinkFrecuencyVisitor;
import com.rgonzalez.aida.bayes.dataset.IWordVisitor;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Administrador
 */
public class Cluster {

    private TreeSet<Document> documents = new TreeSet<Document>();
    private TreeMap<Word, Integer> mapFrecuency = new TreeMap<Word, Integer>();
    private TreeMap<Word, Double> mapMean = new TreeMap<Word, Double>();
    private TreeMap<Word, Double> mapVariance = new TreeMap<Word, Double>();
    private int clusterCod;
    private String clusterName;
    private int numberOfWords;
    private double probability;
    public static int n_cluster = 7;
    public static final int[] CLUSTER_COD = {
        0, 1, 2, 3, 4, 5, 6
    };
    public static final String[] CLUSTER_STRING = {
        "Cultura", "Deporte", "Educacion", "Espectaculo", "Nacional", "Negocios", "Politica"
    };

    public Cluster(int clusterCod) {
        this.clusterCod = clusterCod;
        this.clusterName = CLUSTER_STRING[clusterCod];
    }

    public void addDocument(Document doc) {
        this.documents.add(doc);
    }

    public void accept(IWordVisitor visitor){
        Iterator<Document> itDoc = documents.iterator();
        while (itDoc.hasNext()) {
            Document doc = itDoc.next();
            Iterator<Word> itWord = doc.getMapFrecuency().keySet().iterator();
            while (itWord.hasNext()) {
                Word word = itWord.next();
                visitor.visit(doc, word);
            }
        }
    }
    
    public void setClusterLinkFrecuencys() {
        accept( new ClusterLinkFrecuencyVisitor(mapFrecuency));
    }

    public void setNumberOfWords() {
        Iterator<Word> itWord = mapFrecuency.keySet().iterator();
        int sum = 0;
        while (itWord.hasNext()) {
            Word word = itWord.next();
            sum += mapFrecuency.get(word).intValue();
        }
        this.numberOfWords = sum;
    }

    public int getDocumentSize() {
        return this.documents.size();
    }

    public int getVocabularySize() {
        return this.mapFrecuency.size();
    }

    public String getClusterName() {
        return this.clusterName;
    }

    public int getClusterCod() {
        return this.clusterCod;
    }

    public TreeSet<Document> getDocuments() {
        return this.documents;
    }

//    public void addWord(Word word) {
//        if (mapFrecuency.containsKey(word)) {
//            int oldFrecuency = mapFrecuency.get(word).intValue();
//            int newFrecuency = oldFrecuency + 1;
//            mapFrecuency.put(word, new Integer(newFrecuency));
//        } else {
//            mapFrecuency.put(word, new Integer(1));
//        }
//    }

    public TreeMap<Word, Integer> getMapFrecuency() {
        return this.mapFrecuency;
    }

    public int getNumberOfWords() {
        return this.numberOfWords;
    }

    public void setProbability(int docSize) {
        this.probability = (double) this.getDocumentSize() / docSize;
    }

    public double getProbabilty() {
        return this.probability;
    }

    public TreeMap<Word, Double> getMapMean() {
        return mapMean;
    }

    public TreeMap<Word, Double> getMapVariance() {
        return mapVariance;
    }
}

