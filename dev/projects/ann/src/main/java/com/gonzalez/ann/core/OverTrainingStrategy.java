/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.core;

import com.gonzalez.ann.dataset.Dataset;
import com.gonzalez.ann.neural.NeuralNetwork;

/**
 *
 * @author rene
 */
public class OverTrainingStrategy implements TrainingStrategy{
    private Dataset normData;

    public OverTrainingStrategy(Dataset normData){
        this.normData = normData;
    }

    public void runTraining(NeuralNetwork ann, boolean debug) {
        ann.train(normData.getDataList(), normData.getDataList(), debug);
        double trainingError = ann.getTrainingError();
        double validationError = ann.getValidationError();
//        System.out.println("Training Error: " + trainingError);
//        System.out.println("Validation Error: " + validationError);
    }
}
