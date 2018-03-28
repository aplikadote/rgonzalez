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
public class SigmaEstimator {

    private double delta;
    private double epsilon;
    private static int POINTS = 1000;
    private static double LOWER = 0.01;
    private static double UPPER = 10;
    private double k_hubert;
    double[][] sigma;

    public SigmaEstimator(double epsilon, double delta) {
        update(epsilon, delta);
    }

    public void update(double epsilon, double delta) {
        this.epsilon = epsilon;
        this.delta = delta;

        double sigma_0 = Statistic.getMadn(MainFrame.data);
        sigma = new double[POINTS][2];
        double rate = (UPPER - LOWER) / POINTS;

        for (int i = 0; i < POINTS; i++) {
            boolean flag = true;
            double sigmaAnt = sigma_0;
            double sigmaNext = 0;
            k_hubert = rate * i + LOWER;
            while (flag) {
                sigmaNext = getSigma(MainFrame.data, sigmaAnt);
                if (Math.abs((sigmaNext / sigmaAnt) - 1) < epsilon) {
                    flag = false;
                }
                sigmaAnt = sigmaNext;
            }
            sigma[i][0] = k_hubert;
            sigma[i][1] = sigmaNext;

        }
    }

    private double getSigma(double myData[], double sigmaAnt) {
        double sumNum = 0;
        for (int i = 0; i < myData.length; i++) {
            double weight = getWeightScale(myData[i] / sigmaAnt);
            sumNum += weight * Math.pow(myData[i], 2);
        }
        return Math.pow(sumNum / myData.length * delta, 0.5);
    }

    private double getWeightScale(double x) {
        if (Math.abs(x) > k_hubert) {
            return (2 * k_hubert * Math.abs(x) - Math.pow(k_hubert, 2)) / Math.pow(x, 2);
        } else {
            return 1;
        }
    }
    
    public double[][] getData(){
        return sigma;
    }
}
