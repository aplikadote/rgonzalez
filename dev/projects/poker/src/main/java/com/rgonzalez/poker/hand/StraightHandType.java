/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.hand;

import com.rgonzalez.poker.core.Card;
import com.rgonzalez.poker.core.PokerConstants;

/**
 *
 * @author Administrador
 */
public abstract class StraightHandType extends AHandType {

    public StraightHandType(int value, Card[] cardArray) {
        super(value, cardArray);
    }

    public int getStraightHighCardNumber() {
        Card highCard = null;
        if (cardArray[3].getNumber() == PokerConstants.FIVE) {
            highCard = cardArray[3];
        } else {
            highCard = cardArray[4];
        }
        return highCard.getNumber();
    }

    @Override
    public int compareByType(IHandType o) {
        StraightHandType sht = (StraightHandType) o;
        int thisHighCard = getStraightHighCardNumber();
        int oHighCard = sht.getStraightHighCardNumber();
        return thisHighCard - oHighCard;
    }
}
