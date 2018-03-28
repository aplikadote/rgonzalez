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
public class WordPrinterVisitor implements IWordVisitor{

    public void visit(Document doc, Word word) {
        TreeMap<Word, Integer> mapFrecuency = doc.getMapFrecuency();
        TreeMap<Word, Double> mapRelFrecuency = doc.getMapRelativeFrecuency();
        TreeMap<Word, Double> mapWeight = doc.getMapWeigth();
        
        System.out.println(word + " " + mapFrecuency.get(word) + " " + mapRelFrecuency.get(word) + " " + mapWeight.get(word));
    }

}
