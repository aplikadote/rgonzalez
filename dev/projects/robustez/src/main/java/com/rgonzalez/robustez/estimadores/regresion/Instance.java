/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.robustez.estimadores.regresion;

/**
 *
 * @author Administrador
 */
public class Instance {

    private double[] x;
    private double y;

    public Instance(double[] x, double y) {
        double[] x_aux = new double[x.length+1];
        for(int i=0; i<x_aux.length;i++){
            if(i==0){
                x_aux[i]=1;
            }
            else{
                x_aux[i]=x[i-1];
            }
        }
        this.x = x_aux;
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
