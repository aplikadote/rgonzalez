/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.rgonzalez.poker.core.Card;
import com.rgonzalez.poker.core.CardNumberComparator;
import com.rgonzalez.poker.core.Deck;
import com.rgonzalez.poker.core.PokerConstants;
import com.rgonzalez.poker.hand.HandTypeFactory;
import com.rgonzalez.poker.hand.IHandType;

/**
 * Test de prueba para escala.
 * @author Administrador
 */
public class PokerTest01 {

    public PokerTest01() {
        Deck deck = Deck.getInstance();
        List<Card> cardList = new ArrayList<Card>();
        cardList.add(deck.pop(PokerConstants.HEART, PokerConstants.FOUR));
        cardList.add(deck.pop(PokerConstants.DIAMOND, PokerConstants.FIVE));
        cardList.add(deck.pop(PokerConstants.CLUB, PokerConstants.SIX));
        cardList.add(deck.pop(PokerConstants.DIAMOND, PokerConstants.SEVEN));
        cardList.add(deck.pop(PokerConstants.CLUB, PokerConstants.EIGHT));

        List<Card> ocardList = new ArrayList<Card>();
        ocardList.add(deck.pop(PokerConstants.SPADE, PokerConstants.TEN));
        ocardList.add(deck.pop(PokerConstants.HEART, PokerConstants.JACK));
        ocardList.add(deck.pop(PokerConstants.SPADE, PokerConstants.QUEEN));
        ocardList.add(deck.pop(PokerConstants.HEART, PokerConstants.KING));
        ocardList.add(deck.pop(PokerConstants.SPADE, PokerConstants.AS));

        Collections.sort(cardList, CardNumberComparator.getInstance());
        Collections.sort(ocardList, CardNumberComparator.getInstance());


        IHandType ht = HandTypeFactory.create(cardList);
        IHandType oht = HandTypeFactory.create(ocardList);

        System.out.println(" HT: " + ht);
        System.out.println("OHT: " + oht);

        int diff = ht.compareTo(oht);
        System.out.println(diff);
    }

    public static void main(String[] args) {
        new PokerTest01();
    }
}
