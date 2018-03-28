/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public class SuperSABMLWM extends MultiLayerWeightMatrix {

    private double[] backBiasWeight;
    private double[][] backNeuronWeight;
    private double[] actualDeltaBiasWeight;
    private double[][] actualDeltaNeuronWeight;
    private double[] backDeltaBiasWeight;
    private double[][] backDeltaNeuronWeight;
    private double[] nuBiasWeight;
    private double[][] nuNeuronWeight;
    private double cf;
    private double alpha;
    private double increaseFactor;
    private double decreaseFactor;

    public SuperSABMLWM(int index, int leftLayerSize, int rightLayerSize, double nu, InitValueType initValueWeight, double cf, double alpha, double increaseFactor, double decreaseFactor) {
        super(index, leftLayerSize, rightLayerSize, nu, initValueWeight);

        this.cf = cf;
        this.alpha = alpha;
        this.increaseFactor = increaseFactor;
        this.decreaseFactor = decreaseFactor;

        this.backBiasWeight = new double[rightLayerSize];
        this.backNeuronWeight = new double[rightLayerSize][leftLayerSize];
        this.actualDeltaBiasWeight = new double[rightLayerSize];
        this.actualDeltaNeuronWeight = new double[rightLayerSize][leftLayerSize];
        this.backDeltaBiasWeight = new double[rightLayerSize];
        this.backDeltaNeuronWeight = new double[rightLayerSize][leftLayerSize];
        this.nuBiasWeight = new double[rightLayerSize];
        this.nuNeuronWeight = new double[rightLayerSize][leftLayerSize];

        for (int i = 0; i < rightLayerSize; i++) {
            this.actualDeltaBiasWeight[i] = 0;
            this.backDeltaBiasWeight[i] = 0;
            this.nuBiasWeight[i] = nu;
            for (int j = 0; j < leftLayerSize; j++) {
                this.actualDeltaNeuronWeight[i][j] = 0;
                this.backDeltaNeuronWeight[i][j] = 0;
                this.nuNeuronWeight[i][j] = nu;
            }
        }
    }

    public SuperSABMLWM(int index, int leftLayerSize, int rightLayerSize, double[] biasWeight, double[][] neuronWeight, double[] errorGradientBiasWeight, double[][] errorGradientNeuronWeight, InitValueType init, double cf, double alpha, double increaseFactor, double decreaseFactor) {
        super(index, leftLayerSize, rightLayerSize, biasWeight, neuronWeight, errorGradientBiasWeight, errorGradientNeuronWeight, init);
        this.cf = cf;
        this.alpha = alpha;
        this.increaseFactor = increaseFactor;
        this.decreaseFactor = decreaseFactor;
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

        //Copiando pesos antes de que se actualicen
        for (int i = 0; i < rightLayerSize; i++) {
            this.backBiasWeight[i] = this.biasWeight[i];
            for (int j = 0; j < leftLayerSize; j++) {
                this.backNeuronWeight[i][j] = this.neuronWeight[i][j];
            }
        }

        //Actualizando matriz mu y pesos
        for (int i = 0; i < rightLayerSize; i++) {
            if (actualDeltaBiasWeight[i] * backDeltaBiasWeight[i] >= 0) {
                nuBiasWeight[i] = increaseFactor*nuBiasWeight[i];
                this.biasWeight[i] = this.biasWeight[i] - nu * this.errorGradientBiasWeight[i];
            } else {
                nuBiasWeight[i] = decreaseFactor*nuBiasWeight[i];
                this.biasWeight[i] = this.biasWeight[i] - nu * this.errorGradientBiasWeight[i] + alpha * this.actualDeltaBiasWeight[i];
            }

            for (int j = 0; j < leftLayerSize; j++) {
                if (actualDeltaNeuronWeight[i][j] * backDeltaNeuronWeight[i][j] >= 0) {
                    nuNeuronWeight[i][j] = increaseFactor*nuNeuronWeight[i][j];
                    this.neuronWeight[i][j] = this.neuronWeight[i][j] - nu * this.errorGradientNeuronWeight[i][j];
                } else {
                    nuNeuronWeight[i][j] = decreaseFactor*nuNeuronWeight[i][j];
                    this.neuronWeight[i][j] = this.neuronWeight[i][j] - nu * this.errorGradientNeuronWeight[i][j] + alpha * this.actualDeltaNeuronWeight[i][j];
                }
            }
        }

        //Actualizando pesos
//        for (int i = 0; i < rightLayerSize; i++) {
//            this.biasWeight[i] = this.biasWeight[i] - nu * this.errorGradientBiasWeight[i] + alpha * this.actualDeltaBiasWeight[i];
//            for (int j = 0; j < leftLayerSize; j++) {
//                this.neuronWeight[i][j] = this.neuronWeight[i][j] - nu * this.errorGradientNeuronWeight[i][j] + alpha * this.actualDeltaNeuronWeight[i][j];
//            }
//        }

        //Actualizando deltas
        for (int i = 0; i < rightLayerSize; i++) {
            this.backDeltaBiasWeight[i] = this.actualDeltaBiasWeight[i];
            for (int j = 0; j < leftLayerSize; j++) {
                this.backDeltaNeuronWeight[i][j] = this.actualDeltaNeuronWeight[i][j];
            }
        }

        for (int i = 0; i < rightLayerSize; i++) {
            this.actualDeltaBiasWeight[i] = this.biasWeight[i] - this.backBiasWeight[i];
            for (int j = 0; j < leftLayerSize; j++) {
                this.actualDeltaNeuronWeight[i][j] = this.neuronWeight[i][j] - this.backNeuronWeight[i][j];
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
        InitValueType myInitValueWeight = this.initValueWeight;
        double myCf = this.cf;
        double myAlpha = this.alpha;
        double myIncreaseFactor = this.increaseFactor;
        double myDecreaseFactor = this.decreaseFactor;

        for (int i = 0; i < rightLayerSize; i++) {
            myBiasWeight[i] = this.biasWeight[i];
            for (int j = 0; j < leftLayerSize; j++) {
                myNeuronWeight[i][j] = this.neuronWeight[i][j];
            }
        }
        return new SuperSABMLWM(myIndex, myLeftLayerSize, myRightLayerSize, myBiasWeight, myNeuronWeight, myErrorGradientBiasWeight, myErrorGradientNeuronWeight, myInitValueWeight, myCf, myAlpha, myIncreaseFactor, myDecreaseFactor);
    }
}
