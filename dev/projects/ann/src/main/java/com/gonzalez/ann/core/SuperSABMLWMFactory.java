/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public class SuperSABMLWMFactory implements WeightMatrixFactory {

    private double cf;
    private double alpha;
    private double increaseFactor;
    private double decreaseFactor;
    private double nu;
    private InitValueType initValueWeight;

    public SuperSABMLWMFactory(double nu, InitValueType initValueWeight, double cf, double alpha, double increaseFactor, double decreaseFactor) {
        this.cf = cf;
        this.alpha = alpha;
        this.increaseFactor = increaseFactor;
        this.decreaseFactor = decreaseFactor;
        this.nu = nu;
        this.initValueWeight = initValueWeight;
    }

    public WeightMatrix create(int index, int leftLayerSize, int rightLayerSize) {
        return new SuperSABMLWM(index, leftLayerSize, rightLayerSize, nu, initValueWeight, cf, alpha, increaseFactor, decreaseFactor);
    }
}
