/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.aida.bayes.dataset;

import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.Word;
import java.util.Hashtable;
import java.util.TreeMap;

/**
 *
 * @author Administrador
 */
public class WordRelFrecVisitor implements IWordVisitor{

    private Hashtable<Document, Integer> mapMaxFrec = new Hashtable<Document, Integer>();
    
    public WordRelFrecVisitor(Hashtable<Document, Integer> mapMaxFrec){
        this.mapMaxFrec = mapMaxFrec;
    }
    
    public void visit(Document doc, Word word) {
        TreeMap<Word, Integer> mapFrecuency = doc.getMapFrecuency();
        TreeMap<Word, Double> map = doc.getMapRelativeFrecuency();
        int frec = mapFrecuency.get(word).intValue();
        double maxFrec = mapMaxFrec.get(doc).doubleValue();
        map.put(word, (double) frec / maxFrec);
    }

}
