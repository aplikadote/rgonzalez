/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.dataset;

import com.gonzalez.ann.dataset.RegresionInstance;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rene
 */
public class DatasetPartition {

    private List<RegresionInstance> trainingData;
    private List<RegresionInstance> validationData;
    private List<RegresionInstance> testingData;

    public DatasetPartition(Dataset dataset, double trainingPercentage, double validationPercentage) {
        int dataCount = dataset.getDataCount();

        int trainSize = (int) Math.ceil(dataCount * trainingPercentage);
        int validationSize = (int) Math.ceil(dataCount * validationPercentage);
        int testSize = dataCount - trainSize - validationSize;

        List<RegresionInstance> data = dataset.getDataList();

        this.trainingData = new ArrayList<RegresionInstance>(trainSize);
        for (int i = 0; i < trainSize; i++) {
            this.trainingData.add(data.get(i));
        }

        this.validationData = new ArrayList<RegresionInstance>(validationSize);
        for (int i = trainSize; i < trainSize + validationSize; i++) {
            this.validationData.add(data.get(i));
        }

        this.testingData = new ArrayList<RegresionInstance>(testSize);
        for (int i = trainSize + validationSize; i < dataCount; i++) {
            this.testingData.add(data.get(i));
        }

        

        System.out.println("Training size: " + trainingData.size());
        System.out.println("Validation size: " + validationData.size());
        System.out.println("Testing size: " + testingData.size());
    }
    
    public List<RegresionInstance> getTestingData() {
        return testingData;
    }

    public List<RegresionInstance> getValidationData() {
        return validationData;
    }

    public List<RegresionInstance> getTrainingData() {
        return trainingData;
    }
}
