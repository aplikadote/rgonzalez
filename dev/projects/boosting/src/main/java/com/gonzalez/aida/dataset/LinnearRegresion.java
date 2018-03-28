/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.dataset;

import Jama.Matrix;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class LinnearRegresion {

    private Matrix x;
    private Matrix y;
    private Matrix beta; //vector columna
    private RegresionDataset dataset;

    public LinnearRegresion(RegresionDataset dataset) {
        List<Instance> list = dataset.getData();
        int n = list.size();
        this.dataset = dataset;

        int p = list.get(0).getX().length+1;
        double[][] arrayX = new double[n][p];
        double[][] arrayY = new double[n][1];
        
        for (int i = 0; i < n; i++) {
            RegresionInstance instance = (RegresionInstance) list.get(i);
            for (int j = 0; j < p; j++) {
                double[] plusOne = plusOne( instance.getX() );
                arrayX[i][j] = plusOne[j];
            }
            arrayY[i][0] = instance.getY();
        }

        x = new Matrix(arrayX);
//        X.print(2,4);

        y = new Matrix(arrayY);
//        Y.print(1, 4);

        Matrix xt = x.transpose();
        //Matrix yt = y.transpose();

        Matrix xt_times_x = xt.times(x); // X'X

        Matrix xt_times_y = xt.times(y); // X'Y

        // B*X'X = X'Y
        beta = xt_times_x.solve(xt_times_y);
    }

    private double[] plusOne(double[] x) {
        double[] plusOne = new double[x.length+1];
        for (int i = 0; i < x.length + 1; i++) {
            if (i == 0) {
                plusOne[i] = 1;
            } else {
                plusOne[i] = x[i-1];
            }
        }
        return plusOne;
    }

    public Matrix getBeta() {
        return this.beta;
    }

    public Matrix getY() {
        return this.y;
    }

    public Matrix getX() {
        return this.x;
    }

    public double getVarianzaTotal() {
        double promedio = 0;
        for (int i = 0; i < y.getRowDimension(); i++) {
            promedio += y.get(i, 0);
        }
        promedio /= (double) y.getRowDimension();

        double sum = 0;
        for (int i = 0; i < y.getRowDimension(); i++) {
            sum += Math.pow(y.get(i, 0) - promedio, 2);
        }
        return sum;
    }

    /**
     * Diferencia al cuadrado del error de prediccion respecto
     * al valor real a predecir
     * @param beta
     * @return
     */
    public double getVarianzaNoExplicada(Matrix beta) {
        Matrix yTongo = x.times(beta);
        
//        for(int i=0; i<yTongo.getRowDimension(); i++){
//            System.out.println( y.getArray()[i][0] + " " + yTongo.getArray()[i][0]);
//        }

        double sum = 0;
        for (int i = 0; i < y.getRowDimension(); i++) {
            sum += Math.pow(y.get(i, 0) - yTongo.get(i, 0), 2);
        }
        return sum;
    }

    public double getVarianzaExplicada(Matrix beta) {
        Matrix yTongo = x.times(beta);

        double promedio = 0;
        for (int i = 0; i < y.getRowDimension(); i++) {
            promedio += y.get(i, 0);
        }
        promedio /= (double) y.getRowDimension();

        double sum = 0;
        for (int i = 0; i < y.getRowDimension(); i++) {
            sum += Math.pow(yTongo.get(i, 0) - promedio, 2);
        }
        return sum;
    }

    public void predictAndPrint(Instance instance) {
        double[] xInst = instance.getX();
        double yInst = ((RegresionInstance) instance).getY();

        double[] desStdX = dataset.desestandariceX(xInst);
        double desStdY = dataset.desestandariceY(yInst);
        
        double[] desStdXPlusOne = plusOne( desStdX );
        double[][] matrixStdX = new double[1][desStdXPlusOne.length];
        for( int i=0; i< matrixStdX[0].length; i++){
            matrixStdX[0][i] = desStdXPlusOne[i];
        }
        
        Matrix matrixX = new Matrix(matrixStdX);
        Matrix predictY = matrixX.times(beta);
        double predicValueY = predictY.getArray()[0][0];

        System.out.println("ID instancia    : " + instance.getId());
        System.out.println("Valor Y         : " + desStdY);
        System.out.println("Valor Y predicho: " + predicValueY);
        System.out.println("EC              : " + Math.pow(desStdY - predicValueY, 2));
    }
    
    public double predict(double[] xInst) {
        double[] desStdX = dataset.desestandariceX(xInst);
        
        double[] desStdXPlusOne = plusOne( desStdX );
        double[][] matrixStdX = new double[1][desStdXPlusOne.length];
        for( int i=0; i< matrixStdX[0].length; i++){
            matrixStdX[0][i] = desStdXPlusOne[i];
        }
        
        Matrix matrixX = new Matrix(matrixStdX);
        Matrix predictY = matrixX.times(beta);
        return predictY.getArray()[0][0];
    }
}
