/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.robustez.estimadores.regresion;

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

    public LinnearRegresion(BostonDataset dataset) {
        List<Instance> list = dataset.getData();
        int n = list.size();
        int p = list.get(0).getSizeX();
        double[][] arrayX = new double[n][p];
        double[][] arrayY = new double[n][1];

        for (int i = 0; i < n; i++) {
            Instance instance = list.get(i);
            for (int j = 0; j < p; j++) {
                arrayX[i][j] = instance.getX()[j];
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
    
    public Matrix getBeta(){
        return this.beta;
    }
    
    public Matrix getY(){
        return this.y;
    }
    
    public Matrix getX(){
        return this.x;
    }
    
    public double getVarianzaTotal(){
        double promedio=0;
        for(int i=0; i<y.getRowDimension(); i++){
            promedio += y.get(i, 0);
        }
        promedio /= (double) y.getRowDimension();
        
        double sum=0;
        for(int i=0; i<y.getRowDimension(); i++){
            sum += Math.pow(y.get(i, 0) - promedio, 2);
        }
        return sum;
    }
    
    public double getVarianzaNoExplicada(Matrix beta){
        Matrix yTongo = x.times(beta);
        
        double sum=0;
        for(int i=0; i<y.getRowDimension(); i++){
            sum += Math.pow( y.get(i, 0) - yTongo.get(i, 0), 2);
        }
        return sum;
    }
    
    public double getVarianzaExplicada(Matrix beta){
        Matrix yTongo = x.times(beta);
        
        double promedio=0;
        for(int i=0; i<y.getRowDimension(); i++){
            promedio += y.get(i, 0);
        }
        promedio /= (double) y.getRowDimension();
        
        double sum=0;
        for(int i=0; i<y.getRowDimension(); i++){
            sum += Math.pow( yTongo.get(i, 0) - promedio , 2);
        }
        return sum;
    }
}
