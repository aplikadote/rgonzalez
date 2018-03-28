/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.main;

import com.rgonzalez.aida.bayes.database.ConnectionLoadFileMysql;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class MainDropFileMatlab {

//    private double[][] frecuencyMatrix;
    private DecimalFormat decimal = new DecimalFormat("#0.000");
    private double[][] weightMatrix;
    private double[][] filterWeightMatrix;
    private int[] clusterMatrix;

    public MainDropFileMatlab() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(',');
        dfs.setDecimalSeparator('.');
        decimal.setDecimalFormatSymbols(dfs);
        loadFromDB();
        createWeightMatrix();
        reduceDimension();
        normalice(filterWeightMatrix);
        saveToFile(filterWeightMatrix);
    }

    private void loadFromDB() {
        ConnectionLoadFileMysql load = new ConnectionLoadFileMysql();
        weightMatrix = load.getFrecuencyMatrix();
        clusterMatrix = load.getClusterMatrix();
    }

    public void createWeightMatrix() {
//        int m = frecuencyMatrix.length;
//        int n = frecuencyMatrix[0].length;
//        relativeFrecuencyMatrix = new double[m][n];
        for (int i = 0; i < weightMatrix.length; i++) {
            double max = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < weightMatrix[i].length; j++) {
                if (weightMatrix[i][j] > max) {
                    max = weightMatrix[i][j];
                }
            }
            for (int j = 0; j < weightMatrix[i].length; j++) {
                weightMatrix[i][j] = weightMatrix[i][j] / max;
            }
        }

        for (int j = 0; j < weightMatrix[0].length; j++) {
            int sum = 0;
            for (int i = 0; i < weightMatrix.length; i++) {
                if (Double.compare(weightMatrix[i][j], 0) != 0) {
                    sum++;
                }
            }
            double invFrec = Math.log((double) weightMatrix.length / (double) sum);

            for (int i = 0; i < weightMatrix.length; i++) {
                weightMatrix[i][j] = (0.5 + 0.5*weightMatrix[i][j]) * invFrec;
            }
        }
    }
    
    private void reduceDimension(){
        List<Integer> listColumns = new ArrayList<Integer>();
        for (int i = 0; i < weightMatrix.length; i++) {
            double max = Double.NEGATIVE_INFINITY;
            int maxColumn=-1;
            for (int j = 0; j < weightMatrix[i].length; j++) {
                if(weightMatrix[i][j] > max){
                    max = weightMatrix[i][j];
                    maxColumn = j;
                }
            }
            
            Integer value = new Integer(maxColumn);
            if( !listColumns.contains(value) ){
                listColumns.add(value);
            }
        }
        
        Collections.sort(listColumns);
        int doc_size = weightMatrix.length;
        int voc_size = listColumns.size();
        filterWeightMatrix = new double[doc_size][voc_size];
        
        for (int j = 0; j < voc_size; j++) {
            int column = listColumns.get(j);
            for (int i = 0; i < doc_size; i++) {
                filterWeightMatrix[i][j] = weightMatrix[i][ column ];
            }
        }
    }

    private void normalice(double[][] matrix) {
        
        int doc_size = matrix.length;
        int voc_size = matrix[0].length;
        for (int j = 0; j < voc_size; j++) {
            double sum = 0;
            double count =0;
            for (int i = 0; i < doc_size; i++) {
                if (Double.compare(matrix[i][j], 0) != 0) {
                    sum += matrix[i][j];
                    count++;
//                    System.out.println(weightMatrix[i][j]);
                }
            }
            double mean = sum / count;

            sum = 0;
            count =0;
            for (int i = 0; i < doc_size; i++) {
                if (Double.compare(matrix[i][j], 0) != 0) {
                    sum += Math.pow(matrix[i][j] - mean, 2);
                    count++;
//                    System.out.println(weightMatrix[i][j] + "  " + mean );
                }
            }
            double std = Math.sqrt(sum / count);
            
            if(Double.compare(std, 0) == 0){
                std = 0.001;
            }

            for (int i = 0; i < doc_size; i++) {
                if (Double.compare(matrix[i][j], 0) != 0) {
                    matrix[i][j] = (matrix[i][j] - mean) / std;
//                    System.out.println(weightMatrix[i][j]);
                }
                else{
                    matrix[i][j] = -4;
                }
            }
//            try {
//                Thread.sleep(600000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(MainDropFileMatlab.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }

    private void saveToFile(double[][] matrix) {
        PrintWriter outInputs = null;
        PrintWriter outTargets = null;
        try {
            File fileInputs = new File("dataset" + File.separator + "noticiasInputs.data");
            outInputs = new PrintWriter(fileInputs);
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    outInputs.print(decimal.format(matrix[i][j]) + "  ");
                }
                outInputs.println();
            }
            outInputs.close();

            File fileTargets = new File("dataset" + File.separator + "noticiasTarget.data");
            outTargets = new PrintWriter(fileTargets);
            for (int i = 0; i < clusterMatrix.length; i++) {
                switch (clusterMatrix[i]) {
                    case 0:
                        outTargets.println("1 0 0 0 0 0 0");
                        break;
                    case 1:
                        outTargets.println("0 1 0 0 0 0 0");
                        break;
                    case 2:
                        outTargets.println("0 0 1 0 0 0 0");
                        break;
                    case 3:
                        outTargets.println("0 0 0 1 0 0 0");
                        break;
                    case 4:
                        outTargets.println("0 0 0 0 1 0 0");
                        break;
                    case 5:
                        outTargets.println("0 0 0 0 0 1 0");
                        break;
                    case 6:
                        outTargets.println("0 0 0 0 0 0 1");
                        break;
                }
            }
            outTargets.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainDropFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            outInputs.close();
            outTargets.close();
        }
    }

    public static void main(String[] args) {
        new MainDropFileMatlab();
    }
}
