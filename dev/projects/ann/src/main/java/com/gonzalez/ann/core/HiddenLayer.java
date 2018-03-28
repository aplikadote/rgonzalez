/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

import com.gonzalez.ann.neural.RegresionNeuralNetwork;
import java.text.DecimalFormat;

/**
 *
 * @author rene
 */
public class HiddenLayer extends DefaultLayer {

    public HiddenLayer(int index, int neuronsInHiddenLayers, Function function) {
        super(index, neuronsInHiddenLayers, function);
    }

    public void setGradientError(MultiLayerWeightMatrix rightWeight, DefaultLayer rightLayer) {
        double[][] rightMatrix = rightWeight.getNeuralWeight();
        double[] rightErrorGradient = rightLayer.getErrorGradient();
        double[] rightOutput = rightLayer.getOutput();
        Function rightFunction = rightLayer.getFunction();
        int rightSize = rightLayer.getNeuronSize();

//        System.out.println("Gradiente output: " + rightErrorGradient[0]);

        for (int i = 0; i < this.neuronSize; i++) {
            errorGradientOutput[i] = 0;
            for (int j = 0; j < rightSize; j++) {
                errorGradientOutput[i] += rightMatrix[j][i] * rightErrorGradient[j] * rightFunction.getDerivate(rightOutput[j]);
//                System.out.println("Gradiente Hidden ["+i+"]: " + errorGradientOutput[i] +"  z["+(j)+"]: " + output[j] + "    weightBeta["+(i)+"]:" + weight[j][i]);
            }
        }

//        if (RegresionNeuralNetwork.DEBUG2) {
//            DecimalFormat format = new DecimalFormat("#.00");
//            System.out.println("Error Capa Oculta " + (index + 1));
//            for (int i = 0; i < this.neuronSize; i++) {
//                for (int j = 0; j < rightSize; j++) {
//                    System.out.println("  Ez[" + i + "]: " + format.format(errorGradientOutput[i]) );
//                }
//            }
//        }

    }
}
