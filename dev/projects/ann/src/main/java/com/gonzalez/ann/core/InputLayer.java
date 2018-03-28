/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

import com.gonzalez.ann.neural.RegresionNeuralNetwork;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author rene
 */
public class InputLayer implements Layer {

    private List<InputNeuron> neuronsList;
    private int neuronSize;
    private double[] x;

    public InputLayer(int xComponents) {
        this.neuronSize = xComponents;
        this.neuronsList = new ArrayList<InputNeuron>(neuronSize);
        this.x = new double[neuronSize];

        for (int i = 0; i < neuronSize; i++) {
            InputNeuron neuron = new InputNeuron();
            neuronsList.add(neuron);
        }
    }

    public List<? extends Neuron> getNeuronsList() {
        return this.neuronsList;
    }

    public double[] getOutput() {
        return this.x;
    }

    public void setOutput(double[] x) {
        for(int i=0; i<x.length; i++){
            this.x[i] = x[i];
        }

//        if (RegresionNeuralNetwork.DEBUG2) {
//            DecimalFormat format = new DecimalFormat("#.00");
//            System.out.println("Salida Capa Oculta " + (0 + 1));
//            for (int j = 0; j < x.length; j++) {
//                System.out.println("  z[" + j + "]: " + format.format(x[j]));
//            }
//        }
    }

    public int getNeuronSize() {
        return neuronSize;
    }
}
