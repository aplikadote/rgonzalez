/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.plotter.core;

/**
 *
 * @author Administrador
 */
public class GaussianFunction implements IFunction {

    private double mu;
    private double sigma;

    public GaussianFunction(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;
    }

    public String getName() {
        return "Gaussian";
    }

    public double getValue(double x) {
        return (1 / (sigma * Math.sqrt(2 * Math.PI))) * Math.exp(-0.5 * Math.pow((x - mu) / (sigma), 2));
    }
}
