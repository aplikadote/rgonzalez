/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.robustez.estimadores.cetamen;

import java.util.Arrays;

/**
 *
 * @author Administrador
 */
public class Quartile {

    public static double[] data = {
        28, 26, 33, 24, 34, -44, 27, 16, 40, -2,
        29, 22, 24, 21, 25, 30, 23, 29, 31, 19
    };
//    public static double[] data = {
//        2.20, 2.20, 2.40, 2.40, 2.50, 2.70, 2.80, 2.90,
//        3.03, 3.03, 3.10, 3.37, 3.40, 3.40, 3.40, 3.50,
//        3.60, 3.70, 3.70, 3.70, 3.70, 3.77, 5.28, 28.95
//    };
    public static double mean;
    public static double median;
    public static double sd;
    public static double madn;
    public static double[] ti_mean;
    public static double[] ti_median;

    public static void main(String[] args) {

        Arrays.sort(data);

        mean = 0;
        for (int i = 0; i < data.length; i++) {
            mean += data[i];
        }
        mean /= data.length;

        sd = 0;
        for (int i = 0; i < data.length; i++) {
            sd += Math.pow(data[i] - mean, 2);
        }
        sd /= (data.length - 1);
        sd = Math.sqrt(sd);

        if (data.length % 2 == 0) {
            median = (data[data.length / 2] + data[(data.length / 2) - 1]) / 2;
        } else {
            median = data[(data.length - 1) / 2];
        }

        madn = 0;
        double[] sdData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            sdData[i] = Math.abs(data[i] - median);
        }
        Arrays.sort(sdData);
        if (data.length % 2 == 0) {
            madn = ((sdData[data.length / 2] + sdData[(data.length / 2) - 1]) / 2) / 0.6745;
        } else {
            madn = sdData[(data.length - 1) / 2];
        }

        System.out.println(mean);
        System.out.println(sd);
        System.out.println(median);
        System.out.println(madn);
    }
}
