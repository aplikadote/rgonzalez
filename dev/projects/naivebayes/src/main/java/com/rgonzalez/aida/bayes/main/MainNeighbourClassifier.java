/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.main;

import com.rgonzalez.aida.bayes.dataset.TestDataset;
import com.rgonzalez.aida.bayes.dataset.TrainDataset;
import com.rgonzalez.aida.bayes.core.Cluster;
import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.Word;
import com.rgonzalez.aida.bayes.database.ConnectionLoadMysql;
import com.rgonzalez.aida.bayes.dataset.WordPrinterVisitor;
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
public class MainNeighbourClassifier {

    private TreeSet<Document> documents;
    private TrainDataset train;
    private TestDataset test;

    public MainNeighbourClassifier() {
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
        train.initWordRelativeFrecuencys();
        train.initWordInverseFrecuencys();
        train.initWordWeights();
        train.initIds();
        train.initWeightMatrix();
        
        test.initWordRelativeFrecuencys(train.getVocabulary());
        test.initWordWeights();
    }

    private void classify() {
        int[][] confusionMatrix = new int[Cluster.n_cluster][Cluster.n_cluster];
        Iterator<Document> itTestDoc = test.getTestDocuments().iterator();
        int cont=0;
        int cont2=0;
        while(itTestDoc.hasNext()){
            Document testDoc = itTestDoc.next();
            
            Iterator<Document> itTrainDoc = train.getTrainDocuments().iterator();
            double maxSimil = Double.NEGATIVE_INFINITY;
            Document maxDocument = null;
            while(itTrainDoc.hasNext()){
                Document trainDoc = itTrainDoc.next();
                double simil = trainDoc.similarity( testDoc );
                if(simil > maxSimil){
                    maxSimil = simil;
                    maxDocument = trainDoc;
                }
            }
            
            
            if(testDoc.getClusterCod()==maxDocument.getClusterCod()){
                System.out.println( testDoc.getClusterCod() + "  " + maxDocument.getClusterCod() + " BIEN");
                cont++;
            }
            else{
                System.out.println( testDoc.getClusterCod() + "  " + maxDocument.getClusterCod() + " MAL");    
                cont2++;
            }
            confusionMatrix[testDoc.getClusterCod()][maxDocument.getClusterCod()]++;
        }
        System.out.println(cont + "  " + cont2);
        
        for(int i=0; i<Cluster.n_cluster; i++){
            int contador=0;
            for(int j=0; j<Cluster.n_cluster; j++){
                if(i==j){
                    System.out.print( "\\textbf{" + confusionMatrix[i][j] + "} & ");
                }
                else{
                    System.out.print(confusionMatrix[i][j] + "          & ");
                }
                contador+=confusionMatrix[i][j];
            }
            System.out.println( ((double)confusionMatrix[i][i]/(double)contador)*100 + "\\%");
        }
    }

    public static void main(String[] args) {
        new MainNeighbourClassifier();
    }
}
