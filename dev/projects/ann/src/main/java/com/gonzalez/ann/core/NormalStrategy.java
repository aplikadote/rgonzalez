/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

import com.gonzalez.ann.dataset.DatasetPartition;
import com.gonzalez.ann.neural.NeuralNetwork;

/**
 *
 * @author rene
 */
public class NormalStrategy implements TrainingStrategy {

    private DatasetPartition partition;

    public NormalStrategy(DatasetPartition partition) {
        this.partition = partition;
    }

    public void runTraining(NeuralNetwork ann, boolean debug) {
        ann.train(partition.getTrainingData(), partition.getValidationData(), debug);
        double trainingError = ann.getTrainingError();
        double validationError = ann.getValidationError();
//        System.out.println("Training Error: " + trainingError);
//        System.out.println("Validation Error: " + validationError);
    }
}
