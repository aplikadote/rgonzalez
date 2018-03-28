/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import com.rgonzalez.poker.hand.IHandType;

/**
 *
 * @author Administrador
 */
public class Deck implements PokerConstants {

    private int cardCount;
    private HashMap<Integer, Card> idToCard;
    private static Deck instance;

    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    private Deck() {
        this.idToCard = new HashMap<Integer, Card>();
        for (byte i = 0; i < MAX_TYPE; i++) {
            for (byte j = MIN_NUMBER; j <= MAX_NUMBER; j++) {
                this.idToCard.put(getId(i, j), new Card(i, j));
            }
        }
        this.cardCount = this.idToCard.size();
    }

    /**
     * Quita una carta del mazo. Reduce la cantidad de
     * cartas en 1.
     * @param type El tipo, entero entre [0,3]
     * @param number El numero, entero entre [0,12]
     * @return La carta quitada del mazo.
     */
    public Card pop(int type, int number) {
        int id = getId(type, number);
        Card card = this.idToCard.get(id);
        this.idToCard.remove(id);
        return card;
    }

    /**
     * Retorna una lista con las cartas que el mazo
     * tenga en el momento.
     * @return Un lista de cartas.
     */
    public List<Card> getCardList() {
        List<Card> cardList = new ArrayList<Card>();
        for (byte i = 0; i < MAX_TYPE; i++) {
            for (byte j = MIN_NUMBER; j <= MAX_NUMBER; j++) {
                int id = getId(i, j);
                if (this.idToCard.containsKey(id)) {
                    cardList.add(idToCard.get(id));
                }
            }
        }
        return cardList;
    }

    public int getCardCount() {
        return cardCount;
    }

    private int getId(int cardType, int cardNumber) {
        return cardType * MAX_NUMBER + cardNumber;
    }

    public void printCards(List<Card> cardList) {
        printCards(cardList, "Cartas", true);
    }

    public void printCards(List<Card> cardList, String title, boolean byType) {
        if (byType) {
            System.out.println("--------------------------------");
            System.out.println(title);
            System.out.println("--------------------------------");
            for (int i = 0; i < 4; i++) {
                for (Card card : cardList) {
                    if (card.getType() == i) {
                        System.out.print(card + " ");
                    }
                }
                System.out.println();
            }
            System.out.println("Count: " + cardList.size());
            System.out.println("#################################");
        } else {
            System.out.println("--------------------------------");
            System.out.println(title);
            System.out.println("--------------------------------");
            for (Card card : cardList) {
                System.out.print(card + " ");
            }
            System.out.println("Count: " + cardList.size());
            System.out.println("#################################");
        }
    }

    public String getTypeAcronyms(int type) {
        switch (type) {
            case 0:
                return "H";
            case 1:
                return "D";
            case 2:
                return "S";
            case 3:
                return "C";
            default:
                return "";
        }
    }

    public String getNumberAcronyms(int number) {
        switch (number) {
            case 10:
                return "T";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            case 14:
                return "A";
            default:
                return Integer.toString(number);
        }
    }

    public void printHand(IHandType hand) {
        Card[] cardArray = hand.getCardArray();
        System.out.print(hand.getName() + " ");
        for(int i=0; i<cardArray.length; i++){
            System.out.print(cardArray[i] + " ");
        }
        System.out.println();
    }
}
