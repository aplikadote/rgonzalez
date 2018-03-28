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
public class WordWeightVisitor implements IWordVisitor {

    public void visit(Document doc, Word word) {
        TreeMap<Word, Double> mapRelFrecuency = doc.getMapRelativeFrecuency();
        TreeMap<Word, Double> mapWeigth = doc.getMapWeigth();
        double relFrecuency = mapRelFrecuency.get(word);
        double invFrecuency = word.getInverseFrecuency();
        mapWeigth.put(word, relFrecuency * invFrecuency);
    }
}
