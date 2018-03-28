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
 * Caso de prueba para FourOfAKind.
 * @author Administrador
 */
public class PokerTest03 {

    public PokerTest03() {
        Deck deck = Deck.getInstance();
        List<Card> cardList = new ArrayList<Card>();
        cardList.add(deck.pop(PokerConstants.HEART, PokerConstants.SIX));
        cardList.add(deck.pop(PokerConstants.SPADE, PokerConstants.TWO));
        cardList.add(deck.pop(PokerConstants.HEART, PokerConstants.TWO));
        cardList.add(deck.pop(PokerConstants.DIAMOND, PokerConstants.TWO));
        cardList.add(deck.pop(PokerConstants.CLUB, PokerConstants.TWO));

        List<Card> ocardList = new ArrayList<Card>();
        ocardList.add(deck.pop(PokerConstants.DIAMOND, PokerConstants.FIVE));
        ocardList.add(deck.pop(PokerConstants.SPADE, PokerConstants.THREE));
        ocardList.add(deck.pop(PokerConstants.HEART, PokerConstants.THREE));
        ocardList.add(deck.pop(PokerConstants.DIAMOND, PokerConstants.THREE));
        ocardList.add(deck.pop(PokerConstants.CLUB, PokerConstants.THREE));

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
        new PokerTest03();
    }
}
