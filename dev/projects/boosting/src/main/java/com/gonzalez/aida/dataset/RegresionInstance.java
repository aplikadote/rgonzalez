/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.dataset;

import java.text.DecimalFormat;

/**
 *
 * @author Administrador
 */
public class RegresionInstance implements Instance {

    private double[] x;
    private double y;
    private int id;
    private double residuo;

    public RegresionInstance(double[] x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public double[] getX() {
        return x;
    }

    public int getId() {
        return id;
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

    public Instance clone() {
        return new RegresionInstance(x.clone(), y, id);
    }

    public void print() {
        DecimalFormat decimal = new DecimalFormat("##0.00000");
        double[] x = getX();
        double y = getY();

        for (int j = 0; j < x.length; j++) {
            System.out.print(decimal.format(x[j]) + " ");
        }
        System.out.print(decimal.format(y));
        System.out.println();
    }
    
    public double getResiduo() {
        return residuo;
    }

    public void setResiduo(double residuo) {
        this.residuo = residuo;
    }
}
