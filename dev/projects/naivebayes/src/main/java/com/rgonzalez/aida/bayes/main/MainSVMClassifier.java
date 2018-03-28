/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.main;

/**
 *
 * @author Administrador
 */
public class MainSVMClassifier {

    // {la clase resultado de la svm, la clase real a la que pertenece el doc}
    static int[][] values = {
        {0, 0},
        {1, 1},
        {1, 1},
        {2, 2},
        {4, 4},
        {6, 6},
        {2, 2},
        {2, 2},
        {5, 5},
        {4, 4},
        {4, 0},
        {1, 1},
        {2, 2},
        {5, 5},
        {5, 5},
        {6, 6},
        {1, 1},
        {5, 5},
        {1, 1},
        {6, 4},
        {4, 4},
        {1, 3},
        {0, 0},
        {6, 6},
        {2, 2},
        {1, 5},
        {1, 4},
        {2, 2},
        {2, 2},
        {1, 1},
        {1, 4},
        {0, 0},
        {6, 6},
        {1, 1},
        {5, 5},
        {3, 3},
        {0, 0},
        {0, 0},
        {3, 3},
        {0, 0},
        {1, 4},
        {0, 0},
        {5, 5},
        {6, 6},
        {6, 6},
        {1, 1},
        {1, 4},
        {1, 3},
        {1, 1},
        {5, 5},
        {3, 3},
        {1, 1},
        {2, 2},
        {2, 0},
        {1, 1},
        {1, 6},
        {1, 1},
        {6, 6},
        {3, 3},
        {4, 4},
        {3, 3},
        {5, 5},
        {4, 4},
        {1, 1},
        {3, 3},
        {1, 1},
        {0, 0},
        {1, 3},
        {6, 6},
        {1, 1},
        {1, 1},
        {6, 6},
        {1, 1},
        {4, 3},
        {2, 2},
        {6, 6},
        {1, 1},
        {2, 2},
        {1, 1},
        {1, 1},
        {3, 3},
        {1, 1},
        {1, 4},
        {3, 3},
        {1, 1},
        {2, 2},
        {0, 0},
        {1, 1},
        {3, 3},
        {6, 6}
    };

    public static void main(String[] args) {

        int[][] confusionMatrix = new int[7][7];

        int cont=0;
        int cont2=0;
        for (int i = 0; i < values.length; i++) {
            if(values[i][1]==values[i][0]){
                cont++;
            }
            else{
                cont2++;
            }
            confusionMatrix[values[i][1]][values[i][0]]++;
        }
        System.out.println(cont + "  " + cont2);

        for (int i = 0; i < 7; i++) {
            int contador = 0;
            for (int j = 0; j < 7; j++) {
                if (i == j) {
                    System.out.print("\\textbf{" + confusionMatrix[i][j] + "} & ");
                } else {
                    System.out.print(confusionMatrix[i][j] + "          & ");
                }
                contador += confusionMatrix[i][j];
            }
            System.out.println(((double) confusionMatrix[i][i] / (double) contador) * 100 + "\\% \\\\");
        }
    }
}
