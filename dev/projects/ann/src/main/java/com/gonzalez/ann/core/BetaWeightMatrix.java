/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

import java.util.Random;

/**
 *
 * @author rene
 */
public class BetaWeightMatrix implements WeightMatrix {

    private double[] weightBeta;
    private InitValueType init;

    public BetaWeightMatrix(int zNodesSize, InitValueType init) {
        Random r = new Random(System.currentTimeMillis());
        //creacion e inicializacion de la matriz beta
        this.weightBeta = new double[zNodesSize + 1];
        this.init = init;
        initValues();
    }

    public BetaWeightMatrix(double[] weightBeta, InitValueType init) {
        this.weightBeta = weightBeta;
        this.init = init;
    }

    public double[] getMatrix() {
        return this.weightBeta;
    }

    public WeightMatrix clone() {
        int row = weightBeta.length;
        double[] myWeightBeta = new double[row];
        InitValueType myInit = this.init;

        for (int i = 0; i < row; i++) {
            myWeightBeta[i] = weightBeta[i];
        }
        return new BetaWeightMatrix(myWeightBeta, myInit);
    }

    public void initValues() {
        for (int i = 0; i < weightBeta.length; i++) {
            weightBeta[i] = init.getInitValue();
        }
    }
}
