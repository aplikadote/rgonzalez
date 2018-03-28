/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.svm.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import libsvm.svm_node;

/**
 *
 * @author Administrador
 */
public class FileParser {

    private static final long semilla = System.currentTimeMillis();
    private static final double porcentajeEntrenamiento = 0.8;
    
    private int trainSize;
    private double[] trainY;
    private svm_node[][] trainX;
    
    private int testSize;
    private double[] testY;
    private svm_node[][] testX;

    public FileParser(String filename) {
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            ArrayList<Integer> arrayClases = new ArrayList<Integer>();
            ArrayList<ArrayList<svm_node>> arrayArrayNodes = new ArrayList<ArrayList<svm_node>>();
            while ((line = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, "; ");

                String token = st.nextToken();
                arrayClases.add(Integer.parseInt(token));

                ArrayList<svm_node> arrayNodes = new ArrayList<svm_node>();
                while (st.hasMoreTokens()) {
                    String[] punto = st.nextToken().split(":");
                    svm_node nodo = new svm_node();
                    nodo.index = Integer.parseInt(punto[0]);
                    nodo.value = Double.parseDouble(punto[1]);
                    arrayNodes.add(nodo);
                }
                arrayArrayNodes.add(arrayNodes);
            }

            unorder(arrayClases, arrayArrayNodes);

            int dataSize = arrayClases.size();
            trainSize =  (int)(dataSize * porcentajeEntrenamiento);
            testSize = dataSize - trainSize;
            
            this.trainY = new double[trainSize];
            this.trainX = new svm_node[trainSize][0];
            
            this.testY = new double[testSize];
            this.testX = new svm_node[testSize][0];
                    
            for(int i=0; i<dataSize; i++){
                if( i<trainSize ){
                    this.trainY[i] = arrayClases.get(i);
                    ArrayList<svm_node> arraylistAux = arrayArrayNodes.get(i);
                    svm_node[] arrayAux = new svm_node[arraylistAux.size()];
                    for(int j=0; j<arraylistAux.size(); j++){
                        arrayAux[j] = arraylistAux.get(j);
                    }
                    this.trainX[i] = arrayAux;
                    
                }
                else{
                    this.testY[i-trainSize] = arrayClases.get(i);
                    ArrayList<svm_node> arraylistAux = arrayArrayNodes.get(i);
                    svm_node[] arrayAux = new svm_node[arraylistAux.size()];
                    for(int j=0; j<arraylistAux.size(); j++){
                        arrayAux[j] = arraylistAux.get(j);
                    }
                    this.testX[i-trainSize] = arrayAux;
                }
            }
            
//            for(int i=0; i< trainSize; i++){
//                System.out.println( "trainY["+i+"] " + trainY[i] );
//            }
            
//            for(int i=0; i< trainSize; i++){
//                System.out.print((int)trainY[i]+";");
//                for(int j=0; j< trainNodes[i].length; j++){
//                    System.out.print(trainNodes[i][j].index + ":" + trainNodes[i][j].value + ";");
//                }
//                System.out.println();
//            }
//            
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            
//            for(int i=0; i< testSize; i++){
//                System.out.print( (int)testY[i]+";");
//                for(int j=0; j< testNodes[i].length; j++){
//                    System.out.print(testNodes[i][j].index + ":" + testNodes[i][j].value + ";");
//                }
//                System.out.println();
//            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void unorder(ArrayList<Integer> arrayClases, ArrayList<ArrayList<svm_node>> arrayArrayNodes) {
        Random r = new Random(semilla);
        ArrayList<Integer> myArrayClases = new ArrayList<Integer>();
        ArrayList<ArrayList<svm_node>> myArrayArrayNodes = new ArrayList<ArrayList<svm_node>>();

        int size = arrayClases.size();
        for (int i = 0; i < size; i++) {
            int p = r.nextInt(arrayClases.size());
            
            Integer clase = arrayClases.get(p);
            ArrayList<svm_node> nodos = arrayArrayNodes.get(p);
            
            myArrayClases.add(clase);
            myArrayArrayNodes.add(nodos);
            
            arrayClases.remove(clase);
            arrayArrayNodes.remove(nodos);
        }
        
        arrayClases.addAll(myArrayClases);
        arrayArrayNodes.addAll(myArrayArrayNodes);
    }
    
    public svm_node[][] getTestX() {
        return testX;
    }

    public int getTestSize() {
        return testSize;
    }

    public double[] getTestY() {
        return testY;
    }

    public svm_node[][] getTrainX() {
        return trainX;
    }

    public int getTrainSize() {
        return trainSize;
    }

    public double[] getTrainY() {
        return trainY;
    }
    
}
