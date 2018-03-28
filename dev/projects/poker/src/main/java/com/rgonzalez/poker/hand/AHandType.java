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
public abstract class AHandType implements IHandType {

    protected int value;
    protected Card[] cardArray;

    public abstract int compareByType(IHandType o);

    public AHandType(int value, Card[] cardArray) {
        this.value = value;
        this.cardArray = cardArray;
    }

    public int getValue() {
        return this.value;
    }

    public Card[] getCardArray() {
        return this.cardArray;
    }

    public int compareTo(IHandType o) {
        int typeDiff = getValue() - o.getValue();
        if (typeDiff == 0) {
            return compareByType(o);
        }
        return typeDiff;
    }

    @Override
    public String toString() {
        StringBuffer cards = new StringBuffer();
        cards.append("{ ");
        for (int i = 0; i < 4; i++) {
            cards.append(cardArray[i]);
            cards.append(" ; ");
        }
        cards.append(cardArray[4]);
        cards.append(" }");
        return getName() + " " + cards.toString();
    }
}
