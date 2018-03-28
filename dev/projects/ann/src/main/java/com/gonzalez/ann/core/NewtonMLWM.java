/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public class NewtonMLWM extends MultiLayerWeightMatrix{

    public NewtonMLWM(int index, int leftLayerSize, int rightLayerSize, double nu, InitValueType initValueWeight) {
        super(index, leftLayerSize, rightLayerSize, nu, initValueWeight);
    }

    public NewtonMLWM(int index, int leftLayerSize, int rightLayerSize, double[] biasWeight, double[][] neuronWeight, double[] errorGradientBiasWeight, double[][] errorGradientNeuronWeight, InitValueType initValueWeight) {
        super(index, leftLayerSize, rightLayerSize, biasWeight, neuronWeight, errorGradientBiasWeight, errorGradientNeuronWeight, initValueWeight);
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
            this.errorGradientBiasWeight[i] += rightErrorGradient[i] * rightFunction.getDerivate(rightOutput[i]);
            for (int j = 0; j < leftLayerSize; j++) {
//                this.errorGradientNeuronWeight[i][j] = errorGradient[i] * leftOutput[j];
                this.errorGradientNeuronWeight[i][j] += rightErrorGradient[i] * rightFunction.getDerivate(rightOutput[i]) * leftOutput[j];
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
        for (int i = 0; i < rightLayerSize; i++) {
            this.biasWeight[i] = this.biasWeight[i] - nu * this.errorGradientBiasWeight[i];
            for (int j = 0; j < leftLayerSize; j++) {
                this.neuronWeight[i][j] = this.neuronWeight[i][j] - nu * this.errorGradientNeuronWeight[i][j];
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

        for (int i = 0; i < rightLayerSize; i++) {
            myBiasWeight[i] = this.biasWeight[i];
            for (int j = 0; j < leftLayerSize; j++) {
                myNeuronWeight[i][j] = this.neuronWeight[i][j];
            }
        }
        return new NewtonMLWM(myIndex, myLeftLayerSize, myRightLayerSize, myBiasWeight, myNeuronWeight, myErrorGradientBiasWeight, myErrorGradientNeuronWeight, myInit);
    }
}
