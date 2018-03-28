/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.neural;

import com.gonzalez.ann.core.DefaultLayer;
import com.gonzalez.ann.core.HiddenLayer;
import com.gonzalez.ann.core.IdentityFunction;
import com.gonzalez.ann.core.InputLayer;
import com.gonzalez.ann.core.Layer;
import com.gonzalez.ann.core.LogisticFunction;
import com.gonzalez.ann.core.MomentumMLWM;
import com.gonzalez.ann.core.OutputLayer;
import com.gonzalez.ann.core.TrainingMethod;
import com.gonzalez.ann.dataset.RegresionInstance;
import com.gonzalez.ann.core.MultiLayerWeightMatrix;
import com.gonzalez.ann.core.NewtonMLWM;
import com.gonzalez.ann.core.WeightMatrix;
import com.gonzalez.ann.core.WeightMatrixFactory;
import com.gonzalez.ann.dataset.Normalization;
import java.util.ArrayList;
import java.util.List;

public class RegresionNeuralNetwork extends AbstractNeuralNetwork {

    private InputLayer inputLayer;
    private List<DefaultLayer> hiddenLayerList;

    /**
     * La red siempre tendra una capa
     * de entrada y otra de salida. Solo
     * es necesario especificar cuantas
     * capas ocultas tendra
     * @param hiddenLayers numero de capas ocultas
     * @param neuronsInHiddenLayers la cantidad de neuronas en las
     * capas ocultas
     */
    public RegresionNeuralNetwork(int xDataSize, int hiddenLayersCount, int hiddenLayerNeurons, TrainingMethod method, WeightMatrixFactory factory) {
        this.method = method;
        this.hiddenLayerList = new ArrayList<DefaultLayer>(hiddenLayersCount + 1);
        int ySize = 1;

        //Capa entrada
        this.inputLayer = new InputLayer(xDataSize);

        //Capas ocultas
        for (int i = 0; i < hiddenLayersCount; i++) {
            LogisticFunction function = new LogisticFunction(1);
//            IdentityFunction function = new IdentityFunction();
            hiddenLayerList.add(new HiddenLayer(i, hiddenLayerNeurons, function));
        }

        //Capa salida
        IdentityFunction function = new IdentityFunction();
        hiddenLayerList.add(new OutputLayer(hiddenLayersCount, ySize, function));

        int size = this.hiddenLayerList.size();
        this.weightMatrixList = new ArrayList<WeightMatrix>(size);

        Layer leftLayer = this.inputLayer;
        for (int i = 0; i < size; i++) {
            DefaultLayer rightLayer = this.hiddenLayerList.get(i);

//            if (bpMethod == BPMethod.NEWTON) {
//                this.weightMatrixList.add(new NewtonMLWM(i, leftLayer.getNeuronSize(), rightLayer.getNeuronSize(), nu, init));
//            } else {
//                this.weightMatrixList.add(new MomentumMLWM(i, leftLayer.getNeuronSize(), rightLayer.getNeuronSize(), nu, init, 0,0.6));
//            }

            this.weightMatrixList.add(factory.create(i, leftLayer.getNeuronSize(), rightLayer.getNeuronSize()));

            leftLayer = rightLayer;
        }
    }

    public void forwardPass(RegresionInstance instance, boolean debug) {
        inputLayer.setOutput(instance.getX());
        Layer leftLayer = inputLayer;
        for (int i = 0; i < hiddenLayerList.size(); i++) {

            if (weightMatrixList == null) {
                System.out.println("Lista nula!!");
            }

            MultiLayerWeightMatrix rightWeight = (MultiLayerWeightMatrix) this.weightMatrixList.get(i);
            DefaultLayer rightLayer = hiddenLayerList.get(i);
            rightLayer.setOutput(leftLayer, rightWeight);
            leftLayer = rightLayer;
        }
        double output = getOutputLayer().getOutput()[0];

        if (debug) {
            System.out.println("salida: " + output);
        }
    }

    public void backwardPropagation(RegresionInstance instance, boolean debug) {
        OutputLayer outputLayer = getOutputLayer();
        outputLayer.setGradientError(instance.getY());

        DefaultLayer rightLayer = outputLayer;
        for (int i = hiddenLayerList.size() - 2; i >= 0; i--) {
            MultiLayerWeightMatrix rightWeight = (MultiLayerWeightMatrix) this.weightMatrixList.get(i + 1);
            HiddenLayer leftLayer = (HiddenLayer) this.hiddenLayerList.get(i);
            leftLayer.setGradientError(rightWeight, rightLayer);
//            rightWeight.setGradientError(leftLayer, rightLayer);
            rightLayer = leftLayer;
        }

        Layer leftLayer = this.inputLayer;
        for (int i = 0; i < this.hiddenLayerList.size(); i++) {
            rightLayer = this.hiddenLayerList.get(i);
            MultiLayerWeightMatrix multilayerMatrix = (MultiLayerWeightMatrix) this.weightMatrixList.get(i);
            multilayerMatrix.setGradientError(leftLayer, rightLayer);
            multilayerMatrix.updateWeights();
            leftLayer = rightLayer;
            if (debug) {
                multilayerMatrix.print();
            }
        }

    }

    public double test(List<RegresionInstance> data, boolean debug) {
        double error = 0;
        double size = data.size();
        for (RegresionInstance instance : data) {
            forwardPass(instance, false);
            OutputLayer layer = getOutputLayer();
            double output = layer.getOutput()[0];
            double target = instance.getY();

            if (debug) {
                System.out.println(output + "  " + target);
            }

            error += Math.pow((output - target), 2);
        }
        return error / (size);
    }

    private OutputLayer getOutputLayer() {
        int lastLayerIndex = hiddenLayerList.size() - 1;
        OutputLayer outputLayer = (OutputLayer) this.hiddenLayerList.get(lastLayerIndex);
        return outputLayer;
    }

    public double getNeuralOutput() {
        OutputLayer outputLayer = getOutputLayer();
        return outputLayer.getOutput()[0];
    }
}
