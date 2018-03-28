/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.robustez.estimadores.regresion;

import Jama.Matrix;
import com.rgonzalez.robustez.estimadores.tarea2.Statistic;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author Administrador
 */
public class RobustRegresionAllende {

    private static DecimalFormat decimal = new DecimalFormat("#0.0000");
    private static double epsilon = 0.001;
    private Matrix x;
    private Matrix y;
    private Matrix c;
    private Matrix tau;
    private int contador;
    private double median;

    static {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(',');
        dfs.setDecimalSeparator('.');
        decimal.setDecimalFormatSymbols(dfs);
    }

    public RobustRegresionAllende(LinnearRegresion lr, double k_huber, boolean flag) {
        Matrix beta = lr.getBeta();
        x = lr.getX();
        y = lr.getY();
        c = x.transpose().times(x).inverse().times(x.transpose());

        
        if(flag){
            beta.print(2, 4);
            System.out.println(" 1 :" + Statistic.getMuestralMedian(beta, x, y));
        }
//        System.out.println( "Varianza Total        : " + lr.getVarianzaTotal() );
//        System.out.println( "Varianza Explicada    : " + lr.getVarianzaExplicada(beta) );
//        System.out.println( "Varianza No Explicada : " + lr.getVarianzaNoExplicada(beta) );
//        System.out.println( "Coef. Determinacion   : " + lr.getVarianzaExplicada(beta)/lr.getVarianzaTotal() );

        double error = Statistic.getMuestralMedian(beta, x, y);
        while (error > epsilon) {
            double mediana1 = Statistic.getMuestralMedian(beta, x, y);
            Matrix r = y.minus(x.times(beta));
            applyHuber(r, k_huber);
            tau = c.times(r);
            beta = beta.plus(tau);
            double mediana2 = Statistic.getMuestralMedian(beta, x, y);
            error = Math.abs(mediana1 - mediana2);
//            System.out.println(Statistic.getMuestralMedian(beta, x, y));
        }
        
        setMuestralMedian(Statistic.getMuestralMedian(beta, x, y));
        
        if(flag){
            beta.print(2, 4);
            System.out.println(" 2 :" + Statistic.getMuestralMedian(beta, x, y));
        }

        Matrix yTongo1 = x.times(lr.getBeta());
        Matrix yTongo2 = x.times(beta);

        contador = 0;
        for (int i = 0; i < y.getRowDimension(); i++) {
            double y0 = y.get(i, 0);
            double y1 = yTongo1.get(i, 0);
            double y2 = yTongo2.get(i, 0);
            int up = (Math.abs(y0 - y1) <= Math.abs(y0 - y2)) ? 0 : 1;

            if (up == 1) {
                contador++;
            }
//            System.out.println(
//                    decimal.format(y0) + "  " +
//                    decimal.format(y1) + "  " + 
//                    decimal.format(Math.abs(y0-y1)) + "  " +
//                    decimal.format(y2) + "  " +
//                    decimal.format(Math.abs( y0 -y2)) + "  " +
//                    up
//                    );

        }
//        System.out.println(y.getRowDimension() + " " + contador + " " + (y.getRowDimension() - contador));

    }
    
    public int getContador(){
        return this.contador;
    }

    public void applyHuber(Matrix r, double k_huber) {
        for (int i = 0; i < r.getRowDimension(); i++) {
            double value = r.get(i, 0);
            r.set(i, 0, Statistic.getPsiHuber(value, k_huber));
        }
    }
    
    private void setMuestralMedian(double median){
        this.median = median;
    }
    
    public double getMuestralMedian(){
        return this.median;
    }

//    public double getPsi(double value) {
//        if (Math.abs(value) <= k_huber) {
//            return value;
//        } else {
//            return k_huber;
//        }
//    }
}


