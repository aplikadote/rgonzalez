/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public class HiddenNeuron implements Neuron {

    public double getOutput(double bias, double[] weight, double[] z) {
        double out = bias;
        for (int j = 0; j < weight.length; j++) {
            out += weight[j] * z[j];
        }
        return out;
    }
}
