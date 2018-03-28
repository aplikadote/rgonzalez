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
public class WeightMatrixVisitor implements IWordVisitor {

    private double[][] weightMatrix;

    public WeightMatrixVisitor(double[][] weightMatrix) {
        this.weightMatrix = weightMatrix;
    }

    public void visit(Document doc, Word word) {
        TreeMap<Word, Double> mapWeigth = doc.getMapWeigth();
        weightMatrix[doc.getIdDocument()][word.getIdWord()] = mapWeigth.get(word);
    }
}
