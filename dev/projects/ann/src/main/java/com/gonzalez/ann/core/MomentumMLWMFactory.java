/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public class MomentumMLWMFactory implements WeightMatrixFactory {

    private double cf;
    private double alpha;
    private double nu;
    private InitValueType initValueWeight;

    public MomentumMLWMFactory(double nu, InitValueType initValueWeight, double cf, double alpha) {
        this.nu = nu;
        this.initValueWeight = initValueWeight;
        this.cf = cf;
        this.alpha = alpha;
    }

    public WeightMatrix create(int index, int leftLayerSize, int rightLayerSize) {
        return new MomentumMLWM(index, leftLayerSize, rightLayerSize, nu, initValueWeight, cf, alpha);
    }
}
