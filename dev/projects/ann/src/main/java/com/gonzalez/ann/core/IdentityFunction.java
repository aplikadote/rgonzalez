/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public class IdentityFunction implements Function{

    public IdentityFunction() {
    }

    public double getValue(double input) {
        return input;
    }

    public double getDerivate(double input) {
        return 1;
    }

    public String toString(){
        return "Identity";
    }

}
