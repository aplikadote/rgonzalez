/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.core;

import com.gonzalez.ann.dataset.RegresionInstance;
import com.gonzalez.ann.neural.NeuralNetwork;
import java.util.List;

/**
 *
 * @author rene
 */
public interface TrainingMethod {

    public void train(NeuralNetwork ann, List<RegresionInstance> trainingDataList, List<RegresionInstance> validationDataList, boolean debug);
}
