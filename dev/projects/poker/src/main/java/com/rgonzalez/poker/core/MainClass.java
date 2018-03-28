/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.core;

import com.rgonzalez.poker.combinatory.Combinatory;
import com.rgonzalez.poker.visitor.AdversairesVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class MainClass {

//    private int anotherPlayerNumber = 1;
    private List<Card> cardList;
//    private List<Player> anotherPlayers;
    private Card firstCard;
    private Card secondCard;
    private int nPlayers;

    public MainClass(Card firstCard, Card secondCard, int nPlayers) {
        this.cardList = new ArrayList<Card>();
        this.firstCard = firstCard;
        this.secondCard = secondCard;
        this.nPlayers = nPlayers;

        Deck deck = Deck.getInstance();
        this.cardList = deck.getCardList();
//        deck.printCards(cardList);

//        Collections.shuffle(cardList);
        calculate();
    }

    /**
     * Se crea el objeto <code>TableData</code> que evaluara
     * un juego de la mesa por vez. A este objeto se
     * le asigna las cartas del jugador.
     * <BR> De las cartas restantes, luego de quitar las
     * cartas de la baraja las cartas del jugador, se obtiene
     * el numero de cartas en juego, que es la cantidad de cartas
     * que los contricantes tendra (2 * # de contrincantes). Con ello
     * se crean las combinatorias de las cartas que quedan respecto
     * al numero calculado anteriormente. La combinatoria en este caso
     * crea una copia de las cartas originales, dado que en la clase
     * <code>AdversairesVisitor</code> se eliminan y agregan cartas, lo
     * cual podria afectar al flujo del algoritmo.
     */
    private void calculate() {
        long time = System.currentTimeMillis();

        int nCardToComb = 2 * nPlayers;
        TableData tableData = new TableData(firstCard, secondCard);
        Combinatory combinatory = new Combinatory(cardList, nCardToComb);
        combinatory.accept(new AdversairesVisitor(cardList, tableData));

        time = System.currentTimeMillis() - time;
        System.out.println("TIEMPO: " + time);
    }

    public static void main(String[] args) {
        Deck deck = Deck.getInstance();
        Card card1 = deck.pop(Deck.HEART, Deck.AS);
        Card card2 = deck.pop(Deck.SPADE, Deck.AS);
        new MainClass(card1, card2, 1);
    }
}
