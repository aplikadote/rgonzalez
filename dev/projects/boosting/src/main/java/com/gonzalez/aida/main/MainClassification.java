/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.aida.main;

import com.gonzalez.aida.ann.ClassificationNN;
import com.gonzalez.aida.dataset.WisconsinDataset;

/**
 *
 * @author Administrador
 */
public class MainClassification {

    public static void main(String[] args){
        int clusters = 2;
        WisconsinDataset dataset = new WisconsinDataset("wisconsin.data", 33, clusters, true);
//        dataset.printTrain();
        
        ClassificationNN cnn = new ClassificationNN(dataset.getColumns(), 25, clusters, 0.2);
        double[] error = cnn.trainWith(400, dataset.getTrainData());
        double errorTest = cnn.test(dataset.getTestData());
        
    }
}
