/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.visitor;

import com.rgonzalez.poker.combinatory.ICombinatoryVisitor;
import java.util.List;
import com.rgonzalez.poker.core.Card;
import com.rgonzalez.poker.combinatory.Combinatory;
import com.rgonzalez.poker.core.Deck;
import com.rgonzalez.poker.core.TableData;

/**
 * Este es el visitante para la combinatoria de las cartas
 * de los jugadores adversarios. El tamaño de la lista
 * que llega en el metodo <code>visit(.)</code> depende de
 * la cantidad de adversarios (2 * # de adversarios).
 * @author Administrador
 */
public class AdversairesVisitor implements ICombinatoryVisitor<Card> {

    private List<Card> originalCardList;
    private TableData tableData;
    private int i = 0;

    public AdversairesVisitor(List<Card> originalCardList, TableData tableData) {
        this.originalCardList = originalCardList;
        this.tableData = tableData;
    }

    /**
     * El metodo entrega una lista de las cartas de los contricantes.
     * Las dos primeras cartas corresponden al primer jugador, las 2 siguientes,
     * al siguiente jugador y asi sucesivamente. Por lo tanto, la cantidad
     * de cartas que lleguen en esta lista sera un multiplo de 2. Esta lista
     * se agrega al objeto <code>TableData</code> encargado de evaluar
     * la mesa.
     * <BR>De la lista original de cartas (el total de las cartas de una baraja
     * menos las 2 cartas del jugador), se quitan las cartas de los jugadores
     * que han llegado en la visita. Con las cartas que quedan en la baraja
     * se realiza la ultima combinatoria de 5 cartas que representan las cartas
     * que saldran en la mesa.
     * <BR>El objeto <code>TableVisitor</code> sera el encargado de procesar
     * el ultimo subconjunto de cartas que el objeto <code>TableVisitor</code>
     * necesata para evaluar la mesa.
     * <BR>Finalmente, las cartas que han sido removidas deberan ser agregadas.
     * @param comb La lista de cartas de los contricantes.
     */
    public void visit(List<Card> comb) {
        System.out.println("VISITANDO " + (i++) + ": " + comb);
        try {
            remove(comb);
            tableData.setAdversariesCombs(comb);
            Combinatory<Card> combinatory = new Combinatory<Card>(originalCardList, 5);
            combinatory.accept(new TableVisitor(tableData));
            restore(comb);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void remove(List<Card> comb) {
        for (Card card : comb) {
            this.originalCardList.remove(card);
        }
    }

    private void restore(List<Card> comb) {
        for (Card card : comb) {
            this.originalCardList.add(card);
        }
    }
}
