/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public class MomentumMLWM extends MultiLayerWeightMatrix {

    private double[][] lastNeuronWeight;
    private double[] lastBiasWeight;
    private double[][] lastDeltaNeuronWeight;
    private double[] lastDeltaBiasWeight;
    private double cf;
    private double alpha;

    public MomentumMLWM(int index, int leftLayerSize, int rightLayerSize, double nu, InitValueType init, double cf, double alpha) {
        super(index, leftLayerSize, rightLayerSize, nu, init);
        this.cf = cf;
        this.alpha = alpha;

        this.lastBiasWeight = new double[rightLayerSize];
        this.lastNeuronWeight = new double[rightLayerSize][leftLayerSize];
        this.lastDeltaBiasWeight = new double[rightLayerSize];
        this.lastDeltaNeuronWeight = new double[rightLayerSize][leftLayerSize];
        
        for (int i = 0; i < rightLayerSize; i++) {
            this.lastDeltaBiasWeight[i] = 0;
            for (int j = 0; j < leftLayerSize; j++) {
                this.lastDeltaNeuronWeight[i][j] = 0;
            }
        }
    }

    public MomentumMLWM(int index, int leftLayerSize, int rightLayerSize, double[] biasWeight, double[][] neuronWeight, double[] errorGradientBiasWeight, double[][] errorGradientNeuronWeight, InitValueType init, double cf, double alpha) {
        super(index, leftLayerSize, rightLayerSize, biasWeight, neuronWeight, errorGradientBiasWeight, errorGradientNeuronWeight, init);
        this.cf = cf;
        this.alpha = alpha;
    }

    public void setGradientError(Layer leftLayer, DefaultLayer rightLayer) {
        double[] rightErrorGradient = rightLayer.getErrorGradient();
        double[] rightOutput = rightLayer.getOutput();
        double[] leftOutput = leftLayer.getOutput();
        Function rightFunction = rightLayer.getFunction();

        for (int i = 0; i < rightLayerSize; i++) {
            this.errorGradientBiasWeight[i] = 0;
            for (int j = 0; j < leftLayerSize; j++) {
                this.errorGradientNeuronWeight[i][j] = 0;
            }
        }

        for (int i = 0; i < rightLayerSize; i++) {
//            this.errorGradientBiasWeight[i] = errorGradient[i];
            this.errorGradientBiasWeight[i] += rightErrorGradient[i] * (rightFunction.getDerivate(rightOutput[i]) + cf);
            for (int j = 0; j < leftLayerSize; j++) {
//                this.errorGradientNeuronWeight[i][j] = errorGradient[i] * leftOutput[j];
                this.errorGradientNeuronWeight[i][j] += rightErrorGradient[i] * (rightFunction.getDerivate(rightOutput[i]) + cf) * leftOutput[j];
            }
        }

//        if (RegresionNeuralNetwork.DEBUG2) {
//            DecimalFormat format = new DecimalFormat("#.00");
//            System.out.println("Error Weight " + (index + 1));
//            for (int i = 0; i < rightLayerSize; i++) {
//                System.out.print("  " + format.format(errorGradientBiasWeight[i]) + "  ");
//                for (int j = 0; j < leftLayerSize; j++) {
//                    System.out.print(format.format(errorGradientNeuronWeight[i][j]) + "  ");
//                }
//                System.out.println();
//            }
//        }

    }

    public void updateWeights() {

        //Copiando pesos
        for (int i = 0; i < rightLayerSize; i++) {
            this.lastBiasWeight[i] = this.biasWeight[i];
            for (int j = 0; j < leftLayerSize; j++) {
                this.lastNeuronWeight[i][j] = this.neuronWeight[i][j];
            }
        }

        //Actualizando pesos
        for (int i = 0; i < rightLayerSize; i++) {
            this.biasWeight[i] = this.biasWeight[i] - nu * this.errorGradientBiasWeight[i] + alpha * this.lastDeltaBiasWeight[i];
            for (int j = 0; j < leftLayerSize; j++) {
                this.neuronWeight[i][j] = this.neuronWeight[i][j] - nu * this.errorGradientNeuronWeight[i][j] + alpha * this.lastDeltaNeuronWeight[i][j];
            }
        }

        //Actualizando deltas
        for (int i = 0; i < rightLayerSize; i++) {
            this.lastDeltaBiasWeight[i] = this.biasWeight[i] - this.lastBiasWeight[i];
            for (int j = 0; j < leftLayerSize; j++) {
                this.lastDeltaNeuronWeight[i][j] = this.neuronWeight[i][j] - this.lastNeuronWeight[i][j];
            }
        }

//        if (RegresionNeuralNetwork.DEBUG2) {
//            print();
//        }
    }

    public WeightMatrix clone() {
        int myIndex = this.index;
        int myLeftLayerSize = this.leftLayerSize;
        int myRightLayerSize = this.rightLayerSize;
        double[] myBiasWeight = new double[rightLayerSize];
        double[][] myNeuronWeight = new double[rightLayerSize][leftLayerSize];
        double[] myErrorGradientBiasWeight = new double[rightLayerSize];
        double[][] myErrorGradientNeuronWeight = new double[rightLayerSize][leftLayerSize];
        InitValueType myInit = this.initValueWeight;
        double myCf = this.cf;
        double myAlpha = this.alpha;


        for (int i = 0; i < rightLayerSize; i++) {
            myBiasWeight[i] = this.biasWeight[i];
            for (int j = 0; j < leftLayerSize; j++) {
                myNeuronWeight[i][j] = this.neuronWeight[i][j];
            }
        }
        return new MomentumMLWM(myIndex, myLeftLayerSize, myRightLayerSize, myBiasWeight, myNeuronWeight, myErrorGradientBiasWeight, myErrorGradientNeuronWeight, myInit, myCf, myAlpha);
    }
}
