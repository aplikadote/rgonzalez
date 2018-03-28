/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public class LogisticFunction implements Function{

    private double c;

    public LogisticFunction(double c){
        this.c = c;
    }

    public double getValue(double input) {
        return 1/(1 + Math.exp(-c*input));
    }

    public double getDerivate(double z) {
        return c*z*(1-z);
    }

    public String toString(){
        return "Logistic";
    }
}
