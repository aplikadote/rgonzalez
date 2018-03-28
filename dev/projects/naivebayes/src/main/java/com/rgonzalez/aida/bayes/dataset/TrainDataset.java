/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.dataset;

import com.rgonzalez.aida.bayes.core.Cluster;
import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.Word;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author Administrador
 */
public class TrainDataset {

    public static int n_cluster = 7;
    private TreeSet<Word> trainVocabulary;
    private TreeSet<Document> trainDocuments;
    private Cluster[] trainClusters;
    private double[][] weightMatrix;

    public TrainDataset() {
        trainVocabulary = new TreeSet<Word>();
        trainDocuments = new TreeSet<Document>();
        trainClusters = new Cluster[n_cluster];
        for (int i = 0; i < n_cluster; i++) {
            trainClusters[i] = new Cluster(i);
        }
    }

    public void addDocument(Document doc) {
        this.trainDocuments.add(doc);
        this.trainClusters[doc.getClusterCod()].addDocument(doc);
    }

    public void accept(IWordVisitor visitor) {
        Iterator<Document> itDoc = trainDocuments.iterator();
        while (itDoc.hasNext()) {
            Document doc = itDoc.next();
            Iterator<Word> itWord = doc.getMapFrecuency().keySet().iterator();
            while (itWord.hasNext()) {
                Word word = itWord.next();
                visitor.visit(doc, word);
            }
        }
    }
    
    public void initVocabulary() {
        accept(new VocabularyInitVisitor(trainVocabulary));
    }

    public void initWordRelativeFrecuencys() {
        MaxFrecInDocumentVisitor maxFrec = new MaxFrecInDocumentVisitor(trainDocuments.iterator());
        accept(maxFrec);
        accept(new WordRelFrecVisitor(maxFrec.getMapMaxFrec()));
    }

    public void initWordInverseFrecuencys() {
        Iterator<Word> itWord = trainVocabulary.iterator();
        while (itWord.hasNext()) {
            Word word = itWord.next();
            List<Document> list = word.getListDocuments();
            int docSize = trainDocuments.size();
            int listSize = list.size();
            word.setInverseFrecuency(Math.log((double) docSize / (double) listSize));
        }
    }
    
    public void initWordWeights() {
        accept(new WordWeightVisitor());
    }
    
    public void initIds(){
        Iterator<Document> itDoc = trainDocuments.iterator();
        int i=0;
        while (itDoc.hasNext()) {
            Document doc = itDoc.next();
            doc.setIdDocument(i);
            i++;
        }
        
        i=0;
        Iterator<Word> itWord = trainVocabulary.iterator();
        while(itWord.hasNext()){
           Word word = itWord.next();
           word.setIdWord(i);
           i++;
        }
    }
    
    public void initWeightMatrix(){
        int doc_size = trainDocuments.size();
        int voc_size = trainVocabulary.size();
        weightMatrix = new double[doc_size][voc_size];
        accept(new WeightMatrixVisitor(weightMatrix));
    }

    public void initClusterLinkFrecuencys() {
        for (int i = 0; i < Cluster.n_cluster; i++) {
            trainClusters[i].setClusterLinkFrecuencys();
        }
    }

    public void initNumberOfWords() {
        for (int i = 0; i < Cluster.n_cluster; i++) {
            trainClusters[i].setNumberOfWords();
        }
    }

    public void initProbabilitys(int trainDocSize) {
        for (int i = 0; i < Cluster.n_cluster; i++) {
            trainClusters[i].setProbability(trainDocSize);
        }
    }

    public Cluster[] getTrainClusters() {
        return trainClusters;
    }

    public TreeSet<Document> getTrainDocuments() {
        return trainDocuments;
    }

    public TreeSet<Word> getVocabulary() {
        return trainVocabulary;
    }

}
