/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.aida.svm.core;

/**
 *
 * @author Administrador
 */
public class Data {

    private double cLog;
    private double gammaLog;
    private double accuracy;

    public Data(double cLog, double gammaLog, double accuracy){
        this.cLog = cLog;
        this.gammaLog = gammaLog;        
        this.accuracy = accuracy;
    }
    
    public double getAccuracy() {
        return accuracy;
    }

    public double getCLog() {
        return cLog;
    }

    public double getGammaLog() {
        return gammaLog;
    }
    
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setCLog(double cLog) {
        this.cLog = cLog;
    }

    public void setGammaLog(double gammaLog) {
        this.gammaLog = gammaLog;
    }
}
