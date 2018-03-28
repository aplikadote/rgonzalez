/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.robustez.estimadores.tarea2.estimators;

import com.rgonzalez.robustez.estimadores.tarea2.MainFrame;
import com.rgonzalez.robustez.estimadores.tarea2.Statistic;

/**
 *
 * @author Administrador
 */
public class MuEstimator {

    private double epsilon;
    private static int POINTS = 1000;
    private static double LOWER = 0.01;
    private static double UPPER = 60;
    private static double RATE = (UPPER - LOWER) / POINTS;
    private double[][] mu = new double[POINTS][2];

    public MuEstimator(double epsilon) {
        update(epsilon);
    }
    
    public void update(double epsilon){
        this.epsilon = epsilon;
        for (int i = 0; i < POINTS; i++) {
            double k_huber = RATE * i + LOWER;
            mu[i][0] = k_huber;
            mu[i][1] = getIterativeMu(k_huber);
        }
    }
    
    public double[][] getData(){
        return mu;
    }

    private double getIterativeMu(double k_huber) {
        double muAnt = 0;
        double muNext = Statistic.getMedian(MainFrame.data);
        double sigma = Statistic.getMadn(MainFrame.data);
        do {
            muAnt = muNext;
            muNext = getMu(MainFrame.data, muAnt, sigma, k_huber);
        } while (Math.abs(muNext - muAnt) > epsilon * sigma);
        return muNext;
    }

    private double getMu(double myData[], double muAnt, double sigma, double k_huber) {
        double sumNum = 0;
        double sumDen = 0;
        for (int i = 0; i < myData.length; i++) {
            double weight = getWeightLocation((myData[i] - muAnt) / sigma, k_huber);
            sumDen += weight;
            sumNum += weight * myData[i];
        }

        return sumNum / sumDen;
    }

    private double getWeightLocation(double x, double k_huber) {

        double value = k_huber / Math.abs(x);
        if (value < 1) {
            return value;
        } else {
            return 1;
        }
    }
}
