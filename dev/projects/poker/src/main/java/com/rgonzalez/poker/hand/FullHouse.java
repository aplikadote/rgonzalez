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
public class FullHouse extends RepetitionHandType {

    public FullHouse(Card[] cardArray) {
        super(HandTypeValue.FULL_HOUSE.getValue(), cardArray);
    }

    public String getName(){
        return "FullHouse";
    }
}
