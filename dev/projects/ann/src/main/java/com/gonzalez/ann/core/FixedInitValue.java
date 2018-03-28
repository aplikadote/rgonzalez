/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.core;

/**
 *
 * @author rene
 */
public class FixedInitValue implements InitValueType{

    private double value;

    public FixedInitValue(double value){
        this.value = value;
    }
    public double getInitValue() {
        return value;
    }

}
