/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.main;

import com.gonzalez.aida.boosting.ClassificationBoosting;
import com.gonzalez.aida.dataset.WisconsinDataset;

/**
 *
 * @author Administrador
 */
public class MainClassificationBoosting {

    public static void main(String[] args) {
        int clusters = 2;
        WisconsinDataset dataset = new WisconsinDataset("wisconsin.data", 33, clusters, true);

        for (int i = 1; i <= 20; i++) {
            ClassificationBoosting boosting = new ClassificationBoosting(dataset, i);
            boosting.trainMachines();
            boosting.test(dataset.getTestData());
        }
    }
}
