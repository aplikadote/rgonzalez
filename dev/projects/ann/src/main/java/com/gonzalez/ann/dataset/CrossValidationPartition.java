/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.dataset;

import com.gonzalez.ann.utilities.FoldContainer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rene
 */
public class CrossValidationPartition {

    private List<RegresionInstance> trainingData;
    private List<RegresionInstance> testingData;
    private FoldContainer container;
    private int folds;

    public CrossValidationPartition(Dataset dataset, double trainingPercentage, int folds) {
        this.folds = folds;
        int dataCount = dataset.getDataCount();
        int trainSize = (int) Math.ceil(dataCount * trainingPercentage);
        int testSize = dataCount - trainSize;

        this.trainingData = new ArrayList<RegresionInstance>(trainSize);
        List<RegresionInstance> data = dataset.getDataList();
        for (int i = 0; i < trainSize; i++) {
            this.trainingData.add(data.get(i));
        }

        this.testingData = new ArrayList<RegresionInstance>(testSize);
        for (int i = trainSize; i < dataCount; i++) {
            this.testingData.add(data.get(i));
        }

        this.container = new FoldContainer(trainingData, folds);

//        System.out.println("Training size: " + trainingData.size());
//        System.out.println("Testing size: " + testingData.size());
    }

    public List<RegresionInstance> getTrainFoldData(int i) {
        return this.container.getTrainFoldData(i);
    }

    public List<RegresionInstance> getValidationFoldData(int i) {
        return this.container.getValidationFoldData(i);
    }

    public List<RegresionInstance> getTestingData(){
        return this.testingData;
    }

    public int getFolds(){
        return this.folds;
    }
}
