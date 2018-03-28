/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.dataset;

/**
 *
 * @author Administrador
 */
public abstract class ClassificationDataset extends Dataset {

    public ClassificationDataset(String filename, int cols, int clusters, boolean standarize) {
        super(filename, cols, clusters, standarize);
    }
    
    public int getClusters(){
        return this.clusters;
    }
}
