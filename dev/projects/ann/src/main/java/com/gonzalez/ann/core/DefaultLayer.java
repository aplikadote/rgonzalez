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
 * @author rene
 */
public class DefaultLayer implements Layer {

    protected Function function;
    protected List<HiddenNeuron> neuronsList;
    protected int neuronSize;
    protected int index;
    protected double output[];
    protected double errorGradientOutput[];

    public DefaultLayer(int index, int neuronsInHiddenLayers, Function function) {
        this.index = index;
        this.function = function;
        this.neuronSize = neuronsInHiddenLayers;
        this.output = new double[neuronSize];
        this.errorGradientOutput = new double[neuronSize];
        this.neuronsList = new ArrayList<HiddenNeuron>(neuronSize);

        for (int i = 0; i < neuronSize; i++) {
            HiddenNeuron neuron = new HiddenNeuron();
            neuronsList.add(neuron);
        }
    }

    public List<? extends Neuron> getNeuronsList() {
        return this.neuronsList;
    }

    public void setOutput(Layer leftLayer, MultiLayerWeightMatrix weight) {
        double[] leftOutput = leftLayer.getOutput();
        double[][] neuralWeight = weight.getNeuralWeight();
        double[] biasWeight = weight.getBiasWeight();

        for (int i = 0; i < neuronSize; i++) {
            HiddenNeuron neuron = this.neuronsList.get(i);
            double out = neuron.getOutput(biasWeight[i], neuralWeight[i], leftOutput);
            output[i] = function.getValue(out);
        }

//        if (RegresionNeuralNetwork.DEBUG2) {
//            DecimalFormat format = new DecimalFormat("#.00");
//            weight.print();
//            System.out.println("Salida Capa Oculta " + (index + 1));
//            for (int j = 0; j < output.length; j++) {
//                System.out.println("  z[" + j + "]: " + format.format(output[j]));
//            }
//        }
    }

    public double[] getOutput() {
        return this.output;
    }

    public double[] getErrorGradient() {
        return this.errorGradientOutput;
    }

    public int getNeuronSize() {
        return this.neuronSize;
    }

    protected Function getFunction() {
        return this.function;
    }
}
