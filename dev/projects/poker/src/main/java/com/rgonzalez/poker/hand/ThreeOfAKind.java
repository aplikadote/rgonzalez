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
public class ThreeOfAKind extends RepetitionHandType {

    public ThreeOfAKind(Card[] cardArray) {
        super(HandTypeValue.THREE_OF_A_KIND.getValue(), cardArray);
    }

    public String getName(){
        return "ThreeOfAKind";
    }
}
