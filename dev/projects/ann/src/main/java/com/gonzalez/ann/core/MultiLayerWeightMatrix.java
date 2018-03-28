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
public abstract class MultiLayerWeightMatrix implements WeightMatrix {

    protected int leftLayerSize;
    protected int rightLayerSize;
    protected double[][] neuronWeight;
    protected double[] biasWeight;
    protected double[][] errorGradientNeuronWeight;
    protected double[] errorGradientBiasWeight;
    protected int index;
    protected double nu;
    protected InitValueType initValueWeight;

    public abstract void setGradientError(Layer leftLayer, DefaultLayer rightLayer);

    public abstract void updateWeights();

    public abstract WeightMatrix clone();

    /**
     * El tamaño del biasWeight es el mismo
     * que la cantidad de filas (neuronas) que la
     * matriz de pesos
     * @param leftLayer
     * @param rightLayer
     */
    public MultiLayerWeightMatrix(int index, int leftLayerSize, int rightLayerSize, double nu, InitValueType initValueWeight) {
        this.index = index;
        this.leftLayerSize = leftLayerSize;
        this.rightLayerSize = rightLayerSize;
        this.nu = nu;
        this.initValueWeight = initValueWeight;

        this.biasWeight = new double[rightLayerSize];
        this.errorGradientBiasWeight = new double[rightLayerSize];
        this.neuronWeight = new double[rightLayerSize][leftLayerSize];
        this.errorGradientNeuronWeight = new double[rightLayerSize][leftLayerSize];

        initValues();
    }

    public MultiLayerWeightMatrix(int index, int leftLayerSize, int rightLayerSize, double[] biasWeight, double[][] neuronWeight, double[] errorGradientBiasWeight, double[][] errorGradientNeuronWeight, InitValueType init) {
        this.index = index;
        this.leftLayerSize = leftLayerSize;
        this.rightLayerSize = rightLayerSize;
        this.biasWeight = biasWeight;
        this.neuronWeight = neuronWeight;
        this.errorGradientBiasWeight = errorGradientBiasWeight;
        this.errorGradientNeuronWeight = errorGradientNeuronWeight;
        this.initValueWeight = init;
    }

    public void initValues() {
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < rightLayerSize; i++) {
            this.biasWeight[i] = initValueWeight.getInitValue();
            for (int j = 0; j < leftLayerSize; j++) {
                this.neuronWeight[i][j] = initValueWeight.getInitValue();
            }
        }
    }

    public double[][] getNeuralWeight() {
        return this.neuronWeight;
    }

    public double[] getBiasWeight() {
        return this.biasWeight;
    }

    public void print() {
        System.out.println("Weight " + (index + 1));
        for (int i = 0; i < rightLayerSize; i++) {
            System.out.print("  " + biasWeight[i]);
            for (int j = 0; j < leftLayerSize; j++) {
                System.out.print("  " + neuronWeight[i][j]);
            }
            System.out.println();
        }
    }
}
