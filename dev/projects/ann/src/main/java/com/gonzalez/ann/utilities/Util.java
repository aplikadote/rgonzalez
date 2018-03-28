/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.utilities;

import java.util.List;

/**
 *
 * @author Administrador
 */
public final class Util {

    public static double cross(double[] x, double[] y) {
        if (x.length != y.length) {
            System.out.println("Tamaños de los array diferentes " + x.length + " " + y.length);
            System.exit(0);
            return 0;
        } else {
            double suma = 0;
            for (int i = 0; i < x.length; i++) {
                suma += x[i] * y[i];
            }
            return suma;
        }
    }

    public static void print(double[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void print(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void print(List<? extends Object> list) {
        for (int j = 0; j < list.size(); j++) {
            System.out.println(list.get(j));
        }
        System.out.println();
    }
}
