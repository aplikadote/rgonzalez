/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.boosting;

import com.gonzalez.aida.ann.ClassificationNN;
import com.gonzalez.aida.dataset.ClassificationDataset;
import com.gonzalez.aida.dataset.ClassificationInstance;
import com.gonzalez.aida.dataset.Instance;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrador
 */
public class ClassificationBoosting {

    private ClassificationDataset dataset;
    private List<ClassificationNN> machineList;
    Random r = new Random();

    public ClassificationBoosting(ClassificationDataset dataset, int numberOfMachines) {
        this.dataset = dataset;
        machineList = new ArrayList<ClassificationNN>(numberOfMachines);
        
        for (int i = 0; i < numberOfMachines; i++) {
            int neurons = r.nextInt(15) + 17;
            machineList.add(new ClassificationNN(dataset.getColumns(), neurons, dataset.getClusters(), 0.2));
        }

        for (int i = 0; i < dataset.getTrainSize(); i++) {
            ClassificationInstance instance = (ClassificationInstance) dataset.getTrainData().get(i);
            instance.setWeight((double) 1 / (double) dataset.getTrainSize());
        }
    }

    public void trainMachines() {
        List<Instance> trainData = dataset.getTrainData();
        for (int i = 0; i < machineList.size(); i++) {
            ClassificationNN cnn = machineList.get(i);
            int train = r.nextInt(200) + 300; 
            cnn.trainWith(train, trainData);
            double error = cnn.calculateEnsambleError(trainData);
            if(error == 0){
                System.out.println("EL ERROR FUE CERO");
                System.exit(0);
            }
            cnn.setEmsembleAlpha(0.5*Math.log((1 - error) / error));

            double sumWeight = 0;
            for (int j = 0; j < trainData.size(); j++) {
                ClassificationInstance instance = (ClassificationInstance) trainData.get(j);
                int predictY = cnn.predict(instance.getX());
                double weight = instance.getWeight();

                if (predictY != instance.getTarget()) {
                    instance.setWeight(weight * Math.exp(cnn.getEmsembleAlpha()));
                }
                else{
                    instance.setWeight(weight * Math.exp(-cnn.getEmsembleAlpha()));
                }
                
                sumWeight += instance.getWeight();
            }
            
            for (int j = 0; j < trainData.size(); j++) {
                ClassificationInstance instance = (ClassificationInstance) trainData.get(j);
                double weight = instance.getWeight();
                instance.setWeight(weight/sumWeight);
            }
            
        }
    }

    public int predict(double[] x) {
        double sum = 0;
        for (int i = 0; i < machineList.size(); i++) {
            ClassificationNN cnn = machineList.get(i);
            sum += cnn.getEmsembleAlpha() * cnn.predict(x);
        }

        if (sum > 0) {
            return 1;
        } else if (sum < 0) {
            return -1;
        } else {
            return 0;
        }

    }

    public double test(List<Instance> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            ClassificationInstance instance = (ClassificationInstance) list.get(i);
            double[] x = instance.getX();
            int y = instance.getTarget();
            int predictedY = predict(x);
            if (y != predictedY) {
                sum++;
            }
        }
        System.out.println("TESTEO BOOSTING");
        System.out.println("Mal clasificados: " + sum + " de " + list.size());
        System.out.println();
        return sum;
    }
}
