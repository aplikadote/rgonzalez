/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.dataset;

/**
 *
 * @author Administrador
 */
public class RegresionInstance {

    private double[] x;
    private double y;

    public RegresionInstance(double[] x, double y) {
        this.x = x;
        this.y = y;
    }

    public double[] getX() {
        return x;
    }

    public int getSizeX() {
        return this.x.length;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        String value = "";
        for (int i = 0; i < x.length; i++) {
            value = value.concat(Double.toString(x[i]).concat(" "));
//            System.out.println(" asdd " + value);
        }
        value = value.concat(" ".concat(Double.toString(y)));
        return value;
    }
}
