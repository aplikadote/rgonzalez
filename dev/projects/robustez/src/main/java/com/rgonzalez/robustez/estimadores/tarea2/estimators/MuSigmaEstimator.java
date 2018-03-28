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
public class MuSigmaEstimator {

    private double delta = 0.99;
    private int K = 30;
    private static int POINTS = 1000;
    private static double LOWER = 0.01;
    private static double UPPER = 10;
    double[][] mu = new double[POINTS][2];
    double[][] sigma = new double[POINTS][2];

    public MuSigmaEstimator(double delta, int k) {
        update(delta, k);
    }

    public void update(double delta, int k) {
        this.delta = delta;
        this.K = k;
        double rate = (UPPER - LOWER) / POINTS;
        for (int i = 0; i < POINTS; i++) {
            double k_huber = rate * i + LOWER;

            mu[i][0] = k_huber;
            mu[i][1] = getIterativeMuSigma(k_huber)[0];
            sigma[i][0] = k_huber;
            sigma[i][1] = getIterativeMuSigma(k_huber)[1];
        }
    }

    public double[][] getMuData() {
        return this.mu;
    }

    public double[][] getSigmaData() {
        return this.sigma;
    }

    private double[] getIterativeMuSigma(double k_huber) {
        double muAnt = Statistic.getMedian(MainFrame.data);
        double muNext = 0;
        double sigmaAnt = Statistic.getMadn(MainFrame.data);
        double sigmaNext = 0;

        for (int k = 0; k < K; k++) {
            muNext = getMu(MainFrame.data, muAnt, sigmaAnt, k_huber);
            sigmaNext = getSigma(MainFrame.data, muAnt, sigmaAnt, k_huber);

            muAnt = muNext;
            sigmaAnt = sigmaNext;
        }

        double[] result = new double[2];
        result[0] = muNext;
        result[1] = sigmaNext;
        return result;
    }

    public double getMu(double myData[], double muAnt, double sigmaAnt, double k_huber) {
        double sumNum = 0;
        double sumDen = 0;
        for (int i = 0; i < myData.length; i++) {
            double weight = getWeightLocation((myData[i] - muAnt) / sigmaAnt, k_huber);
            sumDen += weight;
            sumNum += weight * myData[i];
        }
        return sumNum / sumDen;
    }

    public double getSigma(double myData[], double muAnt, double sigmaAnt, double k_huber) {
        double sum = 0;
        for (int i = 0; i < myData.length; i++) {
            double r = (myData[i] - muAnt) / sigmaAnt;
            double weight = getWeightScale(r, k_huber);
            sum += weight * Math.pow(r, 2);
        }
        return Math.pow((Math.pow(sigmaAnt, 2) * sum) / (myData.length * delta), 0.5);
    }

    public double getWeightLocation(double x, double k_huber) {

        if (Math.abs(x) > k_huber) {
            return (Math.signum(x) * k_huber) / x;
        } else {
            return 1;
        }
    }

    public double getWeightScale(double x, double k_huber) {
        if (Math.abs(x) > k_huber) {
            return (2 * k_huber * Math.abs(x) - Math.pow(k_huber, 2)) / Math.pow(x, 2);
        } else {
            return 1;
        }
    }
}
