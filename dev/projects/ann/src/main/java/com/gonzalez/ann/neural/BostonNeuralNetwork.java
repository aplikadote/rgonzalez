/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.neural;

import com.gonzalez.ann.core.AlphaWeightMatrix;
import com.gonzalez.ann.core.BetaWeightMatrix;
import com.gonzalez.ann.core.InitValueType;
import com.gonzalez.ann.core.TrainingMethod;
import com.gonzalez.ann.core.WeightMatrix;
import com.gonzalez.ann.dataset.Normalization;
import com.gonzalez.ann.utilities.Util;
import com.gonzalez.ann.dataset.RegresionInstance;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class BostonNeuralNetwork extends AbstractNeuralNetwork {

    private int xSize;
    private int n_hidden;
    private int zNodesSize;
    private double[] hiddenOutput;
    private double neuralOutput;
//    private static boolean DEBUG = true;

    public BostonNeuralNetwork(int xSize, int n_hidden, TrainingMethod method, double nu, InitValueType init) {
        this.xSize = xSize;
        this.n_hidden = n_hidden;
        this.nu = nu;
        this.zNodesSize = n_hidden;
        this.method = method;

        this.weightMatrixList = new ArrayList<WeightMatrix>();
        this.weightMatrixList.add(new AlphaWeightMatrix(zNodesSize, xSize, init));
        this.weightMatrixList.add(new BetaWeightMatrix(zNodesSize, init));

        this.hiddenOutput = new double[zNodesSize + 1];
    }

    public void forwardPass(RegresionInstance instance, boolean debug) {
        double[] x = addOneDimensionToVector(instance.getX());
        double[] myZ = new double[zNodesSize];
        double[][] weightAlpha = ((AlphaWeightMatrix) this.weightMatrixList.get(0)).getMatrix();

        for (int m = 0; m < weightAlpha.length; m++) {
            myZ[m] = actFunc(Util.cross(weightAlpha[m], x));
        }

        //Al vector z se le debe agregar un 1 como primera componente
        double[] weightBeta = ((BetaWeightMatrix) this.weightMatrixList.get(1)).getMatrix();
        hiddenOutput = addOneDimensionToVector(myZ);
        neuralOutput = Util.cross(weightBeta, hiddenOutput);

        if (debug) {
            System.out.println("salida: " + neuralOutput);
        }
    }

    public void backwardPropagation(RegresionInstance instance, boolean debug) {
        double y = instance.getY();
        double[] x = addOneDimensionToVector(instance.getX());
        double[][] weightAlpha = ((AlphaWeightMatrix) this.weightMatrixList.get(0)).getMatrix();
        double[] weightBeta = ((BetaWeightMatrix) this.weightMatrixList.get(1)).getMatrix();

        double delta = (neuralOutput - y);

//            System.out.println("Gradiente output: " + delta);
        for (int m = 0; m < weightAlpha.length; m++) {
            double deltaHidden = delta * deltaActFunc(hiddenOutput[m + 1]) * weightBeta[m + 1];
//                System.out.println("Gradiente Hidden: " + deltaHidden +"  z["+(m)+"]: " + z[m + 1] +"    weightBeta["+(m)+"]: " + weightBeta[m + 1]);
            for (int p = 0; p < weightAlpha[0].length; p++) {
                weightAlpha[m][p] = weightAlpha[m][p] - nu * deltaHidden * x[p];
            }
        }

        for (int q = 0; q < weightBeta.length; q++) {
            weightBeta[q] = weightBeta[q] - nu * delta * hiddenOutput[q];
        }

        if (debug) {
            System.out.println("Weight 0");
            for (int m = 0; m < weightAlpha.length; m++) {
                for (int p = 0; p < weightAlpha[0].length; p++) {
                    System.out.print("  " + weightAlpha[m][p]);
                }
                System.out.println();
            }

            System.out.println("Weight 1");
            for (int q = 0; q < weightBeta.length; q++) {
                System.out.print("  " + weightBeta[q]);
            }
            System.out.println();
        }
    }

    public double test(List<RegresionInstance> testData, boolean debug) {
        double sum = 0;
        for (int i = 0; i < testData.size(); i++) {
            RegresionInstance instance = testData.get(i);
            forwardPass(instance, debug);

            double y = testData.get(i).getY();
            if (debug) {
                System.out.println(neuralOutput + "   " + y);
            }
            sum += Math.pow(neuralOutput - y, 2);
        }

        return (1 / (double) testData.size()) * sum;
    }

    private double actFunc(double value) {
        return 1 / (1 + Math.exp(-value));
    }

    private double deltaActFunc(double value) {
        return value * (1 - value);
    }

    private double[] addOneDimensionToVector(double[] x) {
        double[] vector = new double[x.length + 1];
        vector[0] = 1;
        for (int j = 1; j < x.length + 1; j++) {
            vector[j] = x[j - 1];
        }
        return vector;
    }

//    public double predict(RegresionInstance instance, Normalization normalization, boolean debug) {
//        RegresionInstance normInstance = normalization.normalice(instance);
//        forwardPass(normInstance, false);
//        double normOutput = neuralOutput;
//        double myOutput = normalization.desnormalice(normOutput);
//
//        if (debug) {
//            System.out.println(myOutput + "  " + instance.getY());
//        }
//        return Math.pow(myOutput - instance.getY(), 2);
//    }
    public double getNeuralOutput() {
        return neuralOutput;
    }
}
