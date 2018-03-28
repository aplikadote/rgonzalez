/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.core;

import java.util.Comparator;

/**
 *
 * @author Administrador
 */
public class CardNumberComparator implements Comparator<Card> {

    private static CardNumberComparator instance;

    private CardNumberComparator() {
    }

    public static CardNumberComparator getInstance() {
        if (instance == null) {
            return new CardNumberComparator();
        }
        return instance;
    }

    public int compare(Card o1, Card o2) {
        return o1.getNumber() - o2.getNumber();
    }
}
