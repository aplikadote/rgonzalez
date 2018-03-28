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
public class TwoPair extends RepetitionHandType {

    public TwoPair(Card[] cardArray) {
        super(HandTypeValue.TWO_PAIRS.getValue(), cardArray);
    }

    public String getName() {
        return "TwoPair";
    }
}
