/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.core;

import com.gonzalez.ann.neural.NeuralNetwork;

/**
 *
 * @author rene
 */
public interface TrainingStrategy {

    public void runTraining(NeuralNetwork ann, boolean debug);

}
