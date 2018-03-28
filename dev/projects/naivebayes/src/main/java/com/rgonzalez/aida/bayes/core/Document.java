/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.core;

import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Administrador
 */
public class Document implements Comparable<Document>{

    private String filename;
    private String clusterName;
    private int clusterCod;
    private int clusterClasifierCod;
    private int idDocument;
    private TreeMap<Word,Integer> mapFrecuency = new TreeMap<Word,Integer>();
    private TreeMap<Word,Double> mapRelativeFrecuency = new TreeMap<Word,Double>();
    private TreeMap<Word,Double> mapWeigth = new TreeMap<Word,Double>();

    /**
     * Constructor usado para carga de datos desde BD
     * @param filename
     */
    public Document(String filename) {
        this.filename = filename;
        setClusterProperties();
    }
    
    /**
     * Utilizado para cuando se cargan los datos secuencialmente
     * desde ficheros de texto
     * @param word
     */
    public void addWord(Word word){
        if( mapFrecuency.containsKey(word)){
            int oldFrecuency = mapFrecuency.get(word).intValue();
            int newFrecuency = oldFrecuency + 1;
            mapFrecuency.put(word, new Integer(newFrecuency));
        }
        else{
            mapFrecuency.put(word, 1);
        }
    }
    
    /**
     * Utilizado cuando se cargan los datos desde una BD
     * @param word
     */
    public void putWord(Word word, Integer frecuency){
        this.mapFrecuency.put(word, frecuency);
    }
    
    public String getFilename(){
        return this.filename;
    }

    public int compareTo(Document o) {
        return this.getFilename().compareTo(o.getFilename());
    }
    
    public void setClusterProperties(){
        String cod = String.valueOf(filename.subSequence(3, 6));
        if (cod.compareTo("Cul") == 0) {
            clusterName = Cluster.CLUSTER_STRING[0];
            clusterCod = Cluster.CLUSTER_COD[0];
        } else if (cod.compareTo("Dep") == 0) {
            clusterName = Cluster.CLUSTER_STRING[1];
            clusterCod = Cluster.CLUSTER_COD[1];
        } else if (cod.compareTo("Edu") == 0) {
            clusterName = Cluster.CLUSTER_STRING[2];
            clusterCod = Cluster.CLUSTER_COD[2];
        } else if (cod.compareTo("Esp") == 0) {
            clusterName = Cluster.CLUSTER_STRING[3];
            clusterCod = Cluster.CLUSTER_COD[3];
        } else if (cod.compareTo("Nac") == 0) {
            clusterName = Cluster.CLUSTER_STRING[4];
            clusterCod = Cluster.CLUSTER_COD[4];
        } else if (cod.compareTo("Neg") == 0) {
            clusterName = Cluster.CLUSTER_STRING[5];
            clusterCod = Cluster.CLUSTER_COD[5];
        } else if (cod.compareTo("Pol") == 0) {
            clusterName = Cluster.CLUSTER_STRING[6];
            clusterCod = Cluster.CLUSTER_COD[6];
        } else {
            clusterName = "";
            clusterCod = -1;
        }
    }
    
    public TreeMap<Word,Integer> getMapFrecuency(){
        return this.mapFrecuency;
    }
    
    public String getClusterName(){
        return this.clusterName;
    }
    
    public int getClusterCod(){
        return this.clusterCod;
    }
    
    public void setClasifierCluster(int cluster){
        this.clusterClasifierCod = cluster;
    }
    
    public int getClasifierCluster(){
        return this.clusterClasifierCod;
    }
    
    public String toString(){
        return this.filename;
    }
    
    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }
    
    public TreeMap<Word, Double> getMapRelativeFrecuency() {
        return mapRelativeFrecuency;
    }
    
    public TreeMap<Word, Double> getMapWeigth() {
        return mapWeigth;
    }
    
    public double similarity(Document doc){
        TreeMap<Word, Double> mapDocWeight = doc.getMapWeigth();
        Iterator<Word> itWord = this.mapWeigth.keySet().iterator();
        
        double sumWeight1 = 0;
        double sumWeight2 = 0;
        double sumCrossPoint = 0;
        while(itWord.hasNext()){
            Word word = itWord.next();
            double weight1 = this.mapWeigth.get(word).doubleValue();
            double weight2;
            if( mapDocWeight.containsKey(word)){
                weight2 = mapDocWeight.get(word).doubleValue();
            }
            else{
                weight2 = 0;
            }
            sumCrossPoint += weight1*weight2;
            sumWeight1 += Math.pow( weight1,2);
            sumWeight2 += Math.pow( weight2,2);
        }
        return sumCrossPoint/( Math.sqrt(sumWeight1) * Math.sqrt(sumWeight2));
    }
}
