/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.core;

import java.util.List;

/**
 *
 * @author rene
 */
public interface Layer {

    public List<? extends Neuron> getNeuronsList();

    public int getNeuronSize();

    public double[] getOutput();


}
