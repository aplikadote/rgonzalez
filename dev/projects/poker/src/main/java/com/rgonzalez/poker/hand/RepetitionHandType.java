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
public abstract class RepetitionHandType extends AHandType {

    public RepetitionHandType(int value, Card[] cardArray) {
        super(value, cardArray);
    }

    /**
     * Retorna un arreglo 2-dimensional en donde la primera
     * dimension presenta el numero distinto del resto y la
     * segunda dimension presenta las veces que se repite.
     * <pre>
     *   cartas --> [2,2,3,3,4]
     *
     *   retorna --> [ [2,3,4],
     *                 [2,2,1] ]
     * <pre>
     * @return
     */
    public int[][] getDistinctCardNumbers() {
        int n = 1;
        Card previousCard = cardArray[0];
        for (int i = 1; i < cardArray.length; i++) {
            Card nextCard = cardArray[i];
            if (previousCard.getNumber() != nextCard.getNumber()) {
                n++;
            }
            previousCard = nextCard;
        }

        if (n == 1) {
            throw new IllegalArgumentException("Todas las cartas tiene el mismo numero " + this);
        }

        int[][] numbers = new int[2][n];
        previousCard = cardArray[0];
        numbers[0][0] = previousCard.getNumber();

        int j = 1;
        for (int i = 1; i < cardArray.length; i++) {
            Card nextCard = cardArray[i];
            if (previousCard.getNumber() != nextCard.getNumber()) {
                numbers[0][j] = nextCard.getNumber();
                j++;
            }
            previousCard = nextCard;
        }

        for (int i = 0; i < n; i++) {
            int number = numbers[0][i];
            numbers[1][i] = 0;
            for (j = 0; j < cardArray.length; j++) {
                Card card = cardArray[j];
                if (card.getNumber() == number) {
                    numbers[1][i]++;
                }
            }
        }

        int aux0, aux1;
        for (j = 0; j < n; j++) {
            for (int k = j + 1; k < n; k++) {
                if (numbers[1][j] > numbers[1][k]) {
                    aux1 = numbers[1][j];
                    numbers[1][j] = numbers[1][k];
                    numbers[1][k] = aux1;

                    aux0 = numbers[0][j];
                    numbers[0][j] = numbers[0][k];
                    numbers[0][k] = aux0;
                } else if (numbers[1][j] == numbers[1][k]) {
                    if (numbers[0][j] > numbers[0][k]) {
                        aux0 = numbers[0][j];
                        numbers[0][j] = numbers[0][k];
                        numbers[0][k] = aux0;
                    }
                }
            }
        }
        return numbers;
    }

    @Override
    public int compareByType(IHandType o) {
        RepetitionHandType rht = (RepetitionHandType) o;
        int[][] thisCardNumbers = getDistinctCardNumbers();
        int[][] oCardNumbers = rht.getDistinctCardNumbers();

        int n1 = thisCardNumbers[0].length;
        int n2 = oCardNumbers[0].length;

        if (n1 != n2) {
            for (int i = 0; i < thisCardNumbers.length; i++) {
                for (int j = 0; j < thisCardNumbers[0].length; j++) {
                    System.out.print(thisCardNumbers[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();

            for (int i = 0; i < oCardNumbers.length; i++) {
                for (int j = 0; j < oCardNumbers[0].length; j++) {
                    System.out.print(oCardNumbers[i][j] + " ");
                }
                System.out.println();
            }

            throw new IllegalArgumentException("El tamaño de los arreglos difiere");
        }

        int diff = 0;
        for (int i = n1 - 1; i >= 0; i--) {
            diff = thisCardNumbers[0][i] - oCardNumbers[0][i];
            if (diff != 0) {
                return diff;
            }
        }
        return diff;
    }
}
