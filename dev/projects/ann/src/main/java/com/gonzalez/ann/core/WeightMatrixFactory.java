/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public interface WeightMatrixFactory {

    public WeightMatrix create(int index, int leftLayerSize, int rightLayerSize);

}
