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
public class ClassificationInstance implements Instance {

    private double[] x;
    private final int target;
    private final int clusters;
    private int[] codifiedY;
    private int id;
    private double weight;

    public ClassificationInstance(double[] x, int target, int clusters, int id) {
        this.x = x;
        this.target = target;
        this.clusters = clusters;
        this.codifiedY = new int[clusters];
        this.id = id;
        if (clusters == 2) {
            if (target == 1) {
                codifiedY[0] = 1;
            } else {
                codifiedY[1] = 1;
            }
        } else {
            for (int i = 0; i < clusters; i++) {
                if (i == (target - 1)) {
                    codifiedY[i] = 1;
                } else {
                    codifiedY[i] = 0;
                }
            }
        }
    }

    public double[] getX() {
        return x;
    }

    public int getId() {
        return this.id;
    }

    public int getSizeX() {
        return this.x.length;
    }

    /**
     * Solo para cumplir con la interfaz.
     * Entrega el valor del cluster al que pertenece
     * @return
     */
    public double getY() {
        return target;
    }

    /**
     * Solo para cumplir con la interfaz
     * @return
     */
    /**
     * El valor de la clase a la que pertenece la instancia.
     * Su valor va desde 1 a "clusters".
     * Si solo se tiene dos clases, el valor sera {-1,1}
     * @return
     */
    public int getTarget() {
        return this.target;
    }

    public int[] getCodifiedY() {
        return this.codifiedY;
    }

    public int getSizeY() {
        return codifiedY.length;
    }

    public void setY(int[] y) {
        this.codifiedY = y;
    }

    public void setY(double y) {
        throw new UnsupportedOperationException("Este metodo no debe ser usado para esta clase");
    }

    public Instance clone() {
        return new ClassificationInstance(x.clone(), target, clusters, id);
    }

    public void print() {
        DecimalFormat decimal = new DecimalFormat("##0.00000");
        double[] x = getX();
        double y = getY();

        System.out.print(id + "  ");
        for (int j = 0; j < x.length; j++) {
            System.out.print(decimal.format(x[j]) + " ");
        }
        System.out.print("   ");
        for (int j = 0; j < codifiedY.length; j++) {
            System.out.print(codifiedY[j] + " ");
        }
        System.out.println();
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }
}
