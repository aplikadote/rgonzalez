/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.aida.main;

import com.gonzalez.aida.ann.RegresionNN;
import com.gonzalez.aida.dataset.BostonDataset;
import com.gonzalez.aida.dataset.LinnearRegresion;
import com.gonzalez.aida.dataset.RegresionInstance;

/**
 *
 * @author Administrador
 */
public class MainRegresion {
    
    public static void main(String[] args){
        BostonDataset data = new BostonDataset("boston.data");
        
        RegresionNN rnn = new RegresionNN(data.getColumns(),11, 0.2);
        double[][] error = rnn.trainWith(2000, data);
        System.out.println( error[ error.length-1][1]);
        LinnearRegresion lr = new LinnearRegresion(data);
        System.out.println( lr.getVarianzaNoExplicada(lr.getBeta()));
        
//        for(int i=0; i<error.length; i++){
//            System.out.println(i + "\t" + error[i][1]);
//        }
        double sumAnn=0;
        double sumLr=0;
        for(int i=0; i<data.getTestSize(); i++){
            RegresionInstance instance = (RegresionInstance) data.getTestData().get(i);

            int id = instance.getId();
            double real = data.desestandariceY( instance.getY() );
            double annPredict = rnn.predict(instance, data);
            double lrPredict = lr.predict(instance.getX());
            
            sumAnn += Math.pow(real - annPredict, 2);
            sumLr += Math.pow(real - lrPredict, 2);
            
            System.out.println(id + "\t" +real + "\t" + annPredict + "\t" + lrPredict);
        }
        System.out.println(sumAnn + "\t" + sumLr);
    }

}
