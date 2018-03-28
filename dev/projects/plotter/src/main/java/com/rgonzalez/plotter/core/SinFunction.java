/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.plotter.core;

/**
 *
 * @author Administrador
 */
public class SinFunction implements IFunction{

    public double getValue(double x) {
        return Math.sin(x);
    }

    public String getName(){
        return "Seno";
    }

}
