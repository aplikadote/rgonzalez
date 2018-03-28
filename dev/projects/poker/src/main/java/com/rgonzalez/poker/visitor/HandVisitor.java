/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.visitor;

import java.util.List;
import com.rgonzalez.poker.combinatory.ICombinatoryVisitor;
import com.rgonzalez.poker.core.Card;
import com.rgonzalez.poker.core.Deck;
import com.rgonzalez.poker.hand.HandTypeFactory;
import com.rgonzalez.poker.hand.IHandType;

/**
 *
 * @author Administrador
 */
public class HandVisitor implements ICombinatoryVisitor<Card> {

    private IHandType bestHand;
//    private int i = 0;

    public HandVisitor() {
        bestHand = null;
    }

    public void visit(List<Card> comb) {
//        Deck.getInstance().printCards(comb, "Comb " + (i + 1) + "", false);
//        i++;
        if (bestHand == null) {
            bestHand = HandTypeFactory.create(comb);
        } else {
            IHandType handType = HandTypeFactory.create(comb);
            if (handType.compareTo(bestHand) > 0) {
                bestHand = handType;
            }
        }
    }

    public IHandType getBestHand() {
        return this.bestHand;
    }

//    public int getCount() {
//        return this.i;
//    }
}
