/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.core;

import com.gonzalez.ann.dataset.CrossValidationPartition;
import com.gonzalez.ann.dataset.RegresionInstance;
import com.gonzalez.ann.neural.NeuralNetwork;
import java.util.List;

/**
 *
 * @author rene
 */
public class CrossValidationStrategy implements TrainingStrategy {

    private CrossValidationPartition partition;
    private int folds;

    public CrossValidationStrategy(CrossValidationPartition partition){
        this.partition = partition;
        this.folds = partition.getFolds();
    }

    public void runTraining(NeuralNetwork ann, boolean debug){
        double[] error = new double[folds];
        double minError = Double.POSITIVE_INFINITY;
        List<WeightMatrix> bestWeight = ann.cloneWeightMatrixList();

        for (int i = 0; i < folds; i++) {
            List<RegresionInstance> trainingData = partition.getTrainFoldData(i);
            List<RegresionInstance> validationData = partition.getValidationFoldData(i);
            ann.train(trainingData, validationData, debug);
            error[i] = (ann.getTrainingError() + ann.getValidationError()) / 2;

//            System.out.println("Fold: " + i + "      MeanError: " + error[i]);
            if (error[i] < minError) {
                minError = error[i];
                bestWeight = ann.cloneWeightMatrixList();
            }
            ann.resetWeight();
        }

        ann.setWeight(bestWeight);
    }

}
