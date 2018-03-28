/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.aida.svm.core;

/**
 *
 * @author Administrador
 */
public class Range {
    
    private double lower;
    private double upper;
    private double points;
    private double scale;

    public Range(double lower, double upper, int points) {
        this.lower = lower;
        this.upper = upper;
        this.points = points;
        this.scale = (upper - lower)/points;
    }
    
    public double getValue(int i){
        return Math.pow(2, scale*i + lower );
    }
    
    public double getLog(int i){
        return scale*i + lower;
    }
    
    public double getPoints(){
        return points;
    }
    
    public double getLower() {
        return lower;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public double getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }
    
    public double getScale() {
        return points;
    }

    public void setScale(double scale) {
        this.points = scale;
    }
}
