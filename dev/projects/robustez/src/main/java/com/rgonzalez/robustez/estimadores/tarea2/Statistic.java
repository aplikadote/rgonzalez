/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.robustez.estimadores.tarea2;

import Jama.Matrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Administrador
 */
public final class Statistic {

    public static double getMedian(double[] myData) {
        Arrays.sort(myData);
        int n = myData.length;
        if (n % 2 == 0) {
            return (myData[n / 2 - 1] + myData[n / 2]) / 2;
        } else {
            return myData[(n + 1) / 2];
        }
    }

    public static double getMadn(double[] myData) {
        double median = getMedian(myData);
        double[] dataAux = new double[myData.length];
        for (int i = 0; i < myData.length; i++) {
            dataAux[i] = Math.abs(myData[i] - median);
        }

        double myMedian = getMedian(dataAux);
        return myMedian / 0.6745;
    }

    public static double getMean(double[] myData) {
        double sum = 0;
        for (int i = 0; i < myData.length; i++) {
            sum += myData[i];
        }
        return sum / myData.length;
    }

    public static double getStdDesv(double[] myData) {
        double mean = getMean(myData);
        double sum = 0;
        for (int i = 0; i < myData.length; i++) {
            sum += Math.pow(myData[i] - mean, 2);
        }
        return Math.pow(sum / (myData.length-1), 0.5);
    }
    
    public static double getPsiHuber(double value, double k_huber) {
        if (Math.abs(value) <= k_huber) {
            return value;
        } else if (value < -k_huber) {
            return -k_huber;
        } else {
            return k_huber;
        }
    }
    
    public static double getMuestralMedian(Matrix beta, Matrix x ,Matrix y) {
        Matrix r = y.minus(x.times(beta));

        double[][] rArray = r.getArray();
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < rArray.length; i++) {
            for (int j = 0; j < rArray[i].length; j++) {
                if(rArray[i][0]!=0){
                    list.add( Math.abs(rArray[i][0]));
                }
            }
        }
        
        double[] array = new double[list.size()];
        for(int i=0; i<array.length; i++){
            array[i] = list.get(i).doubleValue();
        }
        
        return Statistic.getMedian(array) / 0.6745;
    }
}
