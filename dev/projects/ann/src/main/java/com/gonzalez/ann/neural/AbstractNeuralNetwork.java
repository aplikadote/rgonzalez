/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.neural;

import com.gonzalez.ann.core.TrainingMethod;
import com.gonzalez.ann.core.WeightMatrix;
import com.gonzalez.ann.dataset.Normalization;
import com.gonzalez.ann.dataset.RegresionInstance;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rene
 */
public abstract class AbstractNeuralNetwork implements NeuralNetwork {

    protected List<WeightMatrix> weightMatrixList;
    protected TrainingMethod method;
    protected double trainingError;
    protected double validationError;
    protected double nu;

    public abstract double getNeuralOutput();

    public TrainingMethod getMethod() {
        return method;
    }

    public void setMethod(TrainingMethod method) {
        this.method = method;
    }

    public double getNu() {
        return nu;
    }

    public void setNu(double nu) {
        this.nu = nu;
    }

    public void setTrainingError(double trainingError) {
        this.trainingError = trainingError;
    }

    public void setValidationError(double validationError) {
        this.validationError = validationError;
    }

    public double getTrainingError() {
        return this.trainingError;
    }

    public double getValidationError() {
        return this.validationError;
    }

    public void train(List<RegresionInstance> trainingData, List<RegresionInstance> validationData, boolean debug) {
        method.train(this, trainingData, validationData, debug);
    }

    public List<WeightMatrix> cloneWeightMatrixList() {
        List<WeightMatrix> matrixList = new ArrayList<WeightMatrix>();
        for (WeightMatrix matrix : this.weightMatrixList) {
            matrixList.add(matrix.clone());
        }
        return matrixList;
    }

    public void setWeight(List<WeightMatrix> weightMatrixList) {
        this.weightMatrixList = weightMatrixList;
    }

    public void resetWeight(){
        for (WeightMatrix matrix : this.weightMatrixList) {
            matrix.initValues();
        }
    }

    public double predict(RegresionInstance instance, Normalization normalization, boolean debug) {
        RegresionInstance normInstance = normalization.normalice(instance);
        forwardPass(normInstance, false);
//        double normOutput = this.getOutputLayer().getOutput()[0];
        double normOutput = getNeuralOutput();
        double output = normalization.desnormalice(normOutput);
        double target = normalization.desnormalice(instance.getY());

        if (debug) {
            System.out.println(output + "  " + target);
        }

        return Math.pow(output - target, 2);
    }

    public double predict(List<RegresionInstance> testingData, Normalization normalization, boolean debug) {
        double sum = 0;
        for (int i = 0; i < testingData.size(); i++) {
            RegresionInstance instance = testingData.get(i);
            sum += predict(instance, normalization, debug);
        }
        double predictError = sum / (testingData.size());
//        System.out.println("Predict Error: " + predictError);
        return predictError;
    }

}
