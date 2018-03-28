/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.robustez.estimadores.regresion;

import Jama.Matrix;
import com.rgonzalez.robustez.estimadores.tarea2.Statistic;

/**
 *
 * @author Administrador
 */
public class RobustRegresionYohai {

    private static double k_huber = 1.345;
    private static double delta = 0.9;
    private Matrix x;
    private Matrix y;
    
    public RobustRegresionYohai(LinnearRegresion lr){
        Matrix beta = lr.getBeta();
        x = lr.getX();
        y = lr.getY();
            
        double sigma = Statistic.getMuestralMedian(beta, x, y);
        
        Matrix xi = x.getMatrix(0,0,0,x.getColumnDimension()-1);
        
        xi.transpose().times(xi).print(2, 4);
        
        int iteraciones = 4;
        for (int i = 0; i < iteraciones; i++) {
            System.out.println("SIGMA " + sigma);
            System.out.println( "Varianza Muestral: " + Statistic.getMuestralMedian(beta, x, y) );
//            beta.print(2, 4);

            beta = getBeta(beta, sigma);
            sigma = getSigma(beta, sigma);
            
        }

    }
    
    public Matrix getBeta(Matrix beta, double sigma) {
        int n = x.getRowDimension();
        int m = x.getColumnDimension();

        double[] wi = new double[n];
        double[][] array = x.getArray();
        for (int i = 0; i < n; i++) {
            wi[i] = getW(i, beta, sigma);
            for(int j=0; j<m; j++){
                array[i][j] = array[i][j]*wi[i];
            }
        }
        Matrix one = new Matrix(array).transpose().times(x);
        one = one.inverse();

        double[][] yArray = y.getArray();
        double[][] array2 = new double[yArray.length][1];
        
        for (int i = 1; i < n; i++) {
            array2[i][0] = yArray[i][0]*wi[i];
        }
        Matrix two = new Matrix(array2);
        two = x.transpose().times(two); // X'Y

        return one.times(two);
    }

    public double getSigma(Matrix beta, double sigma) {
        int n = x.getRowDimension();
        int m = x.getColumnDimension();

        double sum = 0;
        for (int i = 0; i < n; i++) {
            Matrix xi = x.getMatrix(i, i, 0, m - 1);
            Matrix yi = x.getMatrix(i, i, 0, 0);
            double value = yi.minus(xi.times(beta)).getArray()[0][0];
            value = value / sigma;
            sum += Math.pow( Statistic.getPsiHuber(value, k_huber), 2);
        }
        sum = sum * (1 / (double) (n - m) * delta);
        sum = Math.sqrt(sum);
        return sum * sigma;
    }

    public double getW(int i, Matrix beta, double sigma) {
        Matrix yi = y.getMatrix(i, i, 0, 0);
        Matrix xi = x.getMatrix(i, i, 0, x.getColumnDimension() - 1);
        Matrix aux = yi.minus(xi.times(beta));
        double value = Math.abs(aux.getArray()[0][0]);

        if (value <= k_huber * sigma) {
            return 1;
        } else {
            return k_huber * sigma / value;
        }
    }

}
