/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.boosting;

import com.gonzalez.aida.ann.ClassificationNN;
import com.gonzalez.aida.ann.RegresionNN;
import com.gonzalez.aida.dataset.ClassificationInstance;
import com.gonzalez.aida.dataset.Instance;
import com.gonzalez.aida.dataset.RegresionDataset;
import com.gonzalez.aida.dataset.RegresionInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrador
 */
public class RegresionBoosting {

    private List<RegresionNN> machineList;
    private RegresionDataset dataset;
    private Random r = new Random();

    public RegresionBoosting(RegresionDataset dataset, int numberOfMachines) {
        this.dataset = dataset;
        machineList = new ArrayList<RegresionNN>(numberOfMachines);
        this.dataset = dataset;

        for (int i = 0; i < numberOfMachines; i++) {
            int neurons = r.nextInt(2) + 6;
            machineList.add(new RegresionNN(dataset.getColumns(), neurons, 0.1));
        }

        for (int i = 0; i < dataset.getTrainSize(); i++) {
            RegresionInstance instance = (RegresionInstance) dataset.getTrainData().get(i);
            instance.setResiduo(instance.getY());
        }
    }

    public void trainMachines() {
        List<Instance> trainData = dataset.getTrainData();
        for (int i = 0; i < machineList.size(); i++) {
            RegresionNN rnn = machineList.get(i);
            int train = r.nextInt(200) + 500;
            rnn.trainForEnsembleWith(train, trainData);
            
            for(int j=0; j<trainData.size(); j++){
                RegresionInstance instance = (RegresionInstance) trainData.get(j);
                double value = rnn.predictForEnsemble(instance.getX());
                double res = instance.getResiduo();
                instance.setResiduo(res - value);
            }
        }
    }

    public double predict(double[] x) {
        double sum = 0;
        for (int i = 0; i < machineList.size(); i++) {
            RegresionNN cnn = machineList.get(i);
            sum += cnn.predictForEnsemble(x);
        }
        return sum;
    }

    public double test(List<Instance> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            RegresionInstance instance = (RegresionInstance) list.get(i);
            double[] x = instance.getX();
            double y = dataset.desestandariceY( instance.getY() );
            double predictedY = dataset.desestandariceY( predict(x) );
            
            sum += Math.pow(y - predictedY, 2);
        }
        System.out.println("TESTEO BOOSTING");
        System.out.println("Error: " + sum );
        System.out.println();
        return sum;
    }
}
