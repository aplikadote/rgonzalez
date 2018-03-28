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
public class FourOfAKind extends RepetitionHandType {

    public FourOfAKind(Card[] cardArray) {
        super(HandTypeValue.FOUR_OF_A_KIND.getValue(), cardArray);
    }

    public String getName(){
        return "FourOfAKind";
    }

}
