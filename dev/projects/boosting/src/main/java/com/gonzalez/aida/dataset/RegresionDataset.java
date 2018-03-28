/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.dataset;

import java.text.DecimalFormat;
import java.util.List;


/**
 *
 * @author Administrador
 */
public abstract class RegresionDataset extends Dataset {

    protected double muY;
    protected double sigma2Y;
    
    public RegresionDataset(String filename) {
        super(filename);
        standarixeTrainAndTestY();
    }

    private void standarixeTrainAndTestY() {

        setMuY();
        setSigma2Y();
        standarizeY(trainData);
        standarizeY(testData);

    }
    
    private void setMuY(){
        muY = 0;
        for (int i = 0; i < trainSize; i++) {
            RegresionInstance instance = (RegresionInstance) trainData.get(i);
            muY += instance.getY();
        }
        muY /= trainSize;
    }
    
    private void setSigma2Y(){
        for (int i = 0; i < trainSize; i++) {
            RegresionInstance instance = (RegresionInstance) trainData.get(i);
            sigma2Y += Math.pow(instance.getY() - muY, 2);
        }
        sigma2Y /= trainSize;
    }
    
    private void standarizeY(List<Instance> stdData){
        for (int i = 0; i < stdData.size(); i++) {
            RegresionInstance instance = (RegresionInstance) stdData.get(i);
            if (sigma2Y == 0) {
                instance.setY(0);
            } else {
                instance.setY((instance.getY() - muY) / (Math.sqrt(sigma2Y)));
            }
        }
    }

    public int getDataSize() {
        return dataSize;
    }

    public int getXSize() {
        return columns - 1;
    }
    
    public double getMuY() {
        return muY;
    }

    public double getSigma2Y() {
        return sigma2Y;
    }
    
    public void printMeans(){
        DecimalFormat decimal = new DecimalFormat("##0.00000");
        System.out.print(" MU  : ");
        for(int i=0; i<muX.length; i++){
            System.out.print( decimal.format(muX[i]) + " ");
        }
        System.out.print( decimal.format(muY) );
        System.out.println();
    }
    
    public void printVariances(){
        DecimalFormat decimal = new DecimalFormat("##0.00000");
        System.out.print(" VAR : ");
        for(int i=0; i<sigma2X.length; i++){
            System.out.print( decimal.format( sigma2X[i] ) + " ");
        }
        System.out.print( decimal.format( sigma2Y ) );
        System.out.println();
    }

    
    public double desestandariceY(double valueY){
        return valueY*Math.sqrt(sigma2Y) + muY;
    }
    
}
