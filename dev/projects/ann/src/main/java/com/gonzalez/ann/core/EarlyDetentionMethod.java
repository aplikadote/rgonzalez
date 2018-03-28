/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

import com.gonzalez.ann.dataset.RegresionInstance;
import com.gonzalez.ann.neural.NeuralNetwork;
import java.util.List;

/**
 *
 * @author rene
 */
public class EarlyDetentionMethod implements TrainingMethod {

    private int maxIterations;

    public EarlyDetentionMethod(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void train(NeuralNetwork ann, List<RegresionInstance> trainingDataList, List<RegresionInstance> validationDataList, boolean debug) {
        List<WeightMatrix> bestWeightList = null;
        int cont = 0;
        int iteration = 0;
        int i = 0;
        double minimunError = Double.POSITIVE_INFINITY;
        double minTrainingError = 0;
        double minValidationError = 0;

        while (iteration <= maxIterations) {
            RegresionInstance instance = trainingDataList.get(i);
            ann.forwardPass(instance, debug);
            ann.backwardPropagation(instance, debug);

            double actualTrainingError = ann.test(trainingDataList, false);
            double actualValidationError = ann.test(validationDataList, false);
            double actualMeanError = (actualTrainingError + actualValidationError) / 2;

            if (Double.compare(actualMeanError, minimunError) < 0) {
                bestWeightList = ann.cloneWeightMatrixList();
                cont = 0;
                minTrainingError = actualTrainingError;
                minValidationError = actualValidationError;
            }

            if (Double.compare(actualMeanError, minimunError) > 0) {
                cont++;
            }

            if (cont > 2) {
                break;
            }

            i++;
            iteration++;

            if (i == trainingDataList.size()) {
                i = 0;
            }
        }

        ann.setWeight(bestWeightList);
        ann.setTrainingError(minTrainingError);
        ann.setValidationError(minValidationError);
    }
}
