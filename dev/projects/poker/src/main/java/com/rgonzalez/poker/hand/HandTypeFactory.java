/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.hand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.rgonzalez.poker.core.Card;
import com.rgonzalez.poker.core.CardNumberComparator;
import com.rgonzalez.poker.core.PokerConstants;

/**
 *
 * @author Administrador
 */
public class HandTypeFactory {

    public static IHandType create(List<Card> cardList) {
        Card[] cardArray = new Card[5];
        for (int i = 0; i < 5; i++) {
            cardArray[i] = cardList.get(i).cloneCard();
        }
        Arrays.sort(cardArray, CardNumberComparator.getInstance());
        int[] cardNumber = createCardNumberArray(cardArray);
        int[] cardType = createCardTypeArray(cardArray);

        if (HandTypeFactory.isStraight(cardNumber)) {
            if (HandTypeFactory.isFlush(cardType)) {
                return new StraightFlush(cardArray);
            } else {
                return new Straight(cardArray);
            }
        } else {
            if (HandTypeFactory.isFlush(cardType)) {
                return new Flush(cardArray);
            } else {
                if (HandTypeFactory.isFourOfAKind(cardNumber)) {
                    return new FourOfAKind(cardArray);
                } else if (HandTypeFactory.isFullHouse(cardNumber)) {
                    return new FullHouse(cardArray);
                } else if (HandTypeFactory.isThreeOfAKind(cardNumber)) {
                    return new ThreeOfAKind(cardArray);
                } else if (HandTypeFactory.isTwoPairs(cardNumber)) {
                    return new TwoPair(cardArray);
                } else if (HandTypeFactory.isOnePair(cardNumber)) {
                    return new OnePair(cardArray);
                } else {
                    return new HighCard(cardArray);
                }
            }
        }
    }

    public static boolean isStraight(int[] cardNumber) {
        if (cardNumber[4] == PokerConstants.KING && cardNumber[0] == PokerConstants.AS) {
            return isStraightAdjusted(cardNumber, 1, 4);
        } else {
            return isStraightAdjusted(cardNumber, 0, 4);
        }
    }

    public static boolean isFlush(int[] cardType) {
        return areEquals(cardType, cardType[0], 1, 4);
    }

    public static boolean isFourOfAKind(int[] cardNumber) {
        int card1 = cardNumber[0];
        int card2 = cardNumber[1];
        if (card1 != card2) {
            return areEquals(cardNumber, card2, 2, 4);
        } else {
            return areEquals(cardNumber, card1, 1, 3);
        }
    }

    public static boolean isFullHouse(int[] cardNumber) {
        int card1 = cardNumber[0];
        int card2 = cardNumber[1];
        int card3 = cardNumber[2];
        int card4 = cardNumber[3];
        int card5 = cardNumber[4];

        if (card1 == card3) {
            if (card2 == card1) {
                return card4 == card5;
            } else {
                return false;
            }
        } else {
            if (card1 == card2) {
                return areEquals(cardNumber, card3, 2, 4);
            } else {
                return false;
            }
        }
    }

    public static boolean isThreeOfAKind(int[] cardNumber) {
        int card1 = cardNumber[0];
        int card2 = cardNumber[1];
        int card3 = cardNumber[2];
        int card4 = cardNumber[3];

        if (card1 == card2) {
            if (card2 == card3) {
                return true;
            } else {
                return false;
            }
        } else if (card2 == card3) {
            if (card3 == card4) {
                return true;
            } else {
                return false;
            }
        } else {
            return areEquals(cardNumber, card3, 2, 4);
        }
    }

    public static boolean isTwoPairs(int[] cardNumber) {
        int card1 = cardNumber[0];
        int card2 = cardNumber[1];
        int card3 = cardNumber[2];
        int card4 = cardNumber[3];
        int card5 = cardNumber[4];

        if (card1 == card2) {
            if (card3 == card4) {
                return true;
            } else {
                if (card4 == card5) {
                    return true;
                } else {
                    return false;
                }
            }
        } else if (card2 == card3) {
            if (card4 == card5) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isOnePair(int[] cardNumber) {
        int card1 = cardNumber[0];
        int card2 = cardNumber[1];
        int card3 = cardNumber[2];
        int card4 = cardNumber[3];
        int card5 = cardNumber[4];

        if (card1 == card2) {
            return true;
        }

        if (card2 == card3) {
            return true;
        }

        if (card3 == card4) {
            return true;
        }

        if (card4 == card5) {
            return true;
        }
        return false;
    }

    public static int[] createCardNumberArray(Card[] cardArray) {
        int[] cardNumber = new int[5];
        for (int i = 0; i < 5; i++) {
            cardNumber[i] = cardArray[i].getNumber();
        }
        return cardNumber;
    }

    public static int[] createCardTypeArray(Card[] cardArray) {
        int[] cardType = new int[5];
        for (int i = 0; i < 5; i++) {
            cardType[i] = cardArray[i].getType();
        }
        return cardType;
    }

    private static boolean isStraightAdjusted(int[] cardNumber, int i0, int i1) {
        int minCard = cardNumber[i0];
        for (int i = i0 + 1; i < i1; i++) {
            if ((minCard + i) != cardNumber[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compara si los elementos del array tiene el mismo valor, recorriendo
     * desde el indice 'begin' hasta el indice 'end', incluyendolo.
     * @param cardNumber El arreglo de numeros.
     * @param toCompare El numero con que se desea comparar.
     * @param begin El indice inicial del array.
     * @param end El indice final del array
     * @return TRUE, si los elementos son iguales; FAlSE, en caso contrario.
     */
    private static boolean areEquals(int[] cardNumber, int toCompare, int begin, int end) {
        for (int i = begin; i <= end; i++) {
            if (toCompare != cardNumber[i]) {
                return false;
            }
        }
        return true;
    }
}
