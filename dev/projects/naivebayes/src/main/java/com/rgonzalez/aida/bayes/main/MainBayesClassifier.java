/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.main;

import com.rgonzalez.aida.bayes.core.Cluster;
import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.Word;
import com.rgonzalez.aida.bayes.database.ConnectionLoadMysql;
import com.rgonzalez.aida.bayes.dataset.TestDataset;
import com.rgonzalez.aida.bayes.dataset.TrainDataset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Administrador
 */
public class MainBayesClassifier {

    private TreeSet<Document> documents;
    private TrainDataset train;
    private TestDataset test;

    public MainBayesClassifier() {
        loadFromDB();
        splitDataset();
        initTrainDataset();
        classify();
    }

    private void loadFromDB() {
        ConnectionLoadMysql load = new ConnectionLoadMysql();
        documents = load.getDocuments();
    }

    private void splitDataset() {
        Random r = new Random(System.currentTimeMillis());
        train = new TrainDataset();
        test = new TestDataset();

        int docSize = documents.size();
        int trainSize = (int) Math.floor(docSize * 0.8);

        List<Document> testDataset = new ArrayList<Document>();
        testDataset.addAll(documents);
        for (int j = 0; j < trainSize; j++) {
            int p = r.nextInt(testDataset.size());
            Document doc = testDataset.get(p);
            train.addDocument(doc);
            testDataset.remove(doc);
        }

        for (int j = 0; j < testDataset.size(); j++) {
            Document doc = testDataset.get(j);
            test.addDocument(doc);
        }
    }

    private void initTrainDataset() {
        train.initVocabulary();
//        test.initVocabulary();
        train.initClusterLinkFrecuencys();
        train.initNumberOfWords();
        train.initProbabilitys(train.getTrainDocuments().size());
    }

    private void classify() {
        List<Document> list = test.getTestDocuments();

        int[][] confusionMatrix = new int[Cluster.n_cluster][Cluster.n_cluster];
        int cont = 0;
        int cont2 = 0;
        for (int i = 0; i < list.size(); i++) {
            Document testDocument = list.get(i);

            double max_value = Double.NEGATIVE_INFINITY;
            int max_cluster = -1;
            double[] sumProb = new double[Cluster.n_cluster];
            for (int j = 0; j < Cluster.n_cluster; j++) {
                Cluster cluster = train.getTrainClusters()[j];
                TreeMap<Word, Integer> map = cluster.getMapFrecuency();
                sumProb[j] = 0;
                int n_words = cluster.getNumberOfWords();
                int n_voc = cluster.getVocabularySize();
                Iterator<Word> itTestDocWord = testDocument.getMapFrecuency().keySet().iterator();
                while (itTestDocWord.hasNext()) {
                    Word testWord = itTestDocWord.next();

                    if (train.getVocabulary().contains(testWord)) {
                        if (map.containsKey(testWord)) {
                            int value = map.get(testWord).intValue();
                            sumProb[j] += Math.log((double) (value + 1) / (n_words + n_voc));
                        } else {
                            sumProb[j] += Math.log((1 / (double) (n_words + n_voc)));
                        }
                    }
                }
                sumProb[j] += Math.log(cluster.getProbabilty());

                if (sumProb[j] > max_value) {
                    max_value = sumProb[j];
                    max_cluster = j;
                }
            }

            testDocument.setClasifierCluster(max_cluster);

            if (testDocument.getClusterCod() == testDocument.getClasifierCluster()) {
                System.out.println(testDocument.getClusterCod() + "  " + testDocument.getClasifierCluster() + "  BIEN ");
                cont++;
            } else {
                System.out.println(testDocument.getClusterCod() + "  " + testDocument.getClasifierCluster() + "  MAL ");
                cont2++;
            }
            
            confusionMatrix[testDocument.getClusterCod()][testDocument.getClasifierCluster()]++;

        }
        System.out.println(cont + "  " + cont2);
        
        for(int i=0; i<Cluster.n_cluster; i++){
            for(int j=0; j<Cluster.n_cluster; j++){
                System.out.print(confusionMatrix[i][j] + " &  ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new MainBayesClassifier();
    }
}
