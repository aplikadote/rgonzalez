/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.poker.combinatory;

import java.util.List;

/**
 *
 * @author Administrador
 */
public interface ICombinatoryVisitor<A> {

    public void visit(List<A> comb);
}
