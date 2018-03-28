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
public class Flush extends AHandType {

    /**
     * Constructor de la clase.
     * @param cardList La lista de 5 cartas ordenadas de menor a mayor
     * segun el orden especificado en la clase <code>CardNumber</code>
     * @see CardNumber
     */
    public Flush(Card[] cardArray) {
        super(HandTypeValue.FLUSH.getValue(), cardArray);
    }

    public String getName() {
        return "Flush";
    }

    @Override
    public int compareByType(IHandType o) {
        int highCard = this.cardArray[4].getNumber();
        int ohighCard = o.getCardArray()[4].getNumber();
        return highCard - ohighCard;
    }

}
