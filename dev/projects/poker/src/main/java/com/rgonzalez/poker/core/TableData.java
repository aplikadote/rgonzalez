/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.core;

import java.util.ArrayList;
import java.util.List;
import com.rgonzalez.poker.combinatory.Combinatory;
import com.rgonzalez.poker.visitor.HandVisitor;

/**
 *
 * @author Administrador
 */
public class TableData {

    /**
     * La primera carta de la mano.
     */
    private Card fistCard;
    /**
     * La segunda carta de la mano.
     */
    private Card secondCard;
    /**
     * Las cartas de los jugadores. Si
     * son n jugadores, la lista contendra
     * 2*n cartas, 2 cartas por jugador.
     */
    private List<Card> adversariesComb;
    /**
     * Las 5 cartas del flop
     */
    private List<Card> flopComb;
    /**
     * Un arreglo de tamaño 7 que contiene
     * las 2 cartas de la mano mas las cartas
     * del flop.
     */
    private List<Card> playerCardList;
    /**
     * Un arreglo de tamaño 7 que contiene
     * las 2 cartas de la mano de un jugador
     * mas las cartas del flop.
     */
    private List<Card> adversarieCardList;
    private int allCombination = 0;
    private int win = 0;

    public TableData(Card firstCard, Card secondCard) {
        this.fistCard = firstCard;
        this.secondCard = secondCard;
        this.playerCardList = new ArrayList<Card>();
        this.adversarieCardList = new ArrayList<Card>();
    }

    public void setAdversariesCombs(List<Card> adversariesComb) {
        this.adversariesComb = adversariesComb;
    }

    public void setFlopComb(List<Card> flopComb) {
        this.flopComb = flopComb;
    }

    public void process() {
//        System.out.println(fistCard + " " + secondCard + " " + adversariesComb + " " + flopComb);

        this.playerCardList.clear();
        this.playerCardList.add(fistCard);
        this.playerCardList.add(secondCard);
        this.playerCardList.addAll(flopComb);

        HandVisitor playerVisitor = new HandVisitor();
//        Deck.getInstance().printCards(playerCardList, "Player combs", false);
        Combinatory<Card> combinatory = new Combinatory<Card>(playerCardList, 5);
        combinatory.accept(playerVisitor);

//        System.out.println(playerVisitor.getCount());
//        Deck.getInstance().printHand(playerVisitor.getBestHand());

        HandVisitor adversarieVisitor = new HandVisitor();
        boolean lose = false;
        for (int i = 0; i < adversariesComb.size() && !lose; i = i + 2) {
            this.adversarieCardList.clear();
            this.adversarieCardList.add(adversariesComb.get(i));
            this.adversarieCardList.add(adversariesComb.get(i + 1));
            this.adversarieCardList.addAll(flopComb);

            combinatory = new Combinatory<Card>(adversarieCardList, 5);
            combinatory.accept(adversarieVisitor);

            if (playerVisitor.getBestHand().compareTo(adversarieVisitor.getBestHand()) < 0) {
                lose = true;
            }
        }

        if (!lose) {
            win++;
        }

        allCombination++;
    }

    public double getResult() {
        return (double) win / (double) allCombination;
    }

    public static void main(String[] args) {
        Card p1 = new Card(PokerConstants.HEART, PokerConstants.AS);
        Card p2 = new Card(PokerConstants.SPADE, PokerConstants.AS);

        Card a1 = new Card(PokerConstants.HEART, PokerConstants.TWO);
        Card a2 = new Card(PokerConstants.HEART, PokerConstants.THREE);
        List<Card> aCards = new ArrayList<Card>();
        aCards.add(a1);
        aCards.add(a2);

        Card f1 = new Card(PokerConstants.HEART, PokerConstants.FOUR);
        Card f2 = new Card(PokerConstants.HEART, PokerConstants.FIVE);
        Card f3 = new Card(PokerConstants.HEART, PokerConstants.SIX);
        Card f4 = new Card(PokerConstants.DIAMOND, PokerConstants.TWO);
        Card f5 = new Card(PokerConstants.SPADE, PokerConstants.TWO);
        List<Card> fCards = new ArrayList<Card>();
        fCards.add(f1);
        fCards.add(f2);
        fCards.add(f3);
        fCards.add(f4);
        fCards.add(f5);

        TableData td = new TableData(p1, p2);
        td.setAdversariesCombs(aCards);
        td.setFlopComb(fCards);
        td.process();

    }
}


