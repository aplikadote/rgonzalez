/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.core;

import java.util.Random;

/**
 *
 * @author rene
 */
public class RandomInitValue implements InitValueType {

    private double base;
    private double variance;
    private Random r;

    public RandomInitValue(double base, double variance) {
        this.base = base;
        this.variance = variance;
        this.r = new Random(System.currentTimeMillis());
    }

    public double getInitValue() {
        double limInf = base - variance;
        double limSup = base + variance;
//        System.out.println(limSup - (limSup - limInf) * (1 - r.nextDouble()));
        return limSup - (limSup - limInf) * (1 - r.nextDouble());
    }

    public static void main(String[] args){
        RandomInitValue r = new RandomInitValue(0,0.5);
        for(int i=0; i<20; i++){
            System.out.println( r.getInitValue());
        }
    }
}
