/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.core;

/**
 *
 * @author Administrador
 */
public class Card implements Cloneable{

    private int cardType;
    private int cardNumber;

    /**
     * El constructor necesita el tipo y el numero de la carta.
     * Estos valores estan definidos en la interfaz
     * <code>PokerConstants</code>.
     * @param type El tipo de la carta, entero entre [0,3].
     * @param number El numero de la carta, entero entre [2,14];
     */
    public Card(int cardType, int cardNumber) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
    }

    public int getType() {
        return cardType;
    }

    public int getNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        String type = Deck.getInstance().getTypeAcronyms(cardType);
        String number = Deck.getInstance().getNumberAcronyms(cardNumber);
        return "[" + type + ", " + number + "]";
    }

    public Card cloneCard() {
        return new Card(this.cardType, this.cardNumber);
    }

//    public int compareToByNumberNatural(Card o) {
//        return this.cardNumber - o.getNumber();
//    }
//
//    public int compareToByNumberPoker(Card o) {
//        return this.cardNumber - o.getNumber();
//    }
//
//    public int compareToByType(Card o) {
//        return this.getType() - o.getType();
//    }
}
