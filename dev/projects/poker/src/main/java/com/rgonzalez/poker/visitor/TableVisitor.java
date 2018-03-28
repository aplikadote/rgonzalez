/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.poker.visitor;

import com.rgonzalez.poker.combinatory.ICombinatoryVisitor;
import java.util.List;
import com.rgonzalez.poker.core.Card;
import com.rgonzalez.poker.core.TableData;

/**
 * El objeto visitante encargado de asigar el flop
 * al objeto <code>TableData</code> y llamar
 * al procesamiento del resultado final de la mesa.
 * @author Administrador
 */
public class TableVisitor implements ICombinatoryVisitor<Card>{

    private TableData tableData;
    
    public TableVisitor(TableData tableData) {
        this.tableData = tableData;
    }

    /**
     * El metodo solo asigna las cartas del flop a la
     * mesa y luego llama a procesar todo.
     * @param comb La lista de cartas del flop. Deben ser 5 cartas.
     */
    public void visit(List<Card> comb) {
        this.tableData.setFlopComb(comb);
        this.tableData.process();
    }

}
