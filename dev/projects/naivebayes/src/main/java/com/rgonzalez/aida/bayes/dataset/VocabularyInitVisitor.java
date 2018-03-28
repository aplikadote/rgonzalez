/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.aida.bayes.dataset;

import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.Word;
import java.util.TreeSet;

/**
 *
 * @author Administrador
 */
public class VocabularyInitVisitor implements IWordVisitor{

    private TreeSet<Word> trainVocabulary;
    
    public VocabularyInitVisitor(TreeSet<Word> trainVocabulary) {
        this.trainVocabulary = trainVocabulary;
    }

    public void visit(Document doc, Word word) {
        trainVocabulary.add(word);
    }

}
