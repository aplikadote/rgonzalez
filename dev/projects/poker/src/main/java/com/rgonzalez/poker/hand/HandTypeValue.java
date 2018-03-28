/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.hand;

/**
 *
 * @author Administrador
 */
public enum HandTypeValue {

    NULL(0),
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIRS(3),
    THREE_OF_A_KIND(5),
    STRAIGHT(4),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_A_KIND(8),
    STRAIGHT_FLUSH(9);
    private int value;

    HandTypeValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
