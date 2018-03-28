/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

import com.gonzalez.ann.neural.RegresionNeuralNetwork;

/**
 *
 * @author rene
 */
public class OutputLayer extends DefaultLayer {

    public OutputLayer(int index, int neuronsInHiddenLayers, Function function) {
        super(index, neuronsInHiddenLayers, function);
    }

    public void setGradientError(double y) {
        this.errorGradientOutput[0] = (output[0] - y);

//        if (RegresionNeuralNetwork.DEBUG2) {
//            System.out.println("Error " + (index + 1));
//            System.out.println("  Ez[0]: " + errorGradientOutput[0]);
//        }
    }
}
