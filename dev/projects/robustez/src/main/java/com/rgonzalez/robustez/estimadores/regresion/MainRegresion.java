/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.robustez.estimadores.regresion;

/**
 *
 * @author Administrador
 */
public class MainRegresion {

    public MainRegresion() {

        BostonDataset bd = new BostonDataset("boston.data");
        LinnearRegresion lr = new LinnearRegresion(bd);
//        new RobustRegresionYohai(lr);

        int points = 1000;
        double lower = 0;
        double upper = 3;
        double rate = (upper - lower) / points;
        double[][] arrayKHuber = new double[points][2];

        double max = Double.NEGATIVE_INFINITY;
        double maxK = -1;
        for (int k = 0; k < points; k++) {
            double k_huber = rate * k + lower;
            arrayKHuber[k][0] = k_huber;
            RobustRegresionAllende rra = new RobustRegresionAllende(lr, k_huber, false);
            arrayKHuber[k][1] = rra.getContador();
            
            if(arrayKHuber[k][1]>max){
                max = arrayKHuber[k][1];
                maxK = arrayKHuber[k][0];
            }
        }
        
        new PlotFrame(arrayKHuber, "Performance", "k-Huber", "Mediana Muestral");
        
        System.out.println(maxK);
        RobustRegresionAllende rra = new RobustRegresionAllende(lr, maxK, true);
        
    }

    public static void main(String[] args) {
        new MainRegresion();
    }
}
