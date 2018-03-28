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
public class HighCard extends RepetitionHandType {

    public HighCard(Card[] cardArray) {
        super(HandTypeValue.HIGH_CARD.getValue(), cardArray);
    }

    public int compareTo(IHandType o) {
        Card[] oCardList = o.getCardArray();
        for (int i = 4; i >= 0; i--) {
            int diff = cardArray[i].getNumber() - oCardList[i].getNumber();
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }

    public String getName(){
        return "HighCard";
    }
}
