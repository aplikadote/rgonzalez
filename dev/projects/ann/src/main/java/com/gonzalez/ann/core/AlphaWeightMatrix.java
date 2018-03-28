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
public class AlphaWeightMatrix implements WeightMatrix{

    private double[][] weightAlpha;
    private InitValueType init;

    public AlphaWeightMatrix(int zNodesSize, int xSize, InitValueType init) {
        Random r = new Random(System.currentTimeMillis());
        //creacion e inicializacion de la matriz alpha
        this.weightAlpha = new double[zNodesSize][xSize + 1];
        this.init = init;
        initValues();
    }

    public AlphaWeightMatrix(double[][] weightAlpha, InitValueType init) {
        this.weightAlpha = weightAlpha;
        this.init = init;
    }

    public double[][] getMatrix(){
        return this.weightAlpha;
    }

    public WeightMatrix clone() {
        int row = this.weightAlpha.length;
        int col = this.weightAlpha[0].length;
        double[][] miWeightAlpha = new double[row][col];
        InitValueType myInit = init;

        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                miWeightAlpha[i][j] = this.weightAlpha[i][j];
            }
        }

        return new AlphaWeightMatrix(miWeightAlpha, myInit);
    }

    public void initValues() {
        for (int i = 0; i < weightAlpha.length; i++) {
            for (int j = 0; j < weightAlpha[i].length; j++) {
                weightAlpha[i][j] = init.getInitValue();
            }
        }
    }

}
