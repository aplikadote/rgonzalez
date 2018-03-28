/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.hand;

import java.util.List;
import com.rgonzalez.poker.core.Card;

/**
 *
 * @author Administrador
 */
public class OnePair extends RepetitionHandType {

    public OnePair(Card[] cardArray) {
        super(HandTypeValue.ONE_PAIR.getValue(), cardArray);
    }

    public String getName(){
        return "OnePair";
    }
}
