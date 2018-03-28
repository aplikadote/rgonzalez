/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.main;

import com.gonzalez.aida.boosting.RegresionBoosting;
import com.gonzalez.aida.dataset.BostonDataset;
import com.gonzalez.aida.dataset.LinnearRegresion;
import com.gonzalez.aida.dataset.RegresionInstance;

/**
 *
 * @author Administrador
 */
public class MainRegresionBoosting {

    public static void main(String[] args) {
        BostonDataset dataset = new BostonDataset("boston.data");
        LinnearRegresion lr = new LinnearRegresion(dataset);
        System.out.println(lr.getVarianzaNoExplicada(lr.getBeta()));

        for (int k = 1; k <= 20; k++) {
            RegresionBoosting boosting = new RegresionBoosting(dataset, 10);
            boosting.trainMachines();

            double sumAnn = 0;
            double sumLr = 0;
            for (int i = 0; i < dataset.getTestSize(); i++) {
                RegresionInstance instance = (RegresionInstance) dataset.getTestData().get(i);

                int id = instance.getId();
                double real = dataset.desestandariceY(instance.getY());
                double boostPredict = dataset.desestandariceY(boosting.predict(instance.getX()));
                double lrPredict = lr.predict(instance.getX());

                sumAnn += Math.pow(real - boostPredict, 2);
                sumLr += Math.pow(real - lrPredict, 2);

//                System.out.println(id + "\t" + real + "\t" + boostPredict + "\t" + lrPredict);
            }
            System.out.println(sumAnn + "\t" + sumLr + "\t" + dataset.getTestSize() + "\t" + dataset.getData().size());
        }
    }
}
