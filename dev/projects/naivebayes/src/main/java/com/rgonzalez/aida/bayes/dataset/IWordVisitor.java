/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.aida.bayes.dataset;

import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.Word;

/**
 *
 * @author Administrador
 */
public interface IWordVisitor {

    public void visit(Document doc, Word word);
}
