/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.neural;

import com.gonzalez.ann.core.MultiLayerWeightMatrix;
import com.gonzalez.ann.core.WeightMatrix;
import com.gonzalez.ann.dataset.Normalization;
import com.gonzalez.ann.dataset.RegresionInstance;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrador
 */
public class ThreeLayersNeuralNetwork {

    private double[] hiddenNodes;
    private double[] hiddenLayerBias;
    private double[] outputLayerWeights;
    private double[][] hiddenLayerWeights;
    private double outputLayerBias;
    private double outputNode;
    private static double BIASINPUT = 1;
    private double nu;
    private int numberOfInputs;

    public ThreeLayersNeuralNetwork(int xSize, int numberOfHiddenNodes, double nu) {
        Random r = new Random(System.currentTimeMillis());
        this.nu = nu;

        hiddenNodes = new double[numberOfHiddenNodes];
        hiddenLayerBias = new double[numberOfHiddenNodes];
        outputLayerWeights = new double[numberOfHiddenNodes];
        hiddenLayerWeights = new double[numberOfHiddenNodes][];

        numberOfInputs = xSize;

        for (int h = 0; h < numberOfHiddenNodes; h++) {
            hiddenLayerBias[h] = r.nextDouble() - 0.5;
            hiddenLayerWeights[h] = new double[numberOfInputs];

            for (int i = 0; i < numberOfInputs; i++) {
                hiddenLayerWeights[h][i] = r.nextDouble() - 0.5;
            }
            outputLayerWeights[h] = r.nextDouble() - 0.5;
        }

        this.outputLayerBias = r.nextDouble() - 0.5;

    }

    public void forwardPass(double x[]) {
        outputNode = 0;

        for (int h = 0; h < hiddenNodes.length; h++) {
            hiddenNodes[h] = 0;

            for (int i = 0; i < x.length; i++) {
                hiddenNodes[h] += hiddenLayerWeights[h][i] * x[i];
            }
            hiddenNodes[h] += hiddenLayerBias[h] * BIASINPUT;
            hiddenNodes[h] = 1.0 / (1.0 + Math.exp(-hiddenNodes[h]));
            outputNode += hiddenNodes[h] * outputLayerWeights[h];
        }
        outputNode += outputLayerBias * BIASINPUT;
    }

    public void backwardPropagation(double x[], double target) {
        double outputDelta = (outputNode - target);

        for (int h = 0; h < hiddenNodes.length; h++) {
            double hiddenDelta = outputDelta * outputLayerWeights[h] * (hiddenNodes[h] * (1 - hiddenNodes[h]));

            for (int i = 0; i < x.length; i++) {
                hiddenLayerWeights[h][i] += -nu * hiddenDelta * x[i];
            }
            hiddenLayerBias[h] += -nu * hiddenDelta * BIASINPUT;
            outputLayerWeights[h] += -nu * outputDelta * hiddenNodes[h];
        }
        outputLayerBias += -nu * outputDelta * BIASINPUT;
    }

    public void trainWithIterations(List<RegresionInstance> samples, int iteraciones) {
//        double[][] error = new double[iteraciones][2];
//        double rate = 0;
        int i = 0;
        for (int it = 0; it < iteraciones; it++) {
            double[] x = samples.get(i).getX();
            double y = samples.get(i).getY();

            forwardPass(x);
            backwardPropagation(x, y);
            if (i == samples.size() - 1) {
                i = 0;
            } else {
                i++;
            }

//            rate += Math.pow(outputNode - y, 2);
//            error[it][0] = it;
//            error[it][1] = (1 / (double) (it + 1)) * rate;
//            monitor(it, y, error[it][1]);
        }
//        return error;
    }

    public double test(List<RegresionInstance> testData, boolean debug) {
//        double[] fTest = new double[testData.size()];
        double sum = 0;
        for (int i = 0; i < testData.size(); i++) {
            double[] x = testData.get(i).getX();
            double y = testData.get(i).getY();
            forwardPass(x);

            if (debug) {
                System.out.println(outputNode + "  " + y);
            }
//            monitor(i, y, 0);
            sum += Math.pow(outputNode - y, 2);
        }

        return (1 / (double) testData.size()) * sum;
    }

    private void monitor(int it, double y, double error) {
        System.out.println(
                "it: " + it + " , " +
                "f: " + outputNode + " , " +
                "y: " + y + " , " +
                "error: " + error);
    }

}