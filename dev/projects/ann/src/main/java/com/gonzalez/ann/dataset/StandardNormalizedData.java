/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.dataset;

import com.gonzalez.ann.dataset.RegresionInstance;

/**
 *
 * @author rene
 */
public class StandardNormalizedData {

    private double[] muX;
    private double[] sigma2X;
    private double muY;
    private double sigma2Y;

    public RegresionInstance normalice(RegresionInstance instance) {
        double[] xInstance = instance.getX();
        double[] x = new double[xInstance.length];
        double yInstance = instance.getY();

        for(int i=0; i<x.length; i++){
            x[i] = (xInstance[i] - muX[i]) / (Math.pow(sigma2X[i], 0.5));
        }
        double y = (yInstance - muY) / (Math.pow(sigma2Y, 0.5));

        return new RegresionInstance(x,y);
    }

    public double desnormalice(double normalicedY) {
        return normalicedY*Math.pow(sigma2Y, 0.5) + muY;
    }

    public double[] getMuX() {
        return muX;
    }

    public void setMuX(double[] muX) {
        this.muX = muX;
    }

    public double getMuY() {
        return muY;
    }

    public void setMuY(double muY) {
        this.muY = muY;
    }

    public double[] getSigma2X() {
        return sigma2X;
    }

    public void setSigma2X(double[] sigma2X) {
        this.sigma2X = sigma2X;
    }

    public double getSigma2Y() {
        return sigma2Y;
    }

    public void setSigma2Y(double sigma2Y) {
        this.sigma2Y = sigma2Y;
    }

}
