/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.plotter.core;

/**
 *
 * @author Administrador
 */
public class ExponentialFunction implements IFunction {

    private double lambda;

    public ExponentialFunction(double lambda){
        this.lambda = lambda;
    }

    public String getName() {
        return "Exponential";
    }

    public double getValue(double x) {
        return lambda*Math.pow( Math.E, -lambda*x);
    }

}
