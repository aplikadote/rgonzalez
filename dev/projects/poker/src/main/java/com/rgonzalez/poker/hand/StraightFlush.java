/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.hand;

import java.util.List;
import com.rgonzalez.poker.core.Card;
import com.rgonzalez.poker.core.PokerConstants;

/**
 *
 * @author Administrador
 */
public class StraightFlush extends StraightHandType {

    /**
     * Constructor de la clase.
     * @param cardList La lista de 5 cartas ordenadas de menor a mayor.
     */
    public StraightFlush(Card[] cardArray) {
        super(HandTypeValue.STRAIGHT_FLUSH.getValue(), cardArray);
    }

    public String getName(){
        return "StraightFlush";
    }
}

