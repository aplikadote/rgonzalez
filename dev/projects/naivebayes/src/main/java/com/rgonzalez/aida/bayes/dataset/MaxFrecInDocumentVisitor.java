/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.aida.bayes.dataset;

import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.Word;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Administrador
 */
public class MaxFrecInDocumentVisitor implements IWordVisitor{

    private Hashtable<Document, Integer> mapMaxFrec = new Hashtable<Document, Integer>();

    public MaxFrecInDocumentVisitor(Iterator<Document> itDoc){
        while(itDoc.hasNext()){
            mapMaxFrec.put(itDoc.next(), Integer.MIN_VALUE);
        }
    }

    public void visit(Document doc, Word word) {
        TreeMap<Word, Integer> mapFrecuency = doc.getMapFrecuency();
        if( mapFrecuency.get(word) > mapMaxFrec.get(doc)){
            mapMaxFrec.put(doc, mapFrecuency.get(word));
        }
    }
    
    public Hashtable<Document, Integer> getMapMaxFrec() {
        return mapMaxFrec;
    }
}
