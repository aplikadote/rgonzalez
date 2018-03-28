/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.neural;

import com.gonzalez.ann.core.WeightMatrix;
import com.gonzalez.ann.dataset.Normalization;
import com.gonzalez.ann.dataset.RegresionInstance;
import java.util.List;

/**
 *
 * @author rene
 */
public interface NeuralNetwork {

    public void forwardPass(RegresionInstance instance, boolean debug);

    public void backwardPropagation(RegresionInstance instance, boolean debug);

    public void train(List<RegresionInstance> trainingData, List<RegresionInstance> validationData, boolean debug);

    public double test(List<RegresionInstance> testingDataList, boolean debug);

    public double predict(RegresionInstance instance, Normalization stdData, boolean debug);

    public double predict(List<RegresionInstance> trainingData, Normalization stdData, boolean debug);

    public void setTrainingError(double trainingError);

    public void setValidationError(double validationError);

    public double getTrainingError();

    public double getValidationError();

    public void resetWeight();

    public List<WeightMatrix> cloneWeightMatrixList();

    public void setWeight(List<WeightMatrix> bestWeight);
    
}
