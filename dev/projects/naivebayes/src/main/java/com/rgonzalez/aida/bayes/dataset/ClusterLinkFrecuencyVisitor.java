/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.aida.bayes.dataset;

import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.Word;
import java.util.TreeMap;

/**
 *
 * @author Administrador
 */
public class ClusterLinkFrecuencyVisitor implements IWordVisitor {

    private TreeMap<Word, Integer> mapFrecuency;
    
    public ClusterLinkFrecuencyVisitor(TreeMap<Word, Integer> mapFrecuency){
        this.mapFrecuency = mapFrecuency;
    }
    
    public void visit(Document doc, Word word) {
        if (mapFrecuency.containsKey(word)) {
            int oldFrecuency = mapFrecuency.get(word).intValue();
            int newFrecuency = oldFrecuency + 1;
            mapFrecuency.put(word, new Integer(newFrecuency));
        } else {
            mapFrecuency.put(word, new Integer(1));
        }
    }

}
