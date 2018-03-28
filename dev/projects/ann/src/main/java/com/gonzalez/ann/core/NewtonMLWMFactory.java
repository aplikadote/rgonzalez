/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public class NewtonMLWMFactory implements WeightMatrixFactory {

    private double nu;
    private InitValueType init;

    public NewtonMLWMFactory(double nu, InitValueType init){
        this.nu = nu;
        this.init = init;
    }

    public WeightMatrix create(int index, int leftLayerSize, int rightLayerSize) {
        return new NewtonMLWM(index, leftLayerSize, rightLayerSize, nu, init);
    }
}
