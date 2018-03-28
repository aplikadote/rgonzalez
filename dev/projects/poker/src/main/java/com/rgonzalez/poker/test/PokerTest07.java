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
import com.rgonzalez.poker.core.PokerConstants;
import com.rgonzalez.poker.hand.HandTypeFactory;
import com.rgonzalez.poker.hand.IHandType;

/**
 * Caso de prueba para Un Par.
 * @author Administrador
 */
public class PokerTest07 {

    public PokerTest07() {
//        Deck deck = Deck.getInstance();
        List<Card> cardList = new ArrayList<Card>();
        cardList.add(new Card(PokerConstants.HEART, PokerConstants.THREE));
        cardList.add(new Card(PokerConstants.DIAMOND, PokerConstants.THREE));
        cardList.add(new Card(PokerConstants.CLUB, PokerConstants.FIVE));
        cardList.add(new Card(PokerConstants.SPADE, PokerConstants.SIX));
        cardList.add(new Card(PokerConstants.DIAMOND, PokerConstants.EIGHT));

        List<Card> ocardList = new ArrayList<Card>();
        ocardList.add(new Card(PokerConstants.HEART, PokerConstants.THREE));
        ocardList.add(new Card(PokerConstants.DIAMOND, PokerConstants.THREE));
        ocardList.add(new Card(PokerConstants.CLUB, PokerConstants.FOUR));
        ocardList.add(new Card(PokerConstants.SPADE, PokerConstants.SIX));
        ocardList.add(new Card(PokerConstants.CLUB, PokerConstants.EIGHT));

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
        new PokerTest07();
    }
}
