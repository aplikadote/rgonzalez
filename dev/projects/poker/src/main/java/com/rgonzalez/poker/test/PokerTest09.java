/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.test;

import java.util.ArrayList;
import java.util.List;
import com.rgonzalez.poker.core.Card;
import com.rgonzalez.poker.core.Deck;
import com.rgonzalez.poker.core.PokerConstants;

/**
 * Caso de prueba para Carta Alta
 * @author Administrador
 */
public class PokerTest09 implements PokerConstants {

    public PokerTest09() {
        List<Card> cardList = new ArrayList<Card>();
        cardList.add(new Card(HEART, AS));
        cardList.add(new Card(DIAMOND, EIGHT));
        cardList.add(new Card(CLUB, FIVE));
        cardList.add(new Card(SPADE, SIX));
        cardList.add(new Card(DIAMOND, FOUR));

        Card[] cardArray = cardList.toArray(new Card[0]);
        for(int i=0; i<cardArray.length; i++){
            System.out.println(cardArray[i]);
        }
        Deck.getInstance().printCards(cardList);

        cardList.remove(0);

        System.out.println();
        for(int i=0; i<cardArray.length; i++){
            System.out.println(cardArray[i]);
        }
        Deck.getInstance().printCards(cardList);

    }

    public static void main(String[] args) {
        new PokerTest09();
    }
}
