/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.aida.bayes.main;

import com.rgonzalez.aida.bayes.database.ConnectionLoadFileMysql;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class MainDropFile {

    private double[][] frecuencyMatrix;
    private int[] clusterMatrix;
    
    public MainDropFile(){
        loadFromDB();
        saveToFile();
    }
    
    private void loadFromDB(){
        ConnectionLoadFileMysql load = new ConnectionLoadFileMysql();
        frecuencyMatrix = load.getFrecuencyMatrix();
        clusterMatrix = load.getClusterMatrix();
    }
    
    private void saveToFile(){
        PrintWriter out = null;
        try {
            File file = new File("dataset" + File.separator + "noticias.data");
            out = new PrintWriter(file);

            for (int i = 0; i < frecuencyMatrix.length; i++) {
                for (int j = 0; j < frecuencyMatrix[i].length; j++) {
                    out.print(frecuencyMatrix[i][j] + "  ");
                }
                out.print(clusterMatrix[i]);
                out.println();
            }
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainDropFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
    
    public static void main(String[] args){
        new MainDropFile();
    }
}
