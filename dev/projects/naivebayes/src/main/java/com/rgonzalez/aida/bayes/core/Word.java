/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class Word implements Comparable<Word> {

    private String theWord;
    private int idWord;
    private List<Document> listDocuments = new ArrayList<Document>();
    private double inverseFrecuency;

    /**
     * Constructor por defecto
     * @param theWord
     */
    public Word(String theWord) {
        this.theWord = theWord;
    }

    public void addDocument(Document doc) {
        this.listDocuments.add(doc);
    }

    public String toString() {
        return theWord;
    }

    @Override
    public int compareTo(Word o) {
        return this.theWord.compareTo(o.toString());
    }
    
    public int getIdWord() {
        return idWord;
    }

    public void setIdWord(int idWord) {
        this.idWord = idWord;
    }
    
    public double getInverseFrecuency() {
        return inverseFrecuency;
    }

    public void setInverseFrecuency(double inverseFrecuency) {
        this.inverseFrecuency = inverseFrecuency;
    }

    public List<Document> getListDocuments() {
        return listDocuments;
    }
}
