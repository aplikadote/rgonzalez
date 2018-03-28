/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.visitor;

import com.rgonzalez.poker.combinatory.ICombinatoryVisitor;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class PrintVisitor implements ICombinatoryVisitor<String> {

    public void visit(List<String> comb) {
        for (int i = 0; i < comb.size(); i++) {
            System.out.print(comb.get(i) + " ");
        }
        System.out.println();
    }
}
