/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.hand;

import com.rgonzalez.poker.core.Card;

/**
 *
 * @author Administrador
 */
public class Straight extends StraightHandType {

    /**
     * Constructor de la clase.
     * @param cardList La lista de 5 cartas ordenadas de menor a mayor
     * segun el orden especificado en la clase <code>CardNumber</code>
     * @see CardNumber
     */
    public Straight(Card[] cardArray) {
        super(HandTypeValue.STRAIGHT.getValue(), cardArray);
    }

    public String getName(){
        return "Straight";
    }
}
