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
public class IterationMethod implements TrainingMethod {

    private double iterations;

    public IterationMethod(int iterations) {
        this.iterations = iterations;
    }

    public void train(NeuralNetwork ann, List<RegresionInstance> trainingDataList, List<RegresionInstance> validationDataList, boolean debug) {
        int iteration = 0;
        int i = 0;

        while (iteration < iterations) {
            RegresionInstance instance = trainingDataList.get(i);
            ann.forwardPass(instance, debug);
            ann.backwardPropagation(instance, debug);

            i++;
            iteration++;

            if (i == trainingDataList.size()) {
                i = 0;
            }
        }

        ann.setTrainingError( ann.test(trainingDataList, false) );
        ann.setValidationError( ann.test(validationDataList, false) );
    }


}
